
package org.odk.getin.android.retrofitmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class System {

    @SerializedName("submissionDate")
    @Expose
    private String submissionDate;
    @SerializedName("submitterId")
    @Expose
    private String submitterId;
    @SerializedName("submitterName")
    @Expose
    private String submitterName;
    @SerializedName("status")
    @Expose
    private Object status;

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

}
