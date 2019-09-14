
package org.odk.collect.android.retrofitmodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MappedGirls {

    @SerializedName("value")
    @Expose
    private List<Value> value = null;
    @SerializedName("@odata.context")
    @Expose
    private String odataContext;

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }

    public String getOdataContext() {
        return odataContext;
    }

    public void setOdataContext(String odataContext) {
        this.odataContext = odataContext;
    }

}
