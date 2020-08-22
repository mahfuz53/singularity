package com.presenter.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.Singularity.store.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.model.jsondata.login.LoginData;
import com.model.jsondata.store.StoreData;
import com.presenter.view.AlertMessage;

import org.json.JSONObject;

public class DataParseVolly {


    public void DataVolleyJson(final Context con, String url, final int aryObj, final JSONObject jsonBody,int requestType ,final PromptRunnable postrun)
    {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, con.getString(R.string.app_name),con.getString(R.string.internetConnection));
            return ;
        }
        final BusyDialog busy = new BusyDialog(con, true);
        try{
            busy.show();
        }
        catch (Exception e)
        {
            //TODO
        }
        print.message("jsonBody.."+jsonBody);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(requestType, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response!= null)
                {
                    if (busy != null) {
                        busy.dismis();
                    }
                    print.message("result.."+response.toString());
                    dataAdd(response.toString(),con,aryObj,postrun);
                }
                else {
                    FileUtils.showToast(con,con.getString(R.string.tryagain));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //handle the error
                if (busy != null) {
                    busy.dismis();
                }
                FileUtils.showToast(con,con.getString(R.string.invalid));
                error.printStackTrace();

            }
        });

        Volley.newRequestQueue(con).add(jsonRequest);
    }

    public void dataAdd(String result,Context con,int aryObj,final PromptRunnable postrun)
    {
        Gson gson = new Gson();
        String json = result;
        if(aryObj == 1) {
            StoreData obj = gson.fromJson(json, StoreData.class);
            if (obj != null) {
                postrun.setStore(obj);
                postrun.setValue(true);
                postrun.run();
            }
        }
        else if(aryObj == 2)
        {
            LoginData obj = gson.fromJson(json, LoginData.class);
            if (obj != null) {
                postrun.setLogin(obj);
                postrun.setValue(true);
                postrun.run();
            }
        }
    }


}
