package org.odk.collect.android.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.ui.vieweditmappedgirls.ViewEditMappedGirlsFragment;
import org.odk.collect.android.adapters.AmbulanceAdapter;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.collect.android.provider.userstable.UserstableCursor;
import org.odk.collect.android.provider.userstable.UserstableSelection;


public class AmbulanceActivityFragment extends Fragment {

    private View rootView;
    private AmbulanceAdapter ambulanceAdapter;
    private RecyclerView recyclerView;

    public AmbulanceActivityFragment() {
    }

    public static AmbulanceActivityFragment newInstance() {
        return new AmbulanceActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ambulance, container, false);

        AmbulanceActivity activity = ((AmbulanceActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.ambulance));

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        ambulanceAdapter = new AmbulanceAdapter(getActivity(), queryUserTable());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_ambulance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(ambulanceAdapter);
        return rootView;
    }

    private UserstableCursor queryUserTable() {
        return new UserstableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }
}
