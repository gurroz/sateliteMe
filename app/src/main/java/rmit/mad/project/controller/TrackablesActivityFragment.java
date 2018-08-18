package rmit.mad.project.controller;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rmit.mad.project.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrackablesActivityFragment extends Fragment {

    public TrackablesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trackables, container, false);
    }
}
