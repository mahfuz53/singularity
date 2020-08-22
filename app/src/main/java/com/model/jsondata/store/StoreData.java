
package com.model.jsondata.store;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreData {

    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("links")
    @Expose
    public Links links;
    @SerializedName("meta")
    @Expose
    public Meta meta;

    public List<Datum> getInspectionPoints() {
        return data;
    }

}
