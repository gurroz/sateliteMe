package rmit.mad.project.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimePicker implements View.OnClickListener {

    protected IDateTimePickerListener pickListener;
    protected Calendar currentDate;

    public DateTimePicker(IDateTimePickerListener targetPickListener){
        currentDate = Calendar.getInstance();
        pickListener = targetPickListener;
    }

    @Override
    public void onClick(View v) {
        showPicker(v);
    }

    private void showPicker(View view) {
        new DatePickerDialog(view.getContext(), new DatePickerDialogListener()
                , currentDate.get(Calendar.YEAR)
                , currentDate.get(Calendar.MONTH)
                , currentDate.get(Calendar.DATE))
                .show();
    }

    private class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener {
        private Calendar returnDate = Calendar.getInstance();;

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            returnDate.set(year, monthOfYear, dayOfMonth);
            new TimePickerDialog(view.getContext(), new TimePickerDialogListener(returnDate)
                    , currentDate.get(Calendar.HOUR_OF_DAY)
                    , currentDate.get(Calendar.MINUTE)
                    , true)
                    .show();
        }
    }

    private class TimePickerDialogListener implements TimePickerDialog.OnTimeSetListener {
        private Calendar returnDate;
        public TimePickerDialogListener(Calendar inputDate) {
            returnDate = inputDate;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            returnDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
            returnDate.set(Calendar.MINUTE, minute);

            pickListener.onDateTimePicked(returnDate.getTime());
        }
    }
}
