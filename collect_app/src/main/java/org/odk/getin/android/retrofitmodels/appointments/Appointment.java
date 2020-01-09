
package org.odk.getin.android.retrofitmodels.appointments;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Appointment implements Parcelable
{

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
    public final static Creator<Appointment> CREATOR = new Creator<Appointment>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        public Appointment[] newArray(int size) {
            return (new Appointment[size]);
        }

    }
    ;

    protected Appointment(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.girl = ((Girl) in.readValue((Girl.class.getClassLoader())));
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.date = ((Date) in.readValue((Date.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.odkInstanceId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.healthFacility = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Appointment() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(girl);
        dest.writeValue(user);
        dest.writeValue(date);
        dest.writeValue(status);
        dest.writeValue(odkInstanceId);
        dest.writeValue(createdAt);
        dest.writeValue(healthFacility);
    }

    public int describeContents() {
        return  0;
    }

}
