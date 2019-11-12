
package org.odk.collect.android.retrofitmodels.appointments;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCounty implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("county")
    @Expose
    private County county;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<SubCounty> CREATOR = new Creator<SubCounty>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SubCounty createFromParcel(Parcel in) {
            return new SubCounty(in);
        }

        public SubCounty[] newArray(int size) {
            return (new SubCounty[size]);
        }

    }
    ;

    protected SubCounty(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.county = ((County) in.readValue((County.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public SubCounty() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(county);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
