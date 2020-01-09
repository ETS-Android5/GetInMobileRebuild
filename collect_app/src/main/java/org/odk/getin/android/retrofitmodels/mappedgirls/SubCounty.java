
package org.odk.getin.android.retrofitmodels.mappedgirls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCounty {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("county")
    @Expose
    private County county;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
