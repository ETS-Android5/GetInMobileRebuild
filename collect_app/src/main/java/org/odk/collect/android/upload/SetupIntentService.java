package org.odk.collect.android.upload;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.telephony.TelephonyManager;

import org.odk.collect.android.provider.appointmentstable.AppointmentstableColumns;
import org.odk.collect.android.provider.appointmentstable.AppointmentstableContentValues;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableColumns;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableContentValues;
import org.odk.collect.android.provider.userstable.UserstableColumns;
import org.odk.collect.android.provider.userstable.UserstableContentValues;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.appointments.Appointment;
import org.odk.collect.android.retrofitmodels.appointments.Appointments;
import org.odk.collect.android.retrofitmodels.appointments.Girl;
import org.odk.collect.android.retrofitmodels.mappedgirls.MappedGirl;
import org.odk.collect.android.retrofitmodels.mappedgirls.MappedGirlObject;
import org.odk.collect.android.retrofitmodels.systemusers.SystemUsers;
import org.odk.collect.android.retrofitmodels.systemusers.UserSystemModel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Downloads list of mapped girls
 */
public class SetupIntentService extends IntentService {
    private static final String TAG = SetupIntentService.class.getSimpleName();
    private APIInterface apiInterface;

    public SetupIntentService() {
        super(SetupIntentService.class.getSimpleName());
    }

    public SetupIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("onHandleIntent: started service");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        boolean isConnectedToInternet = isConnectedToInternet(this);
        Timber.d("is connected %s", isConnectedToInternet);

