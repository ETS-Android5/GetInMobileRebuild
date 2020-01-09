
package org.odk.getin.android.retrofitmodels.appointments;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Village implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parish")
    @Expose
    private Parish parish;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Creator<Village> CREATOR = new Creator<Village>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Village createFromParcel(Parcel in) {
            return new Village(in);
        }

        public Village[] newArray(int size) {
            return (new Village[size]);
        }

    }
    ;

    protected Village(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.parish = ((Parish) in.readValue((Parish.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Village() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parish getParish() {
        return parish;
    }

    public void setParish(Parish parish) {
        this.parish = parish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(parish);
        dest.writeValue(name);
    }

    public int describeContents() {
        return  0;
    }

}
