package org.odk.collect.android.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.collect.android.R;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.utilities.ToastUtils;

import java.util.List;

import timber.log.Timber;

public class UpcomingAppointmentsAdapter extends RecyclerView.Adapter<UpcomingAppointmentsAdapter.ViewHolder>   {

    private List<Value> mappedGirlsList;
    private Cursor cursor;
    Context context;
    private UpcomingAppointmentsAdapter.ItemClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView ancVisitDate;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            ancVisitDate = (TextView) v.findViewById(R.id.anc_visit_date);
        }
    }

    public UpcomingAppointmentsAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        this.context = context.getApplicationContext();
    }

    public UpcomingAppointmentsAdapter(Context context, List<Value> mappedGirlsList) {
        this.mappedGirlsList = mappedGirlsList;
        this.context = context.getApplicationContext();
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
            Value value = mappedGirlsList.get(position);
            holder.name.setText(value.getGIRLSDEMOGRAPHIC().getFirstName() + " "
                    + value.getGIRLSDEMOGRAPHIC().getLastName());
            holder.ancVisitDate.setText(value.getGIRLSDEMOGRAPHIC().getDOB());

            holder.itemView.setOnClickListener(v -> {
                ToastUtils.showShortToast("Clicked " + value.getGIRLSDEMOGRAPHIC().getFirstName());
                if (mClickListener != null)
                    mClickListener.onItemClick(v, position, value);
//                context.startActivity(new Intent(context.getApplicationContext(), PregnancySummaryActivity.class));
            });
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
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
        //todo# load data from database
        return (mappedGirlsList == null) ? 0 : mappedGirlsList.size();
    }
}
