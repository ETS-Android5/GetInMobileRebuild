
package org.odk.collect.android.retrofitmodels.appointments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parish {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sub_county")
    @Expose
    private Integer subCounty;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(Integer subCounty) {
        this.subCounty = subCounty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
