
package org.odk.getin.android.retrofitmodels.appointments;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parish implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sub_county")
    @Expose
    private SubCounty subCounty;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<Parish> CREATOR = new Creator<Parish>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Parish createFromParcel(Parcel in) {
            return new Parish(in);
        }

        public Parish[] newArray(int size) {
            return (new Parish[size]);
        }

    }
    ;

    protected Parish(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.subCounty = ((SubCounty) in.readValue((SubCounty.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Parish() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(subCounty);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
