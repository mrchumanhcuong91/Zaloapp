package com.project.android.zaloapp.Helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleTon {
    private static MySingleTon instance;
    private static Context mContex;
    private static RequestQueue requestQueue;

    public MySingleTon(Context context){

        mContex = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){

        if(requestQueue == null){
            requestQueue =  Volley.newRequestQueue(mContex.getApplicationContext());
            return requestQueue;

        }
        return requestQueue;
    }
    public static synchronized MySingleTon getInstance(Context mCtx){

        if(instance == null){
            instance = new MySingleTon(mCtx);
            return instance;
        }
        return instance;
    }

    public <T> void addToRequestQue (Request<T> request){
        requestQueue.add(request);
    }
}
