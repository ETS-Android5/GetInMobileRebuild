
package org.odk.collect.android.retrofitmodels.mappedgirls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Girl {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("village")
    @Expose
    private Village village;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
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
    @SerializedName("created_at")
    @Expose
    private Date createdAt;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("completed_all_visits")
    @Expose
    private Boolean completedAllVisits;
    @SerializedName("pending_visits")
    @Expose
    private int pendingVisits;
    @SerializedName("missed_visits")
    @Expose
    private int missedVisits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean isCompletedAllVisits() {
        return completedAllVisits;
    }

    public void setCompletedAllVisits(Boolean completedAllVisits) {
        this.completedAllVisits = completedAllVisits;
    }

    public int getPendingVisits() {
        return pendingVisits;
    }

    public void setPendingVisits(int pendingVisits) {
        this.pendingVisits = pendingVisits;
    }

    public int getMissedVisits() {
        return missedVisits;
    }

    public void setMissedVisits(int missedVisits) {
        this.missedVisits = missedVisits;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
