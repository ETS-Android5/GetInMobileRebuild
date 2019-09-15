package org.odk.collect.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.collect.android.R;
import org.odk.collect.android.retrofitmodels.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

public class ViewEditMappedGirlsAdapter extends RecyclerView.Adapter<ViewEditMappedGirlsAdapter.ViewHolder>   {

    private List<Value> mappedGirlsList;
    private Cursor cursor;
    Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phoneNumber;
        public TextView dob;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            phoneNumber = (TextView) v.findViewById(R.id.phone_number);
            dob = (TextView) v.findViewById(R.id.dob);
        }
    }

    public ViewEditMappedGirlsAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        this.context = context;
    }

    public ViewEditMappedGirlsAdapter(Context context, List<Value> mappedGirlsList) {
        this.mappedGirlsList = mappedGirlsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewEditMappedGirlsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_edit_mapped_girls_row, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewEditMappedGirlsAdapter.ViewHolder holder, int position) {
        try {
            Value value = mappedGirlsList.get(position);
            holder.name.setText(value.getGIRLSDEMOGRAPHIC().getFirstName() + " "
                    + value.getGIRLSDEMOGRAPHIC().getLastName());
            holder.phoneNumber.setText(value.getGIRLSDEMOGRAPHIC2().getGirlsPhoneNumber());
            holder.dob.setText(value.getGIRLSDEMOGRAPHIC().getDOB());
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        //todo# load data from database
        if (mappedGirlsList == null)
            return 10;
        return mappedGirlsList.size();
    }
}
