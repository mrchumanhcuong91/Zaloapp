package com.project.android.zaloapp.Helper;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class CustomJsonRequest extends JsonObjectRequest {
    public CustomJsonRequest(int method, String url, JSONObject jsonObject,
                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        super(method, url, jsonObject, listener, errorListener);
    }
    private Priority mPriority;
    public void setPriority(Priority priority){
        mPriority = priority;
    }
    @Override
    public Priority getPriority() {
        return mPriority == null ? Priority.NORMAL : mPriority;
    }
}
