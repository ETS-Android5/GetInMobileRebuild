package org.odk.getin.android.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import org.odk.getin.android.R;
import org.odk.getin.android.activities.ViewEditMappedGirlsActivity;
import org.odk.getin.android.provider.healthfacilitytable.HealthfacilitytableCursor;
import org.odk.getin.android.retrofitmodels.Value;

import timber.log.Timber;

public class HealthFacilityAdapter extends RecyclerView.Adapter<HealthFacilityAdapter.ViewHolder> {

    private HealthfacilitytableCursor cursor;
    private HealthFacilityAdapter.ItemClickListener mClickListener;
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView subCounty;
        public ImageView facilityIcon;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            subCounty = (TextView) v.findViewById(R.id.sub_county);
            facilityIcon = (ImageView) v.findViewById(R.id.health_facility_icon);
        }
    }

    public HealthFacilityAdapter(Activity activity, HealthfacilitytableCursor cursor) {
        this.cursor = cursor;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HealthFacilityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.health_facility_row, parent, false);
        return new HealthFacilityAdapter.ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull final HealthFacilityAdapter.ViewHolder holder, int position) {
        try {
            cursor.moveToPosition(position);
            holder.name.setText(cursor.getName());
            holder.subCounty.setText(cursor.getSubcounty());
            holder.facilityIcon.setImageDrawable(generateTextDrawable(cursor.getName().substring(0, 1)));
            holder.itemView.setOnClickListener(v -> activity.startActivity(new Intent(activity,
                    ViewEditMappedGirlsActivity.class).putExtra("healthfacility", cursor.getName())));
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public static TextDrawable generateTextDrawable(String character) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getColor(7);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(110)  // width in px
                .height(110) // height in px
                .endConfig()
                .buildRound(character, color1);
        return drawable;
    }

    // allows clicks events to be caught
    public void setClickListener(HealthFacilityAdapter.ItemClickListener itemClickListener) {
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
}
