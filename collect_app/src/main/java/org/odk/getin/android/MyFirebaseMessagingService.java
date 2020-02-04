package org.odk.getin.android;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixplicity.easyprefs.library.Prefs;
import org.json.JSONException;
import org.json.JSONObject;
import org.odk.getin.android.utilities.NotificationUtils;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.DJANGO_BACKEND_URL;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ID;

/**
 * Receives FCM messages and displays a notificaiton to the user
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: started");
        NotificationUtils.showNotificationMessage(remoteMessage.getFrom(), getString(R.string.getin_reminder));
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // Inorder to send the Instance ID token to the app server.
        sendRegistrationToServer(token);
    }

    /**
     * Persist token to third-party servers.
     * @param firebase_device_id The new token.
     */
    private void sendRegistrationToServer(String firebase_device_id) {
        Timber.d("postrequest started");

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = DJANGO_BACKEND_URL + "api/v1/notifier";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefs.getString(USER_ID, "0"));
            postdata.put("firebase_device_id", firebase_device_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Timber.e("failure Response login" + mMessage);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Timber.d("onResponse: notifier" + responseBody);
                Timber.d("onResponse: notifier" + response.code());
            }
        });
    }
}
