
package org.odk.collect.android.retrofitmodels.mappedgirls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

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
    private String createdAt;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
