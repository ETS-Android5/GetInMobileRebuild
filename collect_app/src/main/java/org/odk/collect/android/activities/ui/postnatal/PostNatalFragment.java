package org.odk.collect.android.activities.ui.postnatal;

import androidx.lifecycle.ViewModelProviders;

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

import org.odk.collect.android.R;
import org.odk.collect.android.activities.FormChooserList;
import org.odk.collect.android.activities.PostNatalActivity;
import org.odk.collect.android.activities.PregnancySummaryActivity;
import org.odk.collect.android.activities.ui.upcomingappointments.UpcomingAppointmentsViewModel;
import org.odk.collect.android.activities.ui.vieweditmappedgirls.ViewEditMappedGirlsFragment;
import org.odk.collect.android.adapters.PostNatalAdapter;
import org.odk.collect.android.adapters.UpcomingAppointmentsAdapter;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.MappedGirls;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.utilities.ToastUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class PostNatalFragment extends Fragment implements PostNatalAdapter.ItemClickListener{

    private PostNatalViewModel mViewModel;
    private PostNatalAdapter postNatalAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private APIInterface apiInterface;


    public static PostNatalFragment newInstance() {
        return new PostNatalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.post_natal_fragment, container, false);

        postNatalAdapter = new PostNatalAdapter(getActivity(), (List<Value>) null);
        postNatalAdapter.setClickListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_edit_mapped_girls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(postNatalAdapter);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        getMappedGirlsList();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PostNatalViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemClick(View view, int position, Value value) {
        if (Collect.allowClick(getClass().getName())) {
            Intent i = new Intent(getActivity(), FormChooserList.class);
            startActivity(i);
        }
    }

    private void getMappedGirlsList() {
        Timber.d("get mapped girls list started");
        Call<MappedGirls> call = apiInterface.getMappedGirls();
        Log.d("Server", "getMappedGirlsList: made server request");

        call.enqueue(new Callback<MappedGirls>() {
            @Override
            public void onResponse(Call<MappedGirls> call, Response<MappedGirls> response) {
                Timber.d("onResponse() -> " + response.code());
                List<Value> values = response.body().getValue();
                postNatalAdapter = new PostNatalAdapter(getActivity(), values);
                postNatalAdapter.setClickListener(PostNatalFragment.this);
                recyclerView.setAdapter(postNatalAdapter);
            }

            @Override
            public void onFailure(Call<MappedGirls> call, Throwable t) {
                Timber.e("onFailure() -> " + t.getMessage());
            }
        });
    }
}
