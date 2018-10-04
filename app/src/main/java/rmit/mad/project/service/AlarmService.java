package rmit.mad.project.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.content.Context.ALARM_SERVICE;

public class AlarmService {
    private static final int SUGGESTION_REQ_CODE = 15;
    private static final int NOTIFICATION_REQ_CODE = 21;
    private static final String TAG = AlarmService.class.getName();

    public static void setAlarmSuggestions(Context context) {
        Intent suggestionIntent = new Intent(context, SuggestionService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, SUGGESTION_REQ_CODE, suggestionIntent,  FLAG_CANCEL_CURRENT);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        long period = Long.valueOf(prefs.getString("settings_suggestion", "0")) * 1000;
        long triggeringTime = Calendar.getInstance().getTimeInMillis() + period;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Log.d(TAG, "Starting Alarm at: " + new Date(triggeringTime));
        Log.d(TAG, "Repetition in: " + period);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggeringTime, period, pendingIntent);
    }

    // TODO: Call to show notification
    public static void setMeetingNotification(Context context) {
//        Intent suggestionIntent = new Intent(context, SuggestionService.class);
//        PendingIntent pendingIntent = PendingIntent.getService(context, NOTIFICATION_REQ_CODE, suggestionIntent,  FLAG_CANCEL_CURRENT);
//
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//
//        long period = Long.valueOf(prefs.getString("settings_notification", "0")) * 1000;
//        long triggeringTime = Calendar.getInstance().getTimeInMillis() + period;
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggeringTime, period, pendingIntent);
    }
}

