package com.android.volley.toolbox;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
/* loaded from: classes.dex */
public class JsonArrayRequest extends JsonRequest<JSONArray> {
    public JsonArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(0, url, null, listener, errorListener);
    }

    public JsonArrayRequest(int method, String url, JSONArray jsonRequest, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest == null ? null : jsonRequest.toString(), listener, errorListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.volley.toolbox.JsonRequest, com.android.volley.Request
    public Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new JSONArray(new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"))), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
