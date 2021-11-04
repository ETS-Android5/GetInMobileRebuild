package org.odk.getin.android.adapters;

import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_NAME;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_REDEEMED_SERVICES;
import static org.odk.getin.android.utilities.ApplicationConstants.GIRL_VOUCHER_NUMBER;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.MainMenuActivity;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableCursor;
import org.odk.getin.android.provider.appointmentstable.AppointmentstableSelection;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.getin.android.retrofitmodels.Value;
import timber.log.Timber;

public class HealthFacilityAdapter extends RecyclerView.Adapter<HealthFacilityAdapter.ViewHolder> {

    private AppointmentstableCursor cursor;
    private HealthFacilityAdapter.ItemClickListener mClickListener;
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView facilityIcon;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            facilityIcon = (ImageView) v.findViewById(R.id.health_facility_icon);
        }
    }

    public HealthFacilityAdapter(Activity activity, AppointmentstableCursor cursor) {
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
//            cursor.moveToPosition(position);
//            holder.name.setText(String.format("%s %s", cursor.getFirstname(), cursor.getLastname()));
            String x = "Yumbe Health facility II";
            holder.name.setText(x);

            holder.facilityIcon.setImageDrawable(generateTextDrawable("A"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, MainMenuActivity.class).putExtra("district", "Arua"));
                }
            });
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    public static TextDrawable generateTextDrawable(String character) {
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color1 = generator.getColor(3);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(110)  // width in px
                .height(110) // height in px
                .endConfig()
                .buildRound(character, color1);
        return drawable;
    }

    private void saveCredentialsInSharedPrefs(@NonNull HealthFacilityAdapter.ViewHolder holder) {
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
    public void setClickListener(HealthFacilityAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, Value value);
    }

    @Override
//    public int getItemCount() {
//        return (cursor == null) ? 0 : cursor.getCount();
//    }
    public int getItemCount() {
        return 10;
    }
}
