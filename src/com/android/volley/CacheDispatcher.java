package com.android.volley;

import android.os.Process;
import com.android.volley.Cache;
import com.android.volley.Request;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
/* loaded from: classes.dex */
public class CacheDispatcher extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    private final Cache mCache;
    private final BlockingQueue<Request<?>> mCacheQueue;
    private final ResponseDelivery mDelivery;
    private final BlockingQueue<Request<?>> mNetworkQueue;
    private volatile boolean mQuit = false;
    private final WaitingRequestManager mWaitingRequestManager = new WaitingRequestManager(this);

    public CacheDispatcher(BlockingQueue<Request<?>> cacheQueue, BlockingQueue<Request<?>> networkQueue, Cache cache, ResponseDelivery delivery) {
        this.mCacheQueue = cacheQueue;
        this.mNetworkQueue = networkQueue;
        this.mCache = cache;
        this.mDelivery = delivery;
    }

    public void quit() {
        this.mQuit = true;
        interrupt();
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (DEBUG) {
            VolleyLog.v("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.mCache.initialize();
        while (true) {
            try {
                processRequest();
            } catch (InterruptedException e) {
                if (this.mQuit) {
                    Thread.currentThread().interrupt();
                    return;
                }
                VolleyLog.e("Ignoring spurious interrupt of CacheDispatcher thread; use quit() to terminate it", new Object[0]);
            }
        }
    }

    private void processRequest() throws InterruptedException {
        processRequest(this.mCacheQueue.take());
    }

    void processRequest(final Request<?> request) throws InterruptedException {
        request.addMarker("cache-queue-take");
        if (request.isCanceled()) {
            request.finish("cache-discard-canceled");
            return;
        }
        Cache.Entry entry = this.mCache.get(request.getCacheKey());
        if (entry == null) {
            request.addMarker("cache-miss");
            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                this.mNetworkQueue.put(request);
            }
        } else if (entry.isExpired()) {
            request.addMarker("cache-hit-expired");
            request.setCacheEntry(entry);
            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                this.mNetworkQueue.put(request);
            }
        } else {
            request.addMarker("cache-hit");
            Response<?> response = request.parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
            request.addMarker("cache-hit-parsed");
            if (!entry.refreshNeeded()) {
                this.mDelivery.postResponse(request, response);
                return;
            }
            request.addMarker("cache-hit-refresh-needed");
            request.setCacheEntry(entry);
            response.intermediate = true;
            if (!this.mWaitingRequestManager.maybeAddToWaitingRequests(request)) {
                this.mDelivery.postResponse(request, response, new Runnable() { // from class: com.android.volley.CacheDispatcher.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            CacheDispatcher.this.mNetworkQueue.put(request);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                });
            } else {
                this.mDelivery.postResponse(request, response);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class WaitingRequestManager implements Request.NetworkRequestCompleteListener {
        private final CacheDispatcher mCacheDispatcher;
        private final Map<String, List<Request<?>>> mWaitingRequests = new HashMap();

        WaitingRequestManager(CacheDispatcher cacheDispatcher) {
            this.mCacheDispatcher = cacheDispatcher;
        }

        @Override // com.android.volley.Request.NetworkRequestCompleteListener
        public void onResponseReceived(Request<?> request, Response<?> response) {
            List<Request<?>> waitingRequests;
            if (response.cacheEntry == null || response.cacheEntry.isExpired()) {
                onNoUsableResponseReceived(request);
                return;
            }
            String cacheKey = request.getCacheKey();
            synchronized (this) {
                waitingRequests = this.mWaitingRequests.remove(cacheKey);
            }
            if (waitingRequests != null) {
                if (VolleyLog.DEBUG) {
                    VolleyLog.v("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(waitingRequests.size()), cacheKey);
                }
                for (Request<?> waiting : waitingRequests) {
                    this.mCacheDispatcher.mDelivery.postResponse(waiting, response);
                }
            }
        }

        @Override // com.android.volley.Request.NetworkRequestCompleteListener
        public synchronized void onNoUsableResponseReceived(Request<?> request) {
            String cacheKey = request.getCacheKey();
            List<Request<?>> waitingRequests = this.mWaitingRequests.remove(cacheKey);
            if (waitingRequests != null && !waitingRequests.isEmpty()) {
                if (VolleyLog.DEBUG) {
                    VolleyLog.v("%d waiting requests for cacheKey=%s; resend to network", Integer.valueOf(waitingRequests.size()), cacheKey);
                }
                Request<?> nextInLine = waitingRequests.remove(0);
                this.mWaitingRequests.put(cacheKey, waitingRequests);
                nextInLine.setNetworkRequestCompleteListener(this);
                try {
                    this.mCacheDispatcher.mNetworkQueue.put(nextInLine);
                } catch (InterruptedException iex) {
                    VolleyLog.e("Couldn't add request to queue. %s", iex.toString());
                    Thread.currentThread().interrupt();
                    this.mCacheDispatcher.quit();
                }
            }
        }

        public synchronized boolean maybeAddToWaitingRequests(Request<?> request) {
            String cacheKey = request.getCacheKey();
            if (this.mWaitingRequests.containsKey(cacheKey)) {
                List<Request<?>> stagedRequests = this.mWaitingRequests.get(cacheKey);
                if (stagedRequests == null) {
                    stagedRequests = new ArrayList<>();
                }
                request.addMarker("waiting-for-response");
                stagedRequests.add(request);
                this.mWaitingRequests.put(cacheKey, stagedRequests);
                if (VolleyLog.DEBUG) {
                    VolleyLog.d("Request for cacheKey=%s is in flight, putting on hold.", cacheKey);
                }
                return true;
            }
            this.mWaitingRequests.put(cacheKey, null);
            request.setNetworkRequestCompleteListener(this);
            if (VolleyLog.DEBUG) {
                VolleyLog.d("new request, sending to network %s", cacheKey);
            }
            return false;
        }
    }
}
