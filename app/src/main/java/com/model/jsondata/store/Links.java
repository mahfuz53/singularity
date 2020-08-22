
package com.model.jsondata.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links {

    @SerializedName("first")
    @Expose
    public String first;
    @SerializedName("last")
    @Expose
    public String last;
    @SerializedName("prev")
    @Expose
    public Object prev;
    @SerializedName("next")
    @Expose
    public String next;

}
