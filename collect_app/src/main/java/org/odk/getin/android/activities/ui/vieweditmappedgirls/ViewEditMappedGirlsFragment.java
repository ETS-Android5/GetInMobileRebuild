package org.odk.getin.android.activities.ui.vieweditmappedgirls;

import static org.odk.getin.android.utilities.ApplicationConstants.HEALTH_FACILITY;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.ViewEditMappedGirlsActivity;
import org.odk.getin.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.getin.android.retrofitmodels.Value;

import timber.log.Timber;

/**
 * Mapped Girl's view displays a list of mapped girls by the VHT and midwife
 * The VHT view only has follow up and postnatal
 * The midwife view has appointment, follow up and postnatal
 * The midwife is able to see all mapped girls from the vhts that she is attached to
 * while the VHT can only see her mapped girls
 *
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */
public class ViewEditMappedGirlsFragment extends Fragment implements ViewEditMappedGirlsAdapter.ItemClickListener, Searchable {

    View rootView;
    private RecyclerView recyclerView;
    private ViewEditMappedGirlsAdapter girlsAdapter;
    private SearchView searchView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;
    private int index = 0;
    private ViewEditMappedGirlsActivity activity;
    private TextView toolbarTitle;


    public static ViewEditMappedGirlsFragment newInstance(int index) {
        ViewEditMappedGirlsFragment fragment = new ViewEditMappedGirlsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_edit_mapped_girls_fragment, container, false);

        activity = ((ViewEditMappedGirlsActivity) getActivity());
        setHasOptionsMenu(true);

        searchView = activity.findViewById(R.id.search);

        MappedgirltableCursor cursor = getArguments().getInt(ARG_SECTION_NUMBER) == 1 ?
                queryMappedAllGirlTable() : queryMappedVoucherGirlTable();
        girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), cursor);
        girlsAdapter.setClickListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_edit_mapped_girls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(girlsAdapter);

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbarTitle = toolbar.findViewById(R.id.title);
        return rootView;
    }

    private MappedgirltableCursor queryMappedAllGirlTable() {
        MappedgirltableSelection mappedgirltableSelection = new MappedgirltableSelection().orderByCreatedAt(true);
        if (!TextUtils.isEmpty(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY)))
            mappedgirltableSelection.healthfacility(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY));
        return mappedgirltableSelection.query(getContext().getContentResolver());
    }

    private MappedgirltableCursor queryMappedVoucherGirlTable() {
        MappedgirltableSelection mappedgirltableSelection = new MappedgirltableSelection().voucherNumberContains("-");
        if (!TextUtils.isEmpty(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY)))
            mappedgirltableSelection.and().healthfacility(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY));
        return mappedgirltableSelection.orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    private MappedgirltableCursor queryMappedAllGirlTable(String name) {
        MappedgirltableSelection mappedgirltableSelection = new MappedgirltableSelection();
        mappedgirltableSelection.firstnameContains(name).or().lastnameContains(name);
        mappedgirltableSelection.and().firstnameContains(name).or().lastnameContains(name);
        if (!TextUtils.isEmpty(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY)))
            mappedgirltableSelection.and().healthfacility(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY));
        return mappedgirltableSelection.query(getContext().getContentResolver());
    }

    private MappedgirltableCursor queryMappedVoucherGirlTable(String name) {
        MappedgirltableSelection mappedgirltableSelection = new MappedgirltableSelection();
        mappedgirltableSelection.voucherNumberContains("-");
        mappedgirltableSelection.and().firstnameContains(name).or().lastnameContains(name);
        if (!TextUtils.isEmpty(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY)))
            mappedgirltableSelection.and().healthfacility(this.getActivity().getIntent().getStringExtra(HEALTH_FACILITY));
        return mappedgirltableSelection.query(getContext().getContentResolver());
    }

    @Override
    public void onItemClick(View view, int position, Value value) {
        Timber.d("Clicked list item");
    }

    @Override
    public void initializeSearch() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterForGirlName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterForGirlName(s);
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

    private void filterForGirlName(String query) {
        if (!TextUtils.isEmpty(query)) {
            MappedgirltableCursor cursor = getArguments().getInt(ARG_SECTION_NUMBER) == 1 ?
                    queryMappedAllGirlTable(query) : queryMappedVoucherGirlTable(query);
            girlsAdapter.filter(cursor);
        }
    }

    private void resetToDefaultState() {
        MappedgirltableCursor cursor = getArguments().getInt(ARG_SECTION_NUMBER) == 1 ?
                queryMappedAllGirlTable() : queryMappedVoucherGirlTable();
        girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), cursor);
        recyclerView.setAdapter(girlsAdapter);
        searchView.onActionViewCollapsed();
        toolbarTitle.setVisibility(View.VISIBLE);
    }

    private void hideOrUnhideToolbarTitle() {
        toolbarTitle.setVisibility(searchView.isIconified() ? View.VISIBLE : View.GONE);
    }
}
