package com.project.android.zaloapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.android.zaloapp.Helper.CustomJsonRequest;
import com.project.android.zaloapp.Helper.MySingleTon;
import com.project.android.zaloapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeatherCalenderFrag extends Fragment {
    // "http://api.openweathermap.org/data/2.5/weather?q="+"London"+"&APPID=86261f8b8fe007a9f5732f5cfc7c676a";
    private TextView mTemp,nameCity;
    private TextView mDescription,dateNow;
    private ImageView imageView;
    private Context mContext;
    private String icon;
    final static String RECENT_API_ENDPOINT ="http://api.openweathermap.org/data/2.5/weather?q=hanoi,vietnam&appid=e148eb053c7e9cc462b03efd7279de4e";;
    private  MySingleTon helper;
    @Override
    public void onCreate(Bundle saveInstanseState){
        super.onCreate(saveInstanseState);
        helper = MySingleTon.getInstance(mContext);

    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle saveInstanseState)
    {
        View view = layoutInflater.inflate(R.layout.weather_calender, container, false);

        mTemp = (TextView)view.findViewById(R.id.temp);
        nameCity = (TextView)view.findViewById(R.id.nameCity);
        dateNow = (TextView)view.findViewById(R.id.dateNow);
        mDescription = (TextView)view.findViewById(R.id.discripTemp);
        imageView = (ImageView)view.findViewById(R.id.imageWeather);
        loadWeatherData();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("EEE - dd - MMM");
        dateNow.setText(date.format(cal.getTime()));

        return view;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    private void loadWeatherData() {
        Log.e("CuongCM",RECENT_API_ENDPOINT);

//        RequestQueue request = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, RECENT_API_ENDPOINT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Process the JSON
                Log.e("CUONGCM ERROR", "Process the JSON");
                try{
                    // Get the JSON array
                    String description ="";
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object_temp = response.getJSONObject("main");
//                    JSONObject icon_obj = response.getJSONObject("icon");

                    for(int i =0;i < array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        description = object.getString("description");
                        icon = object.getString("icon");
                    }
                    Log.e("CUONGCM", "icon "+icon);
                    getIcon(icon);
                    String city = response.getString("name");
                    //tempurature
                    int temp = object_temp.getInt("temp");
                    int minTemp = object_temp.getInt("temp_min");
                    int maxTemp = object_temp.getInt("temp_max");
                    mTemp.setText(Integer.toString(minTemp-273) +"--" +Integer.toString(maxTemp -273));
                    nameCity.setText(city);
                    mDescription.setText(description);
                    Log.e("CUONGCM ","city name "+city +"temp " +temp);

                    Toast.makeText(mContext, "Name of city" +city,Toast.LENGTH_LONG).show();

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(mContext, "Error printStackTrace",Toast.LENGTH_LONG).show();
                    Log.e("CUONGCM","json get failed");
                }
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                // Do something when error occurred
                Log.e("CUONGCM","parseVolleyError" +error.getMessage());


            }
        });

        helper.addToRequestQue(jsonObjectRequest);

    }
    private void getIcon(String code){
//        String url_base = "http://openweathermap.org/img/w/";
        String url_image = "http://openweathermap.org/img/w/"+code+".png";//url_base +code;
        Log.e("CuongCM","image url "+url_image);
        ImageRequest imageRequest = new ImageRequest(url_image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "something wrong",Toast.LENGTH_LONG).show();

                    }
                });

            helper.addToRequestQue(imageRequest);
    }
}
