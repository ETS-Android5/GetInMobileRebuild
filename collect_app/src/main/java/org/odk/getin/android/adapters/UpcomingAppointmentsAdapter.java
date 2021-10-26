package org.odk.getin.android.adapters;

import static org.odk.getin.android.utilities.ApplicationConstants.APPOINTMENT_FORM_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.APPOINTMENT_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_REDEEMED_SERVICES;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_VOUCHER_NUMBER;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ROLE;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.R;
import org.odk.getin.android.provider.FormsProviderAPI;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableCursor;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableSelection;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.getin.android.retrofitmodels.Value;
import org.odk.getin.android.tasks.ServerPollingJob;
import org.odk.getin.android.utilities.ApplicationConstants;
import org.odk.getin.android.utilities.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import timber.log.Timber;

public class UpcomingAppointmentsAdapter extends RecyclerView.Adapter<UpcomingAppointmentsAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_PHONE_CALL = 34;
    private AppointmentstableCursor cursor;
    private UpcomingAppointmentsAdapter.ItemClickListener mClickListener;
    private Activity activity;
    private SimpleDateFormat simpleformat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phoneNumber;
        public TextView age;
        public TextView maritalStatus;
        public TextView village;
        public TextView appointmentStatus;
        public TextView appointmentDate;
        public TextView voucherExpiryDate;
        public TextView voucherNumber;
        public TextView servicesReceived;
        public Button followUpButton;
        public Button appointmentButton;
        public Button postNatalButton;
        public ImageView mappedGirlIcon;
        public ImageButton callGirlButton;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            phoneNumber = (TextView) v.findViewById(R.id.phone_number);
            maritalStatus = (TextView) v.findViewById(R.id.marital_status);
            age = (TextView) v.findViewById(R.id.age);
            village = (TextView) v.findViewById(R.id.village);
            appointmentDate = (TextView) v.findViewById(R.id.appointment_date);
            voucherExpiryDate = (TextView) v.findViewById(R.id.voucher_expiry_date);
            appointmentStatus = (TextView) v.findViewById(R.id.appointment_status);
            voucherNumber = (TextView) v.findViewById(R.id.voucher_number);
            servicesReceived = (TextView) v.findViewById(R.id.services_received);
            followUpButton = (Button) v.findViewById(R.id.create_follow_up_button);
            appointmentButton = (Button) v.findViewById(R.id.create_upcoming_appointment_button);
            postNatalButton = (Button) v.findViewById(R.id.create_post_natal_button);
            mappedGirlIcon = (ImageView) v.findViewById(R.id.mapped_girl_icon);
            callGirlButton = (ImageButton) v.findViewById(R.id.call_girl_button);
        }
    }

    public UpcomingAppointmentsAdapter(Activity activity, AppointmentstableCursor cursor) {
        this.cursor = cursor;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UpcomingAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_appointments_row, parent, false);
        return new UpcomingAppointmentsAdapter.ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull final UpcomingAppointmentsAdapter.ViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);
            holder.name.setText(String.format("%s %s", cursor.getFirstname(), cursor.getLastname()));

            try {
                if (!TextUtils.isEmpty(cursor.getVoucherNumber()))
                    holder.voucherNumber.setText(String.format(activity.getString(R.string.voucher_number_string),
                            cursor.getVoucherNumber()));
                else
                    holder.voucherNumber.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (!TextUtils.isEmpty(cursor.getServicesReceived()))
                    holder.servicesReceived.setText(String.format(activity.getString(R.string.services_received_string),
                            cursor.getServicesReceived()));
                else
                    holder.servicesReceived.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.maritalStatus.setText(org.odk.getin.android.utilities
                    .TextUtils.toCapitalize(cursor.getMaritalstatus()));

            try {
                holder.village.setText(cursor.getVillage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            final String phoneNumber = getActivePhoneNumber(cursor);
            holder.phoneNumber.setText(phoneNumber);
            try {
                holder.age.setText(String.format(Locale.ENGLISH, "%d Years", cursor.getAge()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.appointmentStatus.setText(cursor.getStatus());
            String date = simpleformat.format(cursor.getAppointmentDate());
            holder.appointmentDate.setText(activity.getString(R.string.appointment_date, date));

            MappedgirltableSelection mappedgirltableSelection = new MappedgirltableSelection();
            mappedgirltableSelection.phonenumber(phoneNumber);
            MappedgirltableCursor mappedgirltableCursor = mappedgirltableSelection.query(activity.getContentResolver());

            if (mappedgirltableCursor.moveToFirst()) {
                if (mappedgirltableCursor.getVoucherExpiryDate() != null)
                    holder.voucherExpiryDate.setText(activity.getString(R.string.voucher_expiry_string, simpleformat.format(mappedgirltableCursor.getVoucherExpiryDate())));
                else
                    holder.voucherExpiryDate.setVisibility(View.GONE);
            } else {
                holder.voucherExpiryDate.setVisibility(View.GONE);
            }

            if (cursor.getStatus().equals("Missed")) {
                holder.mappedGirlIcon.setBackground(this.activity.getResources().getDrawable(R.drawable.circular_view_red));
                holder.appointmentStatus.setTextColor(this.activity.getResources().getColor(R.color.light_red));
            } else if (cursor.getStatus().equals("Attended")) {
                holder.mappedGirlIcon.setBackground(this.activity.getResources().getDrawable(R.drawable.circular_view_green));
                holder.appointmentStatus.setTextColor(this.activity.getResources().getColor(R.color.light_green));
            } else {
                holder.mappedGirlIcon.setBackground(this.activity.getResources().getDrawable(R.drawable.circular_view_orange));
                holder.appointmentStatus.setTextColor(this.activity.getResources().getColor(R.color.light_orange));
            }

            if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE)) {
                holder.appointmentButton.setVisibility(View.GONE);
            } else {
                holder.appointmentButton.setOnClickListener(v -> {
                    saveCredentialsInSharedPrefs(holder);
                    if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
                        startFormActivity(APPOINTMENT_FORM_ID);
                    else
                        startFormActivity(APPOINTMENT_FORM_MIDWIFE_ID);
                });
            }


            holder.callGirlButton.setOnClickListener(v -> {

                try {
                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                    }
                } catch (ActivityNotFoundException e) {
                    Timber.e(e);
                    activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
                }
            });
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void saveCredentialsInSharedPrefs(@NonNull UpcomingAppointmentsAdapter.ViewHolder holder) {
        String girlName = holder.name.getText().toString();
        MappedgirltableCursor girlCursor = queryMappedGirlsTable(girlName.split(" ")[0]);
        girlCursor.moveToFirst();
        Prefs.putString(GIRL_NAME, girlName);
        Prefs.putString(GIRL_ID, girlCursor.getServerid());
        if (girlCursor.getVoucherNumber() != null) {
            Prefs.putString(GIRL_VOUCHER_NUMBER, girlCursor.getVoucherNumber());
            Prefs.putString(GIRL_REDEEMED_SERVICES, TextUtils.isEmpty(
                    girlCursor.getServicesReceived()) ? "None" : girlCursor.getServicesReceived());
        }
    }

    private AppointmentstableCursor queryAppointmentTable(String girlName) {
        return new AppointmentstableSelection().firstnameContains(girlName).or()
                .lastnameContains(girlName).orderByCreatedAt(true)
                .query(this.activity.getContentResolver());
    }

    private MappedgirltableCursor queryMappedGirlsTable(String text) {
        MappedgirltableSelection selection = new MappedgirltableSelection();
        selection.firstnameContains(text).or().lastnameContains(text);
        return selection.query(activity.getContentResolver());
    }

    private String getActivePhoneNumber(AppointmentstableCursor cursor) {
        // use girl or next of kin phone number
        String phoneNumber = cursor.getPhonenumber();
        if (TextUtils.isEmpty(phoneNumber))
            phoneNumber = cursor.getNextofkinphonenumber();
        return phoneNumber;
    }

    // allows clicks events to be caught
    public void setClickListener(UpcomingAppointmentsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, Value value);
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 0 : cursor.getCount();
    }

    private void startFormActivity(String formId) {
        String selectionClause = null;
        try {
            selectionClause = FormsProviderAPI.FormsColumns.JR_FORM_ID + " LIKE ?";
            String[] selectionArgs = {formId + "%"};

            Cursor c = activity.getContentResolver().query(
                    FormsProviderAPI.FormsColumns.CONTENT_URI,  // The content URI of the words table
                    null,                       // The columns to return for each row
                    selectionClause,                  // Either null, or the word the user entered
                    selectionArgs,                    // Either empty, or the string the user entered
                    null);

            c.moveToFirst();

            Uri formUri = ContentUris.withAppendedId(FormsProviderAPI.FormsColumns.CONTENT_URI,
                    c.getLong(c.getColumnIndex(FormsProviderAPI.FormsColumns._ID)));

            Intent intent = new Intent(Intent.ACTION_EDIT, formUri);
            intent.putExtra(ApplicationConstants.BundleKeys.FORM_MODE, ApplicationConstants.FormModes.EDIT_SAVED);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showLongToast("Please connect to Internet and try again");
            // download all empty forms from the server. this is required before user can fill in the form
            ServerPollingJob.startJobImmediately();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String phoneNumber = getActivePhoneNumber(cursor);
                    activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                }
                return;
            }
        }
    }

}
