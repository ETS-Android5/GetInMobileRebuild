package org.odk.getin.android.activities.ui.vieweditmappedgirls;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.HealthFacilityActivity;
import org.odk.getin.android.adapters.HealthFacilityAdapter;
import org.odk.getin.android.provider.healthfacilitytable.HealthfacilitytableSelection;

import timber.log.Timber;


public class HealthFacilitiesViewFragment extends Fragment implements Searchable {

    private SearchView searchView;
    private TextView toolbarTitle;
    private HealthFacilityAdapter healthFacilityAdapter;

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

        searchView = rootView.findViewById(R.id.search);
        toolbarTitle = toolbar.findViewById(R.id.title);

        RecyclerView recyclerView = rootView.findViewById(R.id.health_facility_recycler);

        healthFacilityAdapter = new HealthFacilityAdapter(getActivity(),
                new HealthfacilitytableSelection().query(getContext().getContentResolver()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(healthFacilityAdapter);
        this.initializeSearch();
        return rootView;
    }

    @Override
    public void initializeSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterForFacilityName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterForFacilityName(s);
                return false;
            }
        });

        searchView.setOnSearchClickListener(view -> {
            hideOrUnhideToolbarTitle();
        });

        searchView.setOnCloseListener(() -> {
            resetToDefaultState();
            return false;
        });
    }

    private void filterForFacilityName(String query) {
        if (!TextUtils.isEmpty(query)) {
            healthFacilityAdapter.filter(new HealthfacilitytableSelection().nameContains(query).query(getContext().getContentResolver()));
        }
    }

    private void resetToDefaultState() {
        healthFacilityAdapter.filter(new HealthfacilitytableSelection().query(getContext().getContentResolver()));
        searchView.onActionViewCollapsed();
        toolbarTitle.setVisibility(View.VISIBLE);
    }

    private void hideOrUnhideToolbarTitle() {
        toolbarTitle.setVisibility(searchView.isIconified() ? View.VISIBLE : View.GONE);
    }
}
