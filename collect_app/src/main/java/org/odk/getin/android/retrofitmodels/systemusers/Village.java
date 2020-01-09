
package org.odk.getin.android.retrofitmodels.systemusers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Village {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parish")
    @Expose
    private Parish parish;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parish getParish() {
        return parish;
    }

    public void setParish(Parish parish) {
        this.parish = parish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
