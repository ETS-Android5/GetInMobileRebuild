
package org.odk.getin.android.retrofitmodels.healthfacilitymodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HealthFacility {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sub_county")
    @Expose
    private SubCounty subCounty;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("facility_level")
    @Expose
    private String facilityLevel;
    @SerializedName("midwife")
    @Expose
    private Integer midwife;
    @SerializedName("chew")
    @Expose
    private Integer chew;
    @SerializedName("ambulance")
    @Expose
    private Integer ambulance;
    @SerializedName("average_deliveries")
    @Expose
    private Integer averageDeliveries;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SubCounty getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(SubCounty subCounty) {
        this.subCounty = subCounty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacilityLevel() {
        return facilityLevel;
    }

    public void setFacilityLevel(String facilityLevel) {
        this.facilityLevel = facilityLevel;
    }

    public Integer getMidwife() {
        return midwife;
    }

    public void setMidwife(Integer midwife) {
        this.midwife = midwife;
    }

    public Integer getChew() {
        return chew;
    }

    public void setChew(Integer chew) {
        this.chew = chew;
    }

    public Integer getAmbulance() {
        return ambulance;
    }

    public void setAmbulance(Integer ambulance) {
        this.ambulance = ambulance;
    }

    public Integer getAverageDeliveries() {
        return averageDeliveries;
    }

    public void setAverageDeliveries(Integer averageDeliveries) {
        this.averageDeliveries = averageDeliveries;
    }

}
