package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rmit.mad.project.R;
import rmit.mad.project.model.TrackingAdapter;
import rmit.mad.project.model.TrackingDAO;

public class TrackingListActivity extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_trackings, container, false);

        mRecyclerView = view.findViewById(R.id.trackingListView);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new TrackingAdapter(TrackingDAO.getInstance().getTracking());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
