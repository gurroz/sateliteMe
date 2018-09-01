package rmit.mad.project.view;

import android.support.v4.app.DialogFragment;

import java.util.List;

public interface ICategoryFilterListener {
    void onDialogPositiveClick(DialogFragment dialog, List<String> filteringSelectedItems);
    void onDialogNegativeClick(DialogFragment dialog);
}
