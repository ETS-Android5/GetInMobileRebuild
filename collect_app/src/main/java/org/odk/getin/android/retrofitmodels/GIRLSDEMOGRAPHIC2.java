
package org.odk.getin.android.retrofitmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GIRLSDEMOGRAPHIC2 {

    @SerializedName("GirlsPhoneNumber")
    @Expose
    private String girlsPhoneNumber;
    @SerializedName("PowerHolderNumber")
    @Expose
    private String powerHolderNumber;

    public String getGirlsPhoneNumber() {
        return girlsPhoneNumber;
    }

    public void setGirlsPhoneNumber(String girlsPhoneNumber) {
        this.girlsPhoneNumber = girlsPhoneNumber;
    }

    public String getPowerHolderNumber() {
        return powerHolderNumber;
    }

    public void setPowerHolderNumber(String powerHolderNumber) {
        this.powerHolderNumber = powerHolderNumber;
    }

}
