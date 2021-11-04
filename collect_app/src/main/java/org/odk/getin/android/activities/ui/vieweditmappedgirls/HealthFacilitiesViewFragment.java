package org.odk.getin.android.activities.ui.vieweditmappedgirls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.HealthFacilityActivity;
import org.odk.getin.android.adapters.AmbulanceAdapter;
import org.odk.getin.android.adapters.HealthFacilityAdapter;
import org.odk.getin.android.provider.healthfacilitytable.HealthfacilitytableSelection;


public class HealthFacilitiesViewFragment extends Fragment {

    public static HealthFacilitiesViewFragment newInstance() {
        return new HealthFacilitiesViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_hf_fragment, container, false);

        HealthFacilityActivity activity = ((HealthFacilityActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.health_facilities));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = rootView.findViewById(R.id.health_facility_recycler);

        HealthFacilityAdapter healthFacilityAdapter = new HealthFacilityAdapter(getActivity(),
                new HealthfacilitytableSelection().query(getContext().getContentResolver()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(healthFacilityAdapter);
        return rootView;
    }
}
