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
import org.odk.getin.android.activities.AmbulanceViewActivity;
import org.odk.getin.android.adapters.AmbulanceAdapter;
import org.odk.getin.android.adapters.ViewEditMappedGirlsAdapter;
import org.odk.getin.android.provider.userstable.UserstableCursor;
import org.odk.getin.android.provider.userstable.UserstableSelection;
import org.odk.getin.android.retrofitmodels.Value;

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


}
