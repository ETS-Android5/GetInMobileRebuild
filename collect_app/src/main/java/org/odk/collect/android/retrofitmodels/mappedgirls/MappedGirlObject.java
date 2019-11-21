
package org.odk.collect.android.retrofitmodels.mappedgirls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MappedGirlObject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("village")
    @Expose
    private Village village;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("trimester")
    @Expose
    private Integer trimester;
    @SerializedName("next_of_kin_last_name")
    @Expose
    private String nextOfKinLastName;
    @SerializedName("next_of_kin_first_name")
    @Expose
    private String nextOfKinFirstName;
    @SerializedName("next_of_kin_phone_number")
    @Expose
    private String nextOfKinPhoneNumber;
    @SerializedName("education_level")
    @Expose
    private String educationLevel;
    @SerializedName("marital_status")
    @Expose
    private String maritalStatus;
    @SerializedName("last_menstruation_date")
    @Expose
    private String lastMenstruationDate;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("odk_instance_id")
    @Expose
    private Object odkInstanceId;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("completed_all_visits")
    @Expose
    private Boolean completedAllVisits;
    @SerializedName("pending_visits")
    @Expose
    private Integer pendingVisits;
    @SerializedName("missed_visits")
    @Expose
    private Integer missedVisits;
    @SerializedName("created_at")
    @Expose
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getTrimester() {
        return trimester;
    }

    public void setTrimester(Integer trimester) {
        this.trimester = trimester;
    }

    public String getNextOfKinLastName() {
        return nextOfKinLastName;
    }

    public void setNextOfKinLastName(String nextOfKinLastName) {
        this.nextOfKinLastName = nextOfKinLastName;
    }

    public String getNextOfKinFirstName() {
        return nextOfKinFirstName;
    }

    public void setNextOfKinFirstName(String nextOfKinFirstName) {
        this.nextOfKinFirstName = nextOfKinFirstName;
    }

    public String getNextOfKinPhoneNumber() {
        return nextOfKinPhoneNumber;
    }

    public void setNextOfKinPhoneNumber(String nextOfKinPhoneNumber) {
        this.nextOfKinPhoneNumber = nextOfKinPhoneNumber;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getLastMenstruationDate() {
        return lastMenstruationDate;
    }

    public void setLastMenstruationDate(String lastMenstruationDate) {
        this.lastMenstruationDate = lastMenstruationDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Object getOdkInstanceId() {
        return odkInstanceId;
    }

    public void setOdkInstanceId(Object odkInstanceId) {
        this.odkInstanceId = odkInstanceId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getCompletedAllVisits() {
        return completedAllVisits;
    }

    public void setCompletedAllVisits(Boolean completedAllVisits) {
        this.completedAllVisits = completedAllVisits;
    }

    public Integer getPendingVisits() {
        return pendingVisits;
    }

    public void setPendingVisits(Integer pendingVisits) {
        this.pendingVisits = pendingVisits;
    }

    public Integer getMissedVisits() {
        return missedVisits;
    }

    public void setMissedVisits(Integer missedVisits) {
        this.missedVisits = missedVisits;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
