
package com.model.jsondata.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.model.jsondata.store.Datum;

import java.util.List;

public class LoginData {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("app_message")
    @Expose
    public String appMessage;
    @SerializedName("user_message")
    @Expose
    public String userMessage;
    @SerializedName("data")
    @Expose
    public Data data;

    public Data getdata() {
        return data;
    }
}
