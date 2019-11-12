package org.odk.collect.android.activities.ui.vieweditmappedgirls;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.collect.android.R;
import org.odk.collect.android.activities.AmbulanceViewActivity;
import org.odk.collect.android.activities.PregnancySummaryActivity;
import org.odk.collect.android.activities.ViewEditMappedGirlsActivity;
import org.odk.collect.android.adapters.AmbulanceAdapter;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.collect.android.provider.InstanceProviderAPI.InstanceColumns;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.collect.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.collect.android.provider.userstable.UserstableCursor;
import org.odk.collect.android.provider.userstable.UserstableSelection;
import org.odk.collect.android.retrofitmodels.Value;
import org.odk.collect.android.utilities.ApplicationConstants;
import org.odk.collect.android.utilities.ToastUtils;

import timber.log.Timber;


public class AmbulanceViewFragment extends Fragment implements ViewEditMappedGirlsAdapter.ItemClickListener {

    View rootView;
    private RecyclerView recyclerView;
    private AmbulanceAdapter girlsAdapter;
    private SearchView searchView;

    public static AmbulanceViewFragment newInstance() {
        return new AmbulanceViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_ambulance_fragment, container, false);

        AmbulanceViewActivity activity = ((AmbulanceViewActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.ambulance));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        girlsAdapter = new AmbulanceAdapter(getActivity(), queryUserTable());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_ambulance);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(girlsAdapter);
        return rootView;
    }

    private UserstableCursor queryUserTable() {
        return new UserstableSelection().roleContains("ambulance").orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    @Override
    public void onItemClick(View view, int position, Value value) {
        Timber.d("Clicked list item");
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        this.getActivity().getMenuInflater().inflate(R.menu.view_edit_mapped_girls_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastUtils.showShortToast("SearchOnQueryTextSubmit: " + query);
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
    }
}
