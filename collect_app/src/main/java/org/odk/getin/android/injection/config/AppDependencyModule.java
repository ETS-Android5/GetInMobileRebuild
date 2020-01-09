package org.odk.getin.android.injection.config;

import android.app.Application;
import android.content.Context;
import android.telephony.SmsManager;
import android.webkit.MimeTypeMap;

import com.google.android.gms.analytics.Tracker;

import org.odk.getin.android.application.Collect;
import org.odk.getin.android.dao.FormsDao;
import org.odk.getin.android.dao.InstancesDao;
import org.odk.getin.android.events.RxEventBus;
import org.odk.getin.android.http.CollectServerClient;
import org.odk.getin.android.http.CollectThenSystemContentTypeMapper;
import org.odk.getin.android.http.okhttp.OkHttpConnection;
import org.odk.getin.android.http.okhttp.OkHttpOpenRosaServerClientFactory;
import org.odk.getin.android.http.openrosa.OpenRosaHttpInterface;
import org.odk.getin.android.tasks.sms.SmsSubmissionManager;
import org.odk.getin.android.tasks.sms.contracts.SmsSubmissionManagerContract;
import org.odk.getin.android.utilities.DownloadFormListUtils;
import org.odk.getin.android.utilities.PermissionUtils;
import org.odk.getin.android.utilities.WebCredentialsUtils;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Add dependency providers here (annotated with @Provides)
 * for objects you need to inject
 */
@Module
public class AppDependencyModule {

    @Provides
    public SmsManager provideSmsManager() {
        return SmsManager.getDefault();
    }

    @Provides
    SmsSubmissionManagerContract provideSmsSubmissionManager(Application application) {
        return new SmsSubmissionManager(application);
    }

    @Provides
    Context context(Application application) {
        return application;
    }

    @Provides
    public InstancesDao provideInstancesDao() {
        return new InstancesDao();
    }

    @Provides
    public FormsDao provideFormsDao() {
        return new FormsDao();
    }

    @Provides
    @Singleton
    RxEventBus provideRxEventBus() {
        return new RxEventBus();
    }

    @Provides
    MimeTypeMap provideMimeTypeMap() {
        return MimeTypeMap.getSingleton();
    }

    @Provides
    @Singleton
    OpenRosaHttpInterface provideHttpInterface(MimeTypeMap mimeTypeMap) {
        return new OkHttpConnection(
                new OkHttpOpenRosaServerClientFactory(new OkHttpClient.Builder(), Date::new),
                new CollectThenSystemContentTypeMapper(mimeTypeMap)
        );
    }

    @Provides
    CollectServerClient provideCollectServerClient(OpenRosaHttpInterface httpInterface, WebCredentialsUtils webCredentialsUtils) {
        return new CollectServerClient(httpInterface, webCredentialsUtils);
    }

    @Provides
    WebCredentialsUtils provideWebCredentials() {
        return new WebCredentialsUtils();
    }

    @Provides
    DownloadFormListUtils provideDownloadFormListUtils(
            Application application,
            CollectServerClient collectServerClient,
            WebCredentialsUtils webCredentialsUtils,
            FormsDao formsDao) {
        return new DownloadFormListUtils(
                application,
                collectServerClient,
                webCredentialsUtils,
                formsDao
        );
    }

    @Provides
    @Singleton
    public Tracker providesTracker(Application application) {
        return ((Collect) application).getDefaultTracker();
    }

    @Provides
    public PermissionUtils providesPermissionUtils() {
        return new PermissionUtils();
    }
}