        if (isConnectedToInternet) {
            try {
                loadMappedGirls();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadAppointments();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadUsers() {
        Timber.d("get users started");
        Call<UserSystemModel> call = apiInterface.getUsers();

        call.enqueue(new Callback<UserSystemModel>() {
            @Override
            public void onResponse(Call<UserSystemModel> call, Response<UserSystemModel> response) {
                try {
                    Timber.d("Users: " + response.code());
                    Timber.d("Users: " + response.body());
                    if (response.code() == 200) {
                        saveUsers(response.body());
                    } else {
                        Timber.e("Failed to get users");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Timber.e("Failed to get users");
                }
            }

            @Override
            public void onFailure(Call<UserSystemModel> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveUsers(UserSystemModel userSystemModel) {
        Timber.d("INSERT: users starting");
        if (userSystemModel == null)
            throw new NullPointerException("User not found");
        List<SystemUsers> users = userSystemModel.getSystemUsers();


        long deleted = 0;
        try {
            if (users.size() > 1)
                deleted = getContentResolver().delete(UserstableColumns.CONTENT_URI, null, null);
            Timber.d("deleted data count %s", deleted);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (SystemUsers user : users) {
            Timber.d("SystemUser data: " + user.getFirstName() + user.getRole());
            UserstableContentValues values = new UserstableContentValues();
            values.putFirstname(user.getFirstName());
            values.putLastname(user.getLastName());
            values.putPhonenumber(user.getPhone());
            values.putCreatedAt(user.getCreatedAt());
            try {
                values.putMidwifeidNull();
//                values.putMidwifeid(user.getMidwife());
            } catch (Exception e) {
                e.printStackTrace();
                Timber.e("Error adding midwife");
                values.putMidwifeidNull();

            }
            values.putNumberPlate(user.getNumberPlate());
            values.putRole(user.getRole());
            values.putVillage(String.valueOf(user.getVillage()));
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved user %s", uri);
        }
    }

    private void loadAppointments() {
        Timber.d("get appointments started");
        Call<Appointments> call = apiInterface.getAppointments();

        call.enqueue(new Callback<Appointments>() {
            @Override
            public void onResponse(Call<Appointments> call, Response<Appointments> response) {
                try {
                    Timber.d("Appointments: " + response.code());
                    Timber.d("Appointments: " + response.body());
                    if (response.code() == 200) {
                        saveAppointments(response.body());
                    } else {
                        Timber.e("Failed to get appointments");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Timber.e("Failed to get appointments");
                }
            }

            @Override
            public void onFailure(Call<Appointments> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveAppointments(Appointments appointmentsObject) {
        Timber.d("INSERT: appointments starting");
        if (appointmentsObject == null)
            throw new NullPointerException("Appointments not found");
        List<Appointment> appointments = appointmentsObject.getAppointments();

        long deleted = 0;
        try {
            if (appointments.size() > 1)
                deleted = getContentResolver().delete(AppointmentstableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (Appointment appointment : appointments) {
            Timber.d("Appointment data " + appointment.getDate().toString() + appointment.getGirl().getLastName());
            AppointmentstableContentValues values = new AppointmentstableContentValues();
            values.putFirstname(appointment.getGirl().getFirstName());
            values.putLastname(appointment.getGirl().getLastName());
            values.putPhonenumber(appointment.getGirl().getPhoneNumber());
            values.putNextofkinfirstname(appointment.getGirl().getNextOfKinFirstName());
            values.putNextofkinlastname(appointment.getGirl().getNextOfKinLastName());
            values.putNextofkinphonenumber(appointment.getGirl().getNextOfKinPhoneNumber());
            values.putEducationlevel(appointment.getGirl().getEducationLevel());
            values.putMaritalstatus(appointment.getGirl().getMaritalStatus());
            values.putAge(appointment.getGirl().getAge());
            values.putUser(appointment.getGirl().getUser());
            values.putCreatedAt(appointment.getGirl().getCreatedAt());
            values.putCompletedAllVisits(appointment.getGirl().getCompletedAllVisits());
            values.putPendingVisits(appointment.getGirl().getPendingVisits());
            values.putMissedVisits(appointment.getGirl().getMissedVisits());
            values.putServerid(appointment.getGirl().getId());
            values.putTrimester(appointment.getGirl().getTrimester());
            values.putVillage(appointment.getGirl().getVillage().getName());
            values.putVhtName(appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName());
            values.putAppointmentDate(appointment.getDate());
            values.putStatus(appointment.getStatus());
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved appointmentDate %s", uri);
        }
    }

    private void deleteData() {
        try {
            long deleted = getContentResolver().delete(MappedgirltableColumns.CONTENT_URI, null, null);
            Timber.d("deleted data count %s", deleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMappedGirls() {
        Timber.d("get mapped girls list started");
        Call<MappedGirl> call = apiInterface.getMappedGirls();

        call.enqueue(new Callback<MappedGirl>() {
            @Override
            public void onResponse(Call<MappedGirl> call, Response<MappedGirl> response) {
                Timber.d("onResponse() -> %s", response.code());
                try {
                    if (response.code() == 200) {
                        saveMappedGirls(response.body());
                    } else {
                        Timber.e("Failed to get mapped girls");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MappedGirl> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveMappedGirls(MappedGirl mappedGirl) {
        Timber.d("INSERT: mapped girl starting");
        if (mappedGirl == null)
            throw new NullPointerException("Mapped girls not found");
        List<MappedGirlObject> mappedGirls = mappedGirl.getGirls();

        long deleted = 0;
        try {
            if (mappedGirls.size() > 1)
                deleted = getContentResolver().delete(MappedgirltableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);


        for (MappedGirlObject girl : mappedGirls) {
            MappedgirltableContentValues values = new MappedgirltableContentValues();
            Timber.d("MappedGirlObject data " + girl.getAge() + girl.getMaritalStatus());
            values.putFirstname(girl.getFirstName());
            values.putLastname(girl.getLastName());
            values.putPhonenumber(girl.getPhoneNumber());
//            values.putNextofkinfirstnameNull();
//            values.putNextofkinlastnameNull();
//            values.putNextofkinfirstname(girl.getNextOfKinFirstName());
//            values.putNextofkinlastname(girl.getNextOfKinLastName());
            values.putNextofkinphonenumber(girl.getNextOfKinPhoneNumber());
            values.putEducationlevel(girl.getEducationLevel());
            values.putMaritalstatus(girl.getMaritalStatus());
            values.putAge(girl.getAge());
            values.putUser(girl.getUser());
            values.putCreatedAt(girl.getCreatedAt());
            values.putCompletedAllVisits(girl.getCompletedAllVisits());
            values.putPendingVisits(girl.getPendingVisits());
            values.putMissedVisits(girl.getMissedVisits());
//            values.putVillage(girl.getVillage().getName());
            values.putServerid(girl.getId());
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved mapped girl %s", uri);
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        try {
                            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com").openConnection());
                            urlc.setRequestProperty("User-Agent", "Test");
                            urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(2000);
                            urlc.setReadTimeout(2000);
                            urlc.connect();
                            boolean isConnected = (urlc.getResponseCode() == 200);
                            urlc.disconnect();
                            // making a network request on 2G network will not wield proper results
                            // instead depend on the CONNECTED state of the network
                            if (!isConnected && getNetworkClass(context) == 2) {
                                isConnected = info[i].getState() == NetworkInfo.State.CONNECTED;
                            }
                            return isConnected;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
        }
        return false;
    }

    public static int getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 2;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return 3;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return 4;
            default:
                return 3;
        }
    }
}
