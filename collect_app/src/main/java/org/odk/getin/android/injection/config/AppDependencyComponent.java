package org.odk.getin.android.injection.config;

import android.app.Application;
import android.telephony.SmsManager;

import org.odk.getin.android.activities.FormDownloadList;
import org.odk.getin.android.activities.FormEntryActivity;
import org.odk.getin.android.activities.GoogleDriveActivity;
import org.odk.getin.android.activities.GoogleSheetsUploaderActivity;
import org.odk.getin.android.activities.InstanceUploaderListActivity;
import org.odk.getin.android.activities.MainMenuActivity;
import org.odk.getin.android.adapters.InstanceUploaderAdapter;
import org.odk.getin.android.application.Collect;
import org.odk.getin.android.events.RxEventBus;
import org.odk.getin.android.fragments.DataManagerList;
import org.odk.getin.android.http.CollectServerClient;
import org.odk.getin.android.http.openrosa.OpenRosaHttpInterface;
import org.odk.getin.android.logic.PropertyManager;
import org.odk.getin.android.preferences.ServerPreferencesFragment;
import org.odk.getin.android.tasks.InstanceServerUploaderTask;
import org.odk.getin.android.tasks.ServerPollingJob;
import org.odk.getin.android.tasks.sms.SmsNotificationReceiver;
import org.odk.getin.android.tasks.sms.SmsSender;
import org.odk.getin.android.tasks.sms.SmsSentBroadcastReceiver;
import org.odk.getin.android.tasks.sms.SmsService;
import org.odk.getin.android.tasks.sms.contracts.SmsSubmissionManagerContract;
import org.odk.getin.android.utilities.AuthDialogUtility;
import org.odk.getin.android.utilities.DownloadFormListUtils;
import org.odk.getin.android.utilities.FormDownloader;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Dagger component for the application. Should include
 * application level Dagger Modules and be built with Application
 * object.
 *
 * Add an `inject(MyClass myClass)` method here for objects you want
 * to inject into so Dagger knows to wire it up.
 *
 * Annotated with @Singleton so modules can include @Singletons that will
 * be retained at an application level (as this an instance of this components
 * is owned by the Application object).
 *
 * If you need to call a provider directly from the component (in a test
 * for example) you can add a method with the type you are looking to fetch
 * (`MyType myType()`) to this interface.
 *
 * To read more about Dagger visit: https://google.github.io/dagger/users-guide
 **/

@Singleton
@Component(modules = {
        AppDependencyModule.class
})
public interface AppDependencyComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        Builder appDependencyModule(AppDependencyModule testDependencyModule);

        AppDependencyComponent build();
    }

    void inject(Collect collect);

    void inject(SmsService smsService);

    void inject(SmsSender smsSender);

    void inject(SmsSentBroadcastReceiver smsSentBroadcastReceiver);

    void inject(SmsNotificationReceiver smsNotificationReceiver);

    void inject(InstanceUploaderAdapter instanceUploaderAdapter);

    void inject(DataManagerList dataManagerList);

    void inject(PropertyManager propertyManager);

    void inject(FormEntryActivity formEntryActivity);

    void inject(InstanceServerUploaderTask uploader);

    void inject(CollectServerClient collectClient);

    void inject(ServerPreferencesFragment serverPreferencesFragment);

    void inject(FormDownloader formDownloader);

    void inject(ServerPollingJob serverPollingJob);

    void inject(AuthDialogUtility authDialogUtility);

    void inject(FormDownloadList formDownloadList);

    void inject(MainMenuActivity mainMenuActivity);

    void inject(InstanceUploaderListActivity activity);

    void inject(GoogleDriveActivity googleDriveActivity);

    void inject(GoogleSheetsUploaderActivity googleSheetsUploaderActivity);

    SmsManager smsManager();

    SmsSubmissionManagerContract smsSubmissionManagerContract();

    RxEventBus rxEventBus();

    OpenRosaHttpInterface openRosaHttpInterface();

    DownloadFormListUtils downloadFormListUtils();
}
