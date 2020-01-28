package org.odk.getin.android.activities.ui.vieweditmappedgirls;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.PregnancySummaryActivity;
import org.odk.getin.android.activities.ViewEditMappedGirlsActivity;
import org.odk.getin.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.getin.android.provider.InstanceProviderAPI;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableCursor;
import org.odk.getin.android.provider.mappedgirltable.MappedgirltableSelection;
import org.odk.getin.android.retrofitmodels.Value;
import org.odk.getin.android.utilities.ApplicationConstants;
import org.odk.getin.android.utilities.ToastUtils;
import org.odk.getin.android.provider.InstanceProviderAPI.InstanceColumns;

import timber.log.Timber;

/**
 * Mapped Girl's view displays a list of mapped girls by the VHT and midwife
 * The VHT view only has follow up and postnatal
 * The midwife view has appointment, follow up and postnatal
 * The midwife is able to see all mapped girls from the vhts that she is attached to
 * while the VHT can only see her mapped girls
 *
 * @author Phillip Kigenyi (codephillip@gmail.com)
 * */
public class ViewEditMappedGirlsFragment extends Fragment implements ViewEditMappedGirlsAdapter.ItemClickListener {

    View rootView;
    private RecyclerView recyclerView;
    private ViewEditMappedGirlsAdapter girlsAdapter;
    private SearchView searchView;

    public static ViewEditMappedGirlsFragment newInstance() {
        return new ViewEditMappedGirlsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_edit_mapped_girls_fragment, container, false);

        ViewEditMappedGirlsActivity activity = ((ViewEditMappedGirlsActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.mapped_girls));
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), queryMappedGirlTable());
        girlsAdapter.setClickListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_edit_mapped_girls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(girlsAdapter);
        return rootView;
    }

    private MappedgirltableCursor queryMappedGirlTable() {
        return new MappedgirltableSelection().orderByCreatedAt(true).query(getContext().getContentResolver());
    }

    @Override
    public void onItemClick(View view, int position, Value value) {
        Timber.d("Clicked list item");
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
                    girlsAdapter.filter(query);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            girlsAdapter = new ViewEditMappedGirlsAdapter(getActivity(), queryMappedGirlTable());
            recyclerView.setAdapter(girlsAdapter);
            return false;
        });
    }
}
