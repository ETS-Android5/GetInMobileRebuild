
package org.odk.getin.android.retrofitmodels.appointments;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class County implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("district")
    @Expose
    private District district;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<County> CREATOR = new Creator<County>() {


        @SuppressWarnings({
            "unchecked"
        })
        public County createFromParcel(Parcel in) {
            return new County(in);
        }

        public County[] newArray(int size) {
            return (new County[size]);
        }

    }
    ;

    protected County(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.district = ((District) in.readValue((District.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public County() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(district);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
