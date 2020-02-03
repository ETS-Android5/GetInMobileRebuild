package org.odk.getin.android.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.odk.getin.android.utilities.ApplicationConstants.SHOW_NETWORK_STATUS;

public class GeneralUtils {
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        try {
                            //todo replace with async task
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

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

    public static void saveShowNetworkStatus(boolean status) {
        Prefs.putBoolean(SHOW_NETWORK_STATUS, status);
    }

    public static boolean getShowNetworkStatus() {
        Log.d("SharedPref", "saveMapObject: called");
        return Prefs.getBoolean(SHOW_NETWORK_STATUS, true);
    }
}
