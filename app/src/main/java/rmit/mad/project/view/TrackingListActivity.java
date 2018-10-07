package rmit.mad.project.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import rmit.mad.project.R;
import rmit.mad.project.adapter.TrackingAdapter;
import rmit.mad.project.service.TrackableTrackingsService;
import rmit.mad.project.model.Tracking;

public class TrackingListActivity extends Fragment implements Observer {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TrackableTrackingsService trackableTrackingsService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackableTrackingsService = TrackableTrackingsService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trackings_list, container, false);

        mRecyclerView = view.findViewById(R.id.trackingListView);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TrackingAdapter();
        mRecyclerView.setAdapter(mAdapter);

        trackableTrackingsService.addObserver(this);
        trackableTrackingsService.getAllSortedByDate(); // Notify to update adapter for the first time

        return view;
    }


    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof TrackableTrackingsService) {
            List<Tracking> trackings = (List<Tracking>) arg;
            ((TrackingAdapter)mAdapter).updateData(trackings);
        }
    }

    @Override
    public void onStop() {
        trackableTrackingsService.saveState();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        trackableTrackingsService.deleteObserver(this);
        super.onDestroy();
    }
}
