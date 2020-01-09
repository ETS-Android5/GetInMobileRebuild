
package org.odk.getin.android.retrofitmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bundibugyo {

    @SerializedName("GirlsCountyBundibugyo")
    @Expose
    private Object girlsCountyBundibugyo;
    @SerializedName("Girls_SubCounty_Bundibugyo")
    @Expose
    private Object girlsSubCountyBundibugyo;

    public Object getGirlsCountyBundibugyo() {
        return girlsCountyBundibugyo;
    }

    public void setGirlsCountyBundibugyo(Object girlsCountyBundibugyo) {
        this.girlsCountyBundibugyo = girlsCountyBundibugyo;
    }

    public Object getGirlsSubCountyBundibugyo() {
        return girlsSubCountyBundibugyo;
    }

    public void setGirlsSubCountyBundibugyo(Object girlsSubCountyBundibugyo) {
        this.girlsSubCountyBundibugyo = girlsSubCountyBundibugyo;
    }

}
