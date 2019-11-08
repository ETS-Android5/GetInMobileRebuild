package org.odk.collect.android.activities.ui.upcomingappointments;

import androidx.appcompat.widget.Toolbar;
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
import org.odk.collect.android.activities.PregnancySummaryActivity;
import org.odk.collect.android.activities.UpcomingAppointmentsActivity;
import org.odk.collect.android.activities.ViewEditMappedGirlsActivity;
import org.odk.collect.android.activities.ui.vieweditmappedgirls.ViewEditMappedGirlsFragment;
import org.odk.collect.android.adapters.UpcomingAppointmentsAdapter;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.collect.android.provider.appointmentstable.AppointmentstableCursor;
import org.odk.collect.android.provider.appointmentstable.AppointmentstableSelection;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableSelection;
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


public class UpcomingAppointmentsFragment extends Fragment implements UpcomingAppointmentsAdapter.ItemClickListener {

    private UpcomingAppointmentsViewModel mViewModel;
    private UpcomingAppointmentsAdapter upcomingAppointmentsAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private APIInterface apiInterface;

    public static UpcomingAppointmentsFragment newInstance() {
        return new UpcomingAppointmentsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.upcoming_appointments_fragment, container, false);

        UpcomingAppointmentsActivity activity = ((UpcomingAppointmentsActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.appointments));

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        try {
            upcomingAppointmentsAdapter = new UpcomingAppointmentsAdapter(getActivity(), queryAppointmentTable());
            upcomingAppointmentsAdapter.setClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_upcoming_appointments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(upcomingAppointmentsAdapter);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        return rootView;
    }

    private AppointmentstableCursor queryAppointmentTable() {
        return new AppointmentstableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UpcomingAppointmentsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onItemClick(View view, int position, Value value) {
        ToastUtils.showShortToast("clicked item");
//        Intent intent = new Intent(getActivity(), PregnancySummaryActivity.class);
//        startActivity(intent);
    }
}
