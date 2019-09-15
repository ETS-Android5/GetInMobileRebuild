package org.odk.collect.android.activities.ui.vieweditmappedgirls;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.Toast;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.PregnancySummaryActivity;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.MappedGirls;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.utilities.ToastUtils;

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

        apiInterface = APIClient.getClient().create(APIInterface.class);
        getMappedGirlsList();
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
    public void onItemClick(View view, int position) {
        Log.d("Clicked", "onItemClick: ############ clicked");
        displayDialog();
    }

    private void displayDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Girl Data");
        alertDialog.setMessage("Please choose action");
//                alertDialog.setIcon(R.drawable.delete);
        alertDialog.setPositiveButton("UPDATE",
                (dialog, which) -> {
                    ToastUtils.showShortToast("Update clicked");
                });
        alertDialog.setNegativeButton("VIEW",
                (dialog, which) -> {
                    ToastUtils.showShortToast("View clicked");
                    startActivity(new Intent(getActivity(), PregnancySummaryActivity.class));
                    dialog.cancel();
                });
        alertDialog.show();
    }
}
