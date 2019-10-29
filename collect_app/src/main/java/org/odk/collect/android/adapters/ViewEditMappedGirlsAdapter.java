package org.odk.collect.android.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.PregnancySummaryActivity;
import org.odk.collect.android.provider.FormsProviderAPI;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.retrofitmodels.mappedgirls.Result;
import org.odk.collect.android.utilities.ApplicationConstants;
import org.odk.collect.android.utilities.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

public class ViewEditMappedGirlsAdapter extends RecyclerView.Adapter<ViewEditMappedGirlsAdapter.ViewHolder>   {

    private List<Result> mappedGirlsList;
    private Cursor cursor;
    Context context;
    private ItemClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phoneNumber;
        public TextView age;
        public TextView maritalStatus;
        public TextView village;
        public TextView appointment;
        public Button followUpButton;
        public Button appointmentButton;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            phoneNumber = (TextView) v.findViewById(R.id.phone_number);
            maritalStatus = (TextView) v.findViewById(R.id.marital_status);
            age = (TextView) v.findViewById(R.id.age);
            village = (TextView) v.findViewById(R.id.village);
//            appointment = (TextView) v.findViewById(R.id.upcomingappointments);
            followUpButton = (Button) v.findViewById(R.id.create_follow_up_button);
            appointmentButton = (Button) v.findViewById(R.id.upcoming_appointments_button);
        }
    }

    public ViewEditMappedGirlsAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        this.context = context.getApplicationContext();
    }

    public ViewEditMappedGirlsAdapter(Context context, List<Result> mappedGirlsList) {
        this.mappedGirlsList = mappedGirlsList;
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public ViewEditMappedGirlsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_edit_mapped_girls_row, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewEditMappedGirlsAdapter.ViewHolder holder, int position) {
        try {
            Timber.d("onbindviewholder called");

            Result girl = mappedGirlsList.get(position);
            Timber.d("add values " + girl.toString());
            Timber.d(girl.getLastName());
            holder.name.setText(girl.getFirstName() + " "
                    + girl.getLastName());
            holder.phoneNumber.setText(girl.getPhoneNumber());
            holder.age.setText(girl.getDob());
//            holder.age.setText(girl.getGIRLSDEMOGRAPHIC().getDOB());

            holder.followUpButton.setOnClickListener(v -> {
                Timber.d("startFollowUpActivity called");
                startFollowUpActivity();
            });
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
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
        //todo# load data from database
        return (mappedGirlsList == null) ? 10 : mappedGirlsList.size();
    }

    private void startFollowUpActivity() {
        String selectionClause = FormsProviderAPI.FormsColumns.JR_FORM_ID + " LIKE ?";
        String[] selectionArgs = {"build_GetINTestFollowup2_15713999999%"};

        Cursor c = context.getContentResolver().query(
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
        context.startActivity(intent);
    }
}
