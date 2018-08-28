package rmit.mad.project.controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import rmit.mad.project.R;


public class TrackablesFilterFragment extends DialogFragment {

    public interface TrackablesFilterListener {
        void onDialogPositiveClick(DialogFragment dialog, List<String> filteringSelectedItems);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    TrackablesFilterListener clickListener;
    List<String> filteringSelectedItems;  // Where we track the selected items
    String[] filteringItems;

    public TrackablesFilterFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            clickListener = (TrackablesFilterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement TrackablesFilterListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        filteringSelectedItems = new ArrayList();
        filteringItems = savedInstanceState.getStringArray("categories");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_filter_trackables)
                .setMultiChoiceItems(filteringItems, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    filteringSelectedItems.add(filteringItems[which]);
                                } else if (filteringSelectedItems.contains(filteringItems[which])) {
                                    filteringSelectedItems.remove(filteringItems[which]);
                                }
                            }
                        })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        clickListener.onDialogPositiveClick(TrackablesFilterFragment.this, filteringSelectedItems);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        clickListener.onDialogNegativeClick(TrackablesFilterFragment.this);
                    }
                });

        return builder.create();
    }

}
