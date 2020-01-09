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

public class PostNatalAdapter extends RecyclerView.Adapter<PostNatalAdapter.ViewHolder>   {

    private List<Value> mappedGirlsList;
    private Cursor cursor;
    Context context;
    private ItemClickListener mClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView phoneNumber;
        public TextView dob;
        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            phoneNumber = (TextView) v.findViewById(R.id.phone_number);
            dob = (TextView) v.findViewById(R.id.age);
        }
    }

    public PostNatalAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        this.context = context.getApplicationContext();
    }

    public PostNatalAdapter(Context context, List<Value> mappedGirlsList) {
        this.mappedGirlsList = mappedGirlsList;
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public PostNatalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_edit_mapped_girls_row, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostNatalAdapter.ViewHolder holder, int position) {
        try {
            Value value = mappedGirlsList.get(position);
            holder.name.setText(value.getGIRLSDEMOGRAPHIC().getFirstName() + " "
                    + value.getGIRLSDEMOGRAPHIC().getLastName());
            holder.phoneNumber.setText(value.getGIRLSDEMOGRAPHIC2().getGirlsPhoneNumber());
            holder.dob.setText(value.getGIRLSDEMOGRAPHIC().getDOB());

            holder.itemView.setOnClickListener(v -> {
                ToastUtils.showShortToast("Clicked " + value.getGIRLSDEMOGRAPHIC().getFirstName());
                if (mClickListener != null)
                    mClickListener.onItemClick(v, position, value);
//                activity.startActivity(new Intent(activity.getApplicationContext(), PregnancySummaryActivity.class));
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
        return (mappedGirlsList == null) ? 0 : mappedGirlsList.size();
    }
}
