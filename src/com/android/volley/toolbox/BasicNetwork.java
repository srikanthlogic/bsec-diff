package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.ClientError;
import com.android.volley.Header;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.common.net.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
/* loaded from: classes.dex */
public class BasicNetwork implements Network {
    protected static final boolean DEBUG = VolleyLog.DEBUG;
    private static final int DEFAULT_POOL_SIZE = 4096;
    private static final int SLOW_REQUEST_THRESHOLD_MS = 3000;
    private final BaseHttpStack mBaseHttpStack;
    @Deprecated
    protected final HttpStack mHttpStack;
    protected final ByteArrayPool mPool;

    @Deprecated
    public BasicNetwork(HttpStack httpStack) {
        this(httpStack, new ByteArrayPool(4096));
    }

    @Deprecated
    public BasicNetwork(HttpStack httpStack, ByteArrayPool pool) {
        this.mHttpStack = httpStack;
        this.mBaseHttpStack = new AdaptedHttpStack(httpStack);
        this.mPool = pool;
    }

    public BasicNetwork(BaseHttpStack httpStack) {
        this(httpStack, new ByteArrayPool(4096));
    }

    public BasicNetwork(BaseHttpStack httpStack, ByteArrayPool pool) {
        this.mBaseHttpStack = httpStack;
        this.mHttpStack = httpStack;
        this.mPool = pool;
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00bf, code lost:
        throw new java.io.IOException();
     */
    @Override // com.android.volley.Network
    /* Code decompiled incorrectly, please refer to instructions dump */
    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        MalformedURLException e;
        HttpResponse httpResponse;
        IOException e2;
        byte[] responseContents;
        long requestStart = SystemClock.elapsedRealtime();
        while (true) {
            byte[] responseContents2 = null;
            List<Header> responseHeaders = Collections.emptyList();
            try {
                httpResponse = this.mBaseHttpStack.executeRequest(request, getCacheHeaders(request.getCacheEntry()));
                try {
                    int statusCode = httpResponse.getStatusCode();
                    List<Header> responseHeaders2 = httpResponse.getHeaders();
                    try {
                        if (statusCode == 304) {
                            Cache.Entry entry = request.getCacheEntry();
                            if (entry == null) {
                                return new NetworkResponse(304, (byte[]) null, true, SystemClock.elapsedRealtime() - requestStart, responseHeaders2);
                            }
                            return new NetworkResponse(304, entry.data, true, SystemClock.elapsedRealtime() - requestStart, combineHeaders(responseHeaders2, entry));
                        }
                        try {
                            InputStream inputStream = httpResponse.getContent();
                            if (inputStream != null) {
                                responseContents = inputStreamToBytes(inputStream, httpResponse.getContentLength());
                            } else {
                                responseContents = new byte[0];
                            }
                            try {
                                logSlowRequests(SystemClock.elapsedRealtime() - requestStart, request, responseContents, statusCode);
                                try {
                                    if (statusCode < 200 || statusCode > 299) {
                                        break;
                                    }
                                    return new NetworkResponse(statusCode, responseContents, false, SystemClock.elapsedRealtime() - requestStart, responseHeaders2);
                                } catch (MalformedURLException e3) {
                                    e = e3;
                                    throw new RuntimeException("Bad URL " + request.getUrl(), e);
                                } catch (SocketTimeoutException e4) {
                                    attemptRetryOnException("socket", request, new TimeoutError());
                                } catch (IOException e5) {
                                    e2 = e5;
                                    responseContents2 = responseContents;
                                    if (httpResponse != null) {
                                        int statusCode2 = httpResponse.getStatusCode();
                                        VolleyLog.e("Unexpected response code %d for %s", Integer.valueOf(statusCode2), request.getUrl());
                                        if (responseContents2 != null) {
                                            NetworkResponse networkResponse = new NetworkResponse(statusCode2, responseContents2, false, SystemClock.elapsedRealtime() - requestStart, responseHeaders);
                                            if (statusCode2 == 401 || statusCode2 == 403) {
                                                attemptRetryOnException("auth", request, new AuthFailureError(networkResponse));
                                            } else if (statusCode2 >= 400 && statusCode2 <= 499) {
                                                throw new ClientError(networkResponse);
                                            } else if (statusCode2 < 500 || statusCode2 > 599) {
                                                throw new ServerError(networkResponse);
                                            } else if (request.shouldRetryServerErrors()) {
                                                attemptRetryOnException("server", request, new ServerError(networkResponse));
                                            } else {
                                                throw new ServerError(networkResponse);
                                            }
                                        } else {
                                            attemptRetryOnException("network", request, new NetworkError());
                                        }
                                    } else {
                                        throw new NoConnectionError(e2);
                                    }
                                }
                            } catch (MalformedURLException e6) {
                                e = e6;
                            } catch (SocketTimeoutException e7) {
                            } catch (IOException e8) {
                                e2 = e8;
                                responseHeaders = responseHeaders2;
                                responseContents2 = responseContents;
                            }
                        } catch (MalformedURLException e9) {
                            e = e9;
                        } catch (SocketTimeoutException e10) {
                        } catch (IOException e11) {
                            e2 = e11;
                            responseHeaders = responseHeaders2;
                        }
                    } catch (MalformedURLException e12) {
                        e = e12;
                    } catch (SocketTimeoutException e13) {
                    } catch (IOException e14) {
                        e2 = e14;
                        responseHeaders = responseHeaders2;
                    }
                } catch (MalformedURLException e15) {
                    e = e15;
                } catch (SocketTimeoutException e16) {
                } catch (IOException e17) {
                    e2 = e17;
                }
            } catch (MalformedURLException e18) {
                e = e18;
            } catch (SocketTimeoutException e19) {
            } catch (IOException e20) {
                e2 = e20;
                httpResponse = null;
            }
        }
    }

