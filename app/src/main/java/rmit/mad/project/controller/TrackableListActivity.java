package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.Trackable;
import rmit.mad.project.model.TrackableAdapter;
import rmit.mad.project.model.TrackableService;

public class TrackableListActivity extends Fragment implements TrackablesFilterFragment.TrackablesFilterListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_trackables, container, false);

        mRecyclerView = view.findViewById(R.id.trackableListView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new TrackableAdapter(TrackableService.getInstance().getTrackables());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public void showFilterOptions() {
        Bundle bundle = new Bundle();
        bundle.putStringArray("categories", TrackableService.getInstance().getTrackablesCategories());

        DialogFragment filterFragment = (DialogFragment) getFragmentManager().findFragmentByTag("filterTrackble");
        if(filterFragment == null) {
            filterFragment = new TrackablesFilterFragment();
        }

        filterFragment.setArguments(bundle);
        filterFragment.show(getFragmentManager(),"filterTrackble");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, List<String> filteringSelectedItems) {
        if(filteringSelectedItems != null && filteringSelectedItems.size() > 0) {
            List<Trackable> filteredTrackables = TrackableService.getInstance().getTrackablesFilteredByCategory(filteringSelectedItems);
            ((TrackableAdapter)mAdapter).updateData(filteredTrackables);
        }
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
