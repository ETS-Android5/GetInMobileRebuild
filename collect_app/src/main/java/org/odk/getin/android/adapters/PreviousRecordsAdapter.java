package org.odk.getin.android.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.getin.android.R;
import org.odk.getin.android.retrofitmodels.Value;
import org.odk.getin.android.utilities.ToastUtils;

import java.util.List;

import timber.log.Timber;

public class PreviousRecordsAdapter extends RecyclerView.Adapter<PreviousRecordsAdapter.ViewHolder>   {

    private List<Value> mappedGirlsList;
    private Cursor cursor;
    Context context;
    private PreviousRecordsAdapter.ItemClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView ancVisitDate;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
//            ancVisitDate = (TextView) v.findViewById(R.id.anc_visit_date);
        }
    }

    public PreviousRecordsAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        this.context = context.getApplicationContext();
    }

    public PreviousRecordsAdapter(Context context, List<Value> mappedGirlsList) {
        this.mappedGirlsList = mappedGirlsList;
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public PreviousRecordsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_appointments_row, parent, false);
        return new PreviousRecordsAdapter.ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull final PreviousRecordsAdapter.ViewHolder holder, int position) {
        try {
            Value value = mappedGirlsList.get(position);

            holder.itemView.setOnClickListener(v -> {
                ToastUtils.showShortToast("Clicked ");
                if (mClickListener != null)
                    mClickListener.onItemClick(v, position, value);
            });
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(PreviousRecordsAdapter.ItemClickListener itemClickListener) {
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
}
