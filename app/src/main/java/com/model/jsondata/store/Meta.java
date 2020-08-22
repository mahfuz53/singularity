
package com.model.jsondata.store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("from")
    @Expose
    public Integer from;
    @SerializedName("last_page")
    @Expose
    public Integer lastPage;
    @SerializedName("path")
    @Expose
    public String path;
    @SerializedName("per_page")
    @Expose
    public Integer perPage;
    @SerializedName("to")
    @Expose
    public Integer to;
    @SerializedName("total")
    @Expose
    public Integer total;

}
