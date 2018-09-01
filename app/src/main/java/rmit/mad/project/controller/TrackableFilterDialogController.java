package rmit.mad.project.controller;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.List;

import rmit.mad.project.service.TrackableService;
import rmit.mad.project.view.TrackablesFilterFragment;

import static rmit.mad.project.enums.IntentModelEnum.FILTER_FRAGMENT;
import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_CATEGORIES;

public class TrackableFilterDialogController implements View.OnClickListener {

    private FragmentManager fargmenManager;
    private Fragment fragment;

    public TrackableFilterDialogController(FragmentManager fargmenManager, Fragment fragment) {
        this.fargmenManager = fargmenManager;
        this.fragment = fragment;
    }

    public void onDialogPositiveClick(DialogFragment dialog, List<String> filteringSelectedItems) {
        if(filteringSelectedItems != null && filteringSelectedItems.size() > 0) {
            TrackableService.getInstance().getTrackablesFilteredByCategory(filteringSelectedItems);
        } else {
           TrackableService.getInstance().getTrackables();
        }
        dialog.dismiss();
    }

    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        showFilterOptions();
    }

    public void showFilterOptions() {
        Bundle bundle = new Bundle();
        bundle.putStringArray(TRACKABLE_CATEGORIES.name(), TrackableService.getInstance().getTrackablesCategories());

        DialogFragment filterFragment = (DialogFragment) fargmenManager.findFragmentByTag(FILTER_FRAGMENT.name());
        if(filterFragment == null) {
            filterFragment = new TrackablesFilterFragment();
            filterFragment.setTargetFragment(fragment, 300);
        }

        filterFragment.setArguments(bundle);
        filterFragment.show(fargmenManager, FILTER_FRAGMENT.name());
    }
}
