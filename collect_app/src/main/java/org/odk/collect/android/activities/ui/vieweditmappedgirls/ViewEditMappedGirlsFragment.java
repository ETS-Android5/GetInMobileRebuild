package org.odk.collect.android.activities.ui.vieweditmappedgirls;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.odk.collect.android.R;
import org.odk.collect.android.adapters.ViewEditMappedGirlsAdapter;


public class ViewEditMappedGirlsFragment extends Fragment {

    private ViewEditMappedGirlsViewModel mViewModel;
    View rootView;
    private RecyclerView recyclerView;

    public static ViewEditMappedGirlsFragment newInstance() {
        return new ViewEditMappedGirlsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_edit_mapped_girls_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_edit_mapped_girls);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ViewEditMappedGirlsAdapter(getActivity(), null));
        return rootView;
    }
}
