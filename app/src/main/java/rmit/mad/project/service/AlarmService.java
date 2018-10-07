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
    private static final int NOTIFICATION_REQ_CODE = 21;
    private static final String TAG = AlarmService.class.getName();

    public static void setAlarmSuggestions(Context context) {

        Intent locationIntent = new Intent(context, LocationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, NOTIFICATION_REQ_CODE, locationIntent,  FLAG_CANCEL_CURRENT);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        long period = Long.valueOf(prefs.getString("settings_suggestion", "0")) * 1000;
        long triggeringTime = Calendar.getInstance().getTimeInMillis() + period;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        Log.d(TAG, "Starting Alarm at: " + new Date(triggeringTime));
        Log.d(TAG, "Repetition in: " + period);

        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggeringTime, period, pendingIntent);
    }

    public static void setMeetingNotification(Context context) {
        Intent suggestionIntent = new Intent(context, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, NOTIFICATION_REQ_CODE, suggestionIntent,  FLAG_CANCEL_CURRENT);

        long period = 40 * 1000; // HarCoded, checks every 60 seconds
        long triggeringTime = Calendar.getInstance().getTimeInMillis();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggeringTime, period, pendingIntent);
    }
}

