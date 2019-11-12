package org.odk.collect.android.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
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

import org.odk.collect.android.R;
import org.odk.collect.android.provider.FormsProviderAPI;
import org.odk.collect.android.provider.appointmentstable.AppointmentstableCursor;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.utilities.ApplicationConstants;

import timber.log.Timber;

import static org.odk.collect.android.utilities.ApplicationConstants.APPOINTMENT_FORM_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.APPOINTMENT_FORM_MIDWIFE_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.collect.android.utilities.ApplicationConstants.GIRL_ID;
import static org.odk.collect.android.utilities.ApplicationConstants.GIRL_NAME;
import static org.odk.collect.android.utilities.ApplicationConstants.USER_ROLE;

public class UpcomingAppointmentsAdapter extends RecyclerView.Adapter<UpcomingAppointmentsAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_PHONE_CALL = 34;
    private AppointmentstableCursor cursor;
    private UpcomingAppointmentsAdapter.ItemClickListener mClickListener;
    Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phoneNumber;
        public TextView age;
        public TextView maritalStatus;
        public TextView village;
        public TextView appointmentStatus;
        public TextView appointmentDate;
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
            appointmentStatus = (TextView) v.findViewById(R.id.appointment_status);
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
            Timber.d("onbindviewholder called");
            cursor.moveToPosition(position);
            Timber.d("add values " + cursor.getFirstname());
            holder.name.setText(cursor.getFirstname() + " " + cursor.getLastname());
            holder.maritalStatus.setText(cursor.getMaritalstatus());

            final String phoneNumber = getActivePhoneNumber(cursor);
            holder.phoneNumber.setText(phoneNumber);
            try {
                holder.age.setText(cursor.getAge() + " Years");
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.appointmentStatus.setText(cursor.getStatus());
            holder.appointmentDate.setText("Appointment Date: " + cursor.getAppointmentDate().toString().split("GMT")[0]);

            Timber.d(cursor.getStatus());
            Timber.d(String.valueOf(cursor.getStatus().equals("Missed")));
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

            holder.appointmentButton.setOnClickListener(v -> {
                Timber.d("clicked appointmentDate");
                Prefs.putString(GIRL_ID, cursor.getServerid());
                Prefs.putString(GIRL_NAME, holder.name.getText().toString());
                if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
                    startFormActivity(APPOINTMENT_FORM_ID);
                else
                    startFormActivity(APPOINTMENT_FORM_MIDWIFE_ID);
            });

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


            // if logged in user is midwife remove the call button
            // holder.callGirlButton.setVisibility(View.GONE);
        } catch (Exception e) {
            Timber.e(e);
        }
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
        String selectionClause = FormsProviderAPI.FormsColumns.JR_FORM_ID + " LIKE ?";
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
