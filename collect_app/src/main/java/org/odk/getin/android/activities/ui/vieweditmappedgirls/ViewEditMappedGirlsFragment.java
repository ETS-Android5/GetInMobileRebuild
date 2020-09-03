package org.odk.getin.android.activities.ui.vieweditmappedgirls;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        ViewEditMappedGirlsActivity activity = ((ViewEditMappedGirlsActivity) getActivity());
        setHasOptionsMenu(true);

        searchView = activity.findViewById(R.id.search);
        index = getArguments().getInt(ARG_SECTION_NUMBER);
        if (index == 1) {
            girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), queryMappedAllGirlTable());
        } else if (index == 2) {
            girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), queryMappedVoucherGirlTable());
        }
        girlsAdapter.setClickListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_edit_mapped_girls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(girlsAdapter);
        return rootView;
    }

    private MappedgirltableCursor queryMappedAllGirlTable() {
        return new MappedgirltableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    private MappedgirltableCursor queryMappedVoucherGirlTable() {
        return new MappedgirltableSelection().voucherNumberContains("-").orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    private MappedgirltableCursor queryMappedAllGirlTable(String name) {
        MappedgirltableSelection mappedgirltableSelection = new MappedgirltableSelection();
        mappedgirltableSelection.firstnameContains(name).or().lastnameContains(name);
        mappedgirltableSelection.and().firstnameContains(name).or().lastnameContains(name);
        return mappedgirltableSelection.query(getContext().getContentResolver());
    }

    private MappedgirltableCursor queryMappedVoucherGirlTable(String name) {
        MappedgirltableSelection mappedgirltableSelection = new MappedgirltableSelection();
        mappedgirltableSelection.voucherNumberContains("-");
        mappedgirltableSelection.and().firstnameContains(name).or().lastnameContains(name);
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
                if (!TextUtils.isEmpty(query)) {
                    index = getArguments().getInt(ARG_SECTION_NUMBER);
                    MappedgirltableCursor mappedgirltableCursor = null;
                    if (index == 1) {
                        mappedgirltableCursor = queryMappedAllGirlTable(query);
                    } else if (index == 2) {
                        mappedgirltableCursor = queryMappedVoucherGirlTable(query);
                    }
                    girlsAdapter.filter(mappedgirltableCursor);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            if (index == 1) {
                girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), queryMappedAllGirlTable());
            } else if (index == 2) {
                girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), queryMappedVoucherGirlTable());
            }
            recyclerView.setAdapter(girlsAdapter);
            searchView.onActionViewCollapsed();
            return false;
        });
    }
}
