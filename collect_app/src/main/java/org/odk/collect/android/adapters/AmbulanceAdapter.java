package org.odk.collect.android.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import org.odk.collect.android.R;
import org.odk.collect.android.provider.userstable.UserstableCursor;
import org.odk.collect.android.retrofitmodels.Value;

import timber.log.Timber;

import static org.odk.collect.android.utilities.TextUtils.toCapitalize;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceAdapter.ViewHolder> implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int REQUEST_PHONE_CALL = 34;
    private UserstableCursor cursor;
    Activity activity;
    private ItemClickListener mClickListener;
    private String phoneNumber;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button callButton;
        public TextView name;
        public TextView numberPlate;
        public TextView phoneNumber;
//        public TextView age;
//        public TextView healthCenter;
//        public TextView subcounty;
        public Button postNatalButton;
        public ImageButton callGirlButton;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            numberPlate = (TextView) v.findViewById(R.id.number_plate);
//            healthCenter = (TextView) v.findViewById(R.id.health_center);
//            age = (TextView) v.findViewById(R.id.age);
//            subcounty = (TextView) v.findViewById(R.id.subcounty);
            callButton = (Button) v.findViewById(R.id.call_button);
            phoneNumber = (TextView) v.findViewById(R.id.phone_number);
        }
    }

    public AmbulanceAdapter(Activity activity, UserstableCursor cursor) {
        this.cursor = cursor;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AmbulanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ambulance_row, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(@NonNull final AmbulanceAdapter.ViewHolder holder, int position) {
        try {
            Timber.d("onbindviewholder called ");
            cursor.moveToPosition(position);
            Timber.d("add values " + cursor.getFirstname());

            holder.name.setText(cursor.getFirstname() + " "
                    + cursor.getLastname());

            try {
                holder.numberPlate.setText(cursor.getNumberPlate());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                holder.phoneNumber.setText(cursor.getPhonenumber());
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.callButton.setOnClickListener(v -> {
                phoneNumber = holder.phoneNumber.getText().toString();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activity.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
                }
                return;
            }
        }
    }
}
