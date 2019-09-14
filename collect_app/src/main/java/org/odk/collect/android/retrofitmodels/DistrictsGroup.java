
package org.odk.collect.android.retrofitmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictsGroup {

    @SerializedName("GirlDistrict")
    @Expose
    private String girlDistrict;
    @SerializedName("Bundibugyo")
    @Expose
    private Bundibugyo bundibugyo;

    public String getGirlDistrict() {
        return girlDistrict;
    }

    public void setGirlDistrict(String girlDistrict) {
        this.girlDistrict = girlDistrict;
    }

    public Bundibugyo getBundibugyo() {
        return bundibugyo;
    }

    public void setBundibugyo(Bundibugyo bundibugyo) {
        this.bundibugyo = bundibugyo;
    }

}
