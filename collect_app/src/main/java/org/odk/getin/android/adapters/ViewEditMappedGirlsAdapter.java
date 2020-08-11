package org.odk.getin.android.adapters;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.ProfileActivity;
import org.odk.getin.android.provider.FormsProviderAPI;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.getin.android.retrofitmodels.Value;
import org.odk.getin.android.retrofitmodels.systemusers.Midwife;
import org.odk.getin.android.tasks.ServerPollingJob;
import org.odk.getin.android.utilities.ApplicationConstants;
import org.odk.getin.android.utilities.ToastUtils;

import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.APPOINTMENT_FORM_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.APPOINTMENT_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.EDIT_GIRL;
import static org.odk.getin.android.utilities.ApplicationConstants.FOLLOW_UP_FORM_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.FOLLOW_UP_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_ARUA_FORM_CHEW_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_ARUA_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_BUNDIBUGYO_FORM_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_BUNDIBUGYO_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_FIRST_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_LAST_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_KAMPALA_FORM_CHEW_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MAP_GIRL_KAMPALA_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.MIDWIFE_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.POSTNATAL_FORM_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.POSTNATAL_FORM_MIDWIFE_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_DISTRICT;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ROLE;
import static org.odk.getin.android.utilities.TextUtils.toCapitalize;

