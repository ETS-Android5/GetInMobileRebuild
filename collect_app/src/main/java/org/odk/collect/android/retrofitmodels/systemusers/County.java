
package org.odk.collect.android.retrofitmodels.systemusers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class County {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
