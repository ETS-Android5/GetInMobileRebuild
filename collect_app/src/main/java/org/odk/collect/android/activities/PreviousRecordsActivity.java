package org.odk.collect.android.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import org.odk.collect.android.R;
import org.odk.collect.android.adapters.PreviousRecordsAdapter;
import org.odk.collect.android.retrofit.APIClient;
import org.odk.collect.android.retrofit.APIInterface;
import org.odk.collect.android.retrofitmodels.Value;

import java.util.List;

public class PreviousRecordsActivity extends CollectAbstractActivity implements PreviousRecordsAdapter.ItemClickListener{

    private PreviousRecordsAdapter previousRecordsAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_records);

        previousRecordsAdapter = new PreviousRecordsAdapter(this, (List<Value>) null);
        previousRecordsAdapter.setClickListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_upcoming_appointments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(previousRecordsAdapter);

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public void onItemClick(View view, int position, Value value) {

    }
}