public class ViewEditMappedGirlsAdapter extends RecyclerView.Adapter<ViewEditMappedGirlsAdapter.ViewHolder>  implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_PHONE_CALL = 34;
    private MappedgirltableCursor cursor;
    Activity activity;
    private ItemClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phoneNumber;
        public TextView age;
        public TextView maritalStatus;
        public TextView village;
        public TextView byVht;
        public Button followUpButton;
        public Button appointmentButton;
        public Button postNatalButton;
        public ImageButton callGirlButton;
        public ImageButton editGirlButton;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            phoneNumber = (TextView) v.findViewById(R.id.phone_number);
            maritalStatus = (TextView) v.findViewById(R.id.marital_status);
            age = (TextView) v.findViewById(R.id.age);
            village = (TextView) v.findViewById(R.id.village);
            followUpButton = (Button) v.findViewById(R.id.create_follow_up_button);
            if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(MIDWIFE_ROLE)) {
                appointmentButton = (Button) v.findViewById(R.id.create_upcoming_appointment_button);
                byVht = (TextView) v.findViewById(R.id.byvht);
            }
            postNatalButton = (Button) v.findViewById(R.id.create_post_natal_button);
            callGirlButton = (ImageButton) v.findViewById(R.id.call_girl_button);
            editGirlButton = (ImageButton) v.findViewById(R.id.edit_girl_button);
        }
    }

    public ViewEditMappedGirlsAdapter(Activity activity, MappedgirltableCursor cursor) {
        this.cursor = cursor;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewEditMappedGirlsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview;
        if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
            cardview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_edit_mapped_girls_row, parent, false);
        else
            cardview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_edit_mapped_girls_midwife_row, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewEditMappedGirlsAdapter.ViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);
            holder.name.setText(cursor.getFirstname() + " " + cursor.getLastname());
            holder.maritalStatus.setText(toCapitalize(cursor.getMaritalstatus()));

            try {
                holder.village.setText(toCapitalize(cursor.getVillage()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            final String phoneNumber = getActivePhoneNumber(cursor);
            holder.phoneNumber.setText(phoneNumber);
            try {
                holder.age.setText(cursor.getAge() + " Years");
                holder.village.setText(cursor.getVillage());
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.postNatalButton.setOnClickListener(v -> {
                Timber.d("clicked postnatal");
                saveCredentialsInSharedPrefs(holder);
                if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
                    startFormActivity(POSTNATAL_FORM_ID);
                else
                    startFormActivity(POSTNATAL_FORM_MIDWIFE_ID);
            });

            if (!Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
                holder.appointmentButton.setOnClickListener(v -> {
                    saveCredentialsInSharedPrefs(holder);

                    if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
                        startFormActivity(APPOINTMENT_FORM_ID);
                    else
                        startFormActivity(APPOINTMENT_FORM_MIDWIFE_ID);
                });


            holder.followUpButton.setOnClickListener(v -> {
                saveCredentialsInSharedPrefs(holder);
                if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
                    startFormActivity(FOLLOW_UP_FORM_ID);
                else
                    startFormActivity(FOLLOW_UP_FORM_MIDWIFE_ID);
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

            holder.editGirlButton.setOnClickListener(v -> {
                String girlFirstName = holder.name.getText().toString().split(" ")[0];
                String girlLastName = holder.name.getText().toString().split(" ")[1];
                Prefs.putString(GIRL_FIRST_NAME, girlFirstName);
                Prefs.putString(GIRL_LAST_NAME, girlLastName);
                Prefs.putBoolean(EDIT_GIRL, true);

                if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE)) {
                    if (Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").equals("BUNDIBUGYO"))
                        startFormActivity(MAP_GIRL_BUNDIBUGYO_FORM_ID);
                    else if (Prefs.getString(USER_DISTRICT, "Kampala").equals("Kampala"))
                        startFormActivity(MAP_GIRL_KAMPALA_FORM_CHEW_ID);
                    else
                        startFormActivity(MAP_GIRL_ARUA_FORM_CHEW_ID);
                } else {
                    if (Prefs.getString(USER_DISTRICT, "BUNDIBUGYO").equals("BUNDIBUGYO"))
                        startFormActivity(MAP_GIRL_BUNDIBUGYO_FORM_MIDWIFE_ID);
                    else if (Prefs.getString(USER_DISTRICT, "Kampala").equals("Kampala"))
                        startFormActivity(MAP_GIRL_KAMPALA_FORM_MIDWIFE_ID);
                    else
                        startFormActivity(MAP_GIRL_ARUA_FORM_MIDWIFE_ID);
                }
            });

            holder.itemView.setOnClickListener(v -> {
                String girlFirstName = holder.name.getText().toString().split(" ")[0];
                String girlLastName = holder.name.getText().toString().split(" ")[1];
                Prefs.putString(GIRL_FIRST_NAME, girlFirstName);
                Prefs.putString(GIRL_LAST_NAME, girlLastName);
                activity.startActivity(new Intent(activity.getApplicationContext(), ProfileActivity.class));
            });
        } catch (Exception e) {
            Timber.e(e);
        }

        try {
            if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(MIDWIFE_ROLE)) {
                if (!cursor.getUser().equals(Prefs.getString(USER_ID, ""))) {
                    holder.byVht.setVisibility(View.VISIBLE);
                } else {
                    holder.byVht.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void saveCredentialsInSharedPrefs(@NonNull ViewHolder holder) {
        String girlName = holder.name.getText().toString();
        MappedgirltableCursor girlCursor = queryMappedGirlsTable(girlName.split(" ")[0]);
        girlCursor.moveToFirst();
        Prefs.putString(GIRL_NAME, girlName);
        Prefs.putString(GIRL_ID, girlCursor.getServerid());
    }

    private String getActivePhoneNumber(MappedgirltableCursor cursor) {
        // use girl or next of kin phone number
        String phoneNumber = cursor.getPhonenumber();
        if (TextUtils.isEmpty(phoneNumber))
            phoneNumber = cursor.getNextofkinphonenumber();
        return phoneNumber;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, Value value);
    }

    @Override
    public int getItemCount() {
        return (cursor == null) ? 10 : cursor.getCount();
    }

    private void startFormActivity(String formId) {
        String selectionClause = null;
        try {
            selectionClause = FormsProviderAPI.FormsColumns.JR_FORM_ID + " LIKE ?";
            String[] selectionArgs = { formId + "%"};

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

    public void filter(String text) {
        if (!text.isEmpty()) {
            text = text.toLowerCase();
            swapCursor(queryMappedGirlsTable(text));
        }
    }

    public MappedgirltableCursor swapCursor(MappedgirltableCursor cursor) {
        if (this.cursor == cursor) {
            return null;
        }
        MappedgirltableCursor oldCursor = this.cursor;
        this.cursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    private MappedgirltableCursor queryMappedGirlsTable(String text) {
        MappedgirltableSelection selection = new MappedgirltableSelection();
        selection.firstnameContains(text).or().lastnameContains(text);
        return selection.query(activity.getContentResolver());
    }
}
