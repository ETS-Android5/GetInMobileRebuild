
package org.odk.collect.android.retrofitmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("__id")
    @Expose
    private String id;
    @SerializedName("MenstruationDate")
    @Expose
    private String menstruationDate;
    @SerializedName("AttendedANCVisit")
    @Expose
    private String attendedANCVisit;
    @SerializedName("UsedContraceptives")
    @Expose
    private String usedContraceptives;
    @SerializedName("ContraceptiveMethod")
    @Expose
    private String contraceptiveMethod;
    @SerializedName("ReasonNoContraceptives")
    @Expose
    private Object reasonNoContraceptives;
    @SerializedName("VoucherCard")
    @Expose
    private String voucherCard;
    @SerializedName("VoucherNumber")
    @Expose
    private Object voucherNumber;
    @SerializedName("__system")
    @Expose
    private System system;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("GIRLSDEMOGRAPHIC")
    @Expose
    private GIRLSDEMOGRAPHIC gIRLSDEMOGRAPHIC;
    @SerializedName("GIRLSDEMOGRAPHIC2")
    @Expose
    private GIRLSDEMOGRAPHIC2 gIRLSDEMOGRAPHIC2;
    @SerializedName("DistrictsGroup")
    @Expose
    private DistrictsGroup districtsGroup;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenstruationDate() {
        return menstruationDate;
    }

    public void setMenstruationDate(String menstruationDate) {
        this.menstruationDate = menstruationDate;
    }

    public String getAttendedANCVisit() {
        return attendedANCVisit;
    }

    public void setAttendedANCVisit(String attendedANCVisit) {
        this.attendedANCVisit = attendedANCVisit;
    }

    public String getUsedContraceptives() {
        return usedContraceptives;
    }

    public void setUsedContraceptives(String usedContraceptives) {
        this.usedContraceptives = usedContraceptives;
    }

    public String getContraceptiveMethod() {
        return contraceptiveMethod;
    }

    public void setContraceptiveMethod(String contraceptiveMethod) {
        this.contraceptiveMethod = contraceptiveMethod;
    }

    public Object getReasonNoContraceptives() {
        return reasonNoContraceptives;
    }

    public void setReasonNoContraceptives(Object reasonNoContraceptives) {
        this.reasonNoContraceptives = reasonNoContraceptives;
    }

    public String getVoucherCard() {
        return voucherCard;
    }

    public void setVoucherCard(String voucherCard) {
        this.voucherCard = voucherCard;
    }

    public Object getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(Object voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public GIRLSDEMOGRAPHIC getGIRLSDEMOGRAPHIC() {
        return gIRLSDEMOGRAPHIC;
    }

    public void setGIRLSDEMOGRAPHIC(GIRLSDEMOGRAPHIC gIRLSDEMOGRAPHIC) {
        this.gIRLSDEMOGRAPHIC = gIRLSDEMOGRAPHIC;
    }

    public GIRLSDEMOGRAPHIC2 getGIRLSDEMOGRAPHIC2() {
        return gIRLSDEMOGRAPHIC2;
    }

    public void setGIRLSDEMOGRAPHIC2(GIRLSDEMOGRAPHIC2 gIRLSDEMOGRAPHIC2) {
        this.gIRLSDEMOGRAPHIC2 = gIRLSDEMOGRAPHIC2;
    }

    public DistrictsGroup getDistrictsGroup() {
        return districtsGroup;
    }

    public void setDistrictsGroup(DistrictsGroup districtsGroup) {
        this.districtsGroup = districtsGroup;
    }

}
