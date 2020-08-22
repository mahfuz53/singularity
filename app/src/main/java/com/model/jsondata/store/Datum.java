
package com.model.jsondata.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("address")
    @Expose
    public String address;

    public int getid() {
        return id;
    }
    public String getname() {
        return name;
    }
    public String getaddress() {
        return address;
    }

}
