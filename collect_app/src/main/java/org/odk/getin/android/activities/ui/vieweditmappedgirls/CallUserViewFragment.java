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

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.CallUserViewActivity;
import org.odk.getin.android.adapters.CallUserAdapter;
import org.odk.getin.android.provider.userstable.UserstableCursor;
import org.odk.getin.android.provider.userstable.UserstableSelection;

import timber.log.Timber;

import static org.odk.getin.android.utilities.ApplicationConstants.CHEW_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ID;
import static org.odk.getin.android.utilities.ApplicationConstants.USER_ROLE;
import static org.odk.getin.android.utilities.ApplicationConstants.VHT_MIDWIFE_ID;


public class CallUserViewFragment extends Fragment {

    View rootView;
    private RecyclerView recyclerView;
    private CallUserAdapter callUserAdapter;
    private SearchView searchView;

    public static CallUserViewFragment newInstance() {
        return new CallUserViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_call_user_fragment, container, false);

        CallUserViewActivity activity = ((CallUserViewActivity) getActivity());
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);

        if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
            toolbar.setTitle(getString(R.string.call_midwife));
        else
            toolbar.setTitle(getString(R.string.call_vhts));

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        callUserAdapter = new CallUserAdapter(getActivity(), queryUserTable());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_call_user);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(callUserAdapter);
        return rootView;
    }

    private UserstableCursor queryUserTable() {
        Timber.d("Midwife phone " + Prefs.getString(VHT_MIDWIFE_ID, ""));

        if (Prefs.getString(USER_ROLE, CHEW_ROLE).equals(CHEW_ROLE))
            return new UserstableSelection().useridContains(Prefs.getString(VHT_MIDWIFE_ID, "")).orderByCreatedAt(true).query(getContext().getContentResolver());
        else
            return new UserstableSelection().midwifeidContains(Prefs.getString(USER_ID, "")).orderByCreatedAt(true).query(getContext().getContentResolver());
    }
}