    private void logSlowRequests(long requestLifetime, Request<?> request, byte[] responseContents, int statusCode) {
        if (DEBUG || requestLifetime > 3000) {
            Object[] objArr = new Object[5];
            objArr[0] = request;
            objArr[1] = Long.valueOf(requestLifetime);
            objArr[2] = responseContents != null ? Integer.valueOf(responseContents.length) : "null";
            objArr[3] = Integer.valueOf(statusCode);
            objArr[4] = Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount());
            VolleyLog.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", objArr);
        }
    }

    private static void attemptRetryOnException(String logPrefix, Request<?> request, VolleyError exception) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();
        try {
            retryPolicy.retry(exception);
            request.addMarker(String.format("%s-retry [timeout=%s]", logPrefix, Integer.valueOf(oldTimeout)));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", logPrefix, Integer.valueOf(oldTimeout)));
            throw e;
        }
    }

    private Map<String, String> getCacheHeaders(Cache.Entry entry) {
        if (entry == null) {
            return Collections.emptyMap();
        }
        Map<String, String> headers = new HashMap<>();
        if (entry.etag != null) {
            headers.put(HttpHeaders.IF_NONE_MATCH, entry.etag);
        }
        if (entry.lastModified > 0) {
            headers.put(HttpHeaders.IF_MODIFIED_SINCE, HttpHeaderParser.formatEpochAsRfc1123(entry.lastModified));
        }
        return headers;
    }

    protected void logError(String what, String url, long start) {
        VolleyLog.v("HTTP ERROR(%s) %d ms to fetch %s", what, Long.valueOf(SystemClock.elapsedRealtime() - start), url);
    }

    private byte[] inputStreamToBytes(InputStream in, int contentLength) throws IOException, ServerError {
        PoolingByteArrayOutputStream bytes = new PoolingByteArrayOutputStream(this.mPool, contentLength);
        try {
            if (in != null) {
                byte[] buffer = this.mPool.getBuf(1024);
                while (true) {
                    int count = in.read(buffer);
                    if (count == -1) {
                        break;
                    }
                    bytes.write(buffer, 0, count);
                }
                byte[] byteArray = bytes.toByteArray();
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                    }
                }
                this.mPool.returnBuf(buffer);
                bytes.close();
                return byteArray;
            }
            throw new ServerError();
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e2) {
                    VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                }
            }
            this.mPool.returnBuf(null);
            bytes.close();
            throw th;
        }
    }

    @Deprecated
    protected static Map<String, String> convertHeaders(Header[] headers) {
        Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < headers.length; i++) {
            result.put(headers[i].getName(), headers[i].getValue());
        }
        return result;
    }

    private static List<Header> combineHeaders(List<Header> responseHeaders, Cache.Entry entry) {
        Set<String> headerNamesFromNetworkResponse = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (!responseHeaders.isEmpty()) {
            for (Header header : responseHeaders) {
                headerNamesFromNetworkResponse.add(header.getName());
            }
        }
        List<Header> combinedHeaders = new ArrayList<>(responseHeaders);
        if (entry.allResponseHeaders != null) {
            if (!entry.allResponseHeaders.isEmpty()) {
                for (Header header2 : entry.allResponseHeaders) {
                    if (!headerNamesFromNetworkResponse.contains(header2.getName())) {
                        combinedHeaders.add(header2);
                    }
                }
            }
        } else if (!entry.responseHeaders.isEmpty()) {
            for (Map.Entry<String, String> header3 : entry.responseHeaders.entrySet()) {
                if (!headerNamesFromNetworkResponse.contains(header3.getKey())) {
                    combinedHeaders.add(new Header(header3.getKey(), header3.getValue()));
                }
            }
        }
        return combinedHeaders;
    }
}
