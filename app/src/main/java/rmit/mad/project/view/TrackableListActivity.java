package rmit.mad.project.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import rmit.mad.project.R;
import rmit.mad.project.adapter.TrackableAdapter;
import rmit.mad.project.service.TrackableService;
import rmit.mad.project.controller.TrackableFilterDialogController;
import rmit.mad.project.model.Trackable;

public class TrackableListActivity extends Fragment implements Observer, ICategoryFilterListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageButton filterBtn;
    private TrackableService trackableService;
    private TrackableFilterDialogController trackableFilterDialogController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackableService = TrackableService.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trackables_list, container, false);
        filterBtn = view.findViewById(R.id.filterBtn);

        trackableFilterDialogController = new TrackableFilterDialogController(getFragmentManager(), TrackableListActivity.this);
        filterBtn.setOnClickListener(trackableFilterDialogController);

        mAdapter = new TrackableAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());

        mRecyclerView = view.findViewById(R.id.trackableListView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        trackableService.addObserver(this);
        trackableService.getTrackables(); // Notify to update adapter for the first time

        return view;
    }


    @Override
    public void update(Observable observable, Object arg) {
        if (observable instanceof TrackableService) {
            List<Trackable> filteredTrackables = (List<Trackable>) arg;
            ((TrackableAdapter)mAdapter).updateData(filteredTrackables);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        trackableService.deleteObserver(this);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, List<String> filteringSelectedItems) {
        trackableFilterDialogController.onDialogPositiveClick(dialog, filteringSelectedItems);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        trackableFilterDialogController.onDialogNegativeClick(dialog);
    }
}
