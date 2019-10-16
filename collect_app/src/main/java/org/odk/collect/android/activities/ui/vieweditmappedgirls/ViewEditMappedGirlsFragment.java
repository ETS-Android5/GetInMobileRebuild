package org.odk.collect.android.activities.ui.vieweditmappedgirls;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.PregnancySummaryActivity;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.collect.android.provider.InstanceProviderAPI;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.MappedGirls;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.utilities.ApplicationConstants;
import org.odk.collect.android.utilities.ToastUtils;
import org.odk.collect.android.provider.InstanceProviderAPI.InstanceColumns;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class ViewEditMappedGirlsFragment extends Fragment implements ViewEditMappedGirlsAdapter.ItemClickListener {

    private ViewEditMappedGirlsViewModel mViewModel;
    View rootView;
    private RecyclerView recyclerView;
    private APIInterface apiInterface;
    HashMap<String, Value> mappedGirlsHashmap;
    private ViewEditMappedGirlsAdapter girlsAdapter;

    public static ViewEditMappedGirlsFragment newInstance() {
        return new ViewEditMappedGirlsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_edit_mapped_girls_fragment, container, false);

        girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), (List<Value>) null);
        girlsAdapter.setClickListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_edit_mapped_girls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(girlsAdapter);

//        apiInterface = APIClient.getClient().create(APIInterface.class);
//        getMappedGirlsList();
        return rootView;
    }

    private void getMappedGirlsList() {
        Timber.d("get mapped girls list started");
        Call<MappedGirls> call = apiInterface.getMappedGirls("build_GetInTest4_1568212345");
        Log.d("Server", "getMappedGirlsList: made server request");

        call.enqueue(new Callback<MappedGirls>() {
            @Override
            public void onResponse(Call<MappedGirls> call, Response<MappedGirls> response) {
                Timber.d("onResponse() -> " + response.code());
                List<Value> values = response.body().getValue();
                girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), values);
                girlsAdapter.setClickListener(ViewEditMappedGirlsFragment.this);
                recyclerView.setAdapter(girlsAdapter);
            }

            @Override
            public void onFailure(Call<MappedGirls> call, Throwable t) {
                Timber.e("onFailure() -> " + t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(View view, int position, Value value) {
        Timber.d("Clicked list item");
        displayDialog();
    }

    private void displayDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Girl Data");
        alertDialog.setMessage("Please choose action");
//                alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton("UPDATE",
                (dialog, which) -> {
                    updateGirlForm();
                    ToastUtils.showShortToast("Update clicked");
                });
        alertDialog.setNegativeButton("VIEW",
                (dialog, which) -> {
                    ToastUtils.showShortToast("View clicked");
                    Intent intent = new Intent(getActivity(), PregnancySummaryActivity.class);
                    startActivity(intent);
                    dialog.cancel();
                });
        alertDialog.show();
    }

    private void updateGirlForm() {
        String selectionClause = InstanceColumns.DISPLAY_NAME + " LIKE ?";
        String[] selectionArgs = {"GetInTest18%"};

        Cursor c = getActivity().getContentResolver().query(
                InstanceProviderAPI.InstanceColumns.CONTENT_URI,  // The content URI of the words table
                null,                       // The columns to return for each row
                selectionClause,                  // Either null, or the word the user entered
                selectionArgs,                    // Either empty, or the string the user entered
                null);

        c.moveToFirst();

        Uri instanceUri =
                ContentUris.withAppendedId(InstanceColumns.CONTENT_URI,
                        c.getLong(c.getColumnIndex(InstanceColumns._ID)));

        Timber.d("Cursor value " + c.toString());
        Timber.d("Uri value " + instanceUri.toString());

        Intent intent = new Intent(Intent.ACTION_EDIT, instanceUri);
        intent.putExtra(ApplicationConstants.BundleKeys.FORM_MODE, ApplicationConstants.FormModes.EDIT_SAVED);
        startActivity(intent);
        getActivity().finish();
    }
}
