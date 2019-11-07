
package org.odk.collect.android.retrofitmodels.appointments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("girl")
    @Expose
    private Girl girl;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("odk_instance_id")
    @Expose
    private Object odkInstanceId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("health_facility")
    @Expose
    private Object healthFacility;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getOdkInstanceId() {
        return odkInstanceId;
    }

    public void setOdkInstanceId(Object odkInstanceId) {
        this.odkInstanceId = odkInstanceId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getHealthFacility() {
        return healthFacility;
    }

    public void setHealthFacility(Object healthFacility) {
        this.healthFacility = healthFacility;
    }

}
