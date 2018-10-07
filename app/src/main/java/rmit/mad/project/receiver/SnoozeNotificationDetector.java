package rmit.mad.project.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import rmit.mad.project.service.NotificationService;
import rmit.mad.project.service.SuggestionService;

import static rmit.mad.project.enums.IntentModelEnum.TRACKING_ID;
import static rmit.mad.project.service.NotificationService.NOTIFICATION_ID_KEY;

public class SnoozeNotificationDetector extends BroadcastReceiver {

    public static final String ACTION_SNOOZE_NOT = "rmit.mad.project.receiver.SnoozeNotificationDetector.ACTION_SNOOZE_NOT";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SnoozeNotificationctor", "REceiving");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long period = Long.valueOf(prefs.getString("settings_snooze", "1")) * 60 * 1000;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d("SnoozeNotificationctor", "Sending Sonoozed notification: " +  intent.getStringExtra(TRACKING_ID.name()));

                Intent notificationIntent = new Intent(context, NotificationService.class);
                notificationIntent.putExtra(TRACKING_ID.name(), intent.getStringExtra(TRACKING_ID.name()));
                context.startService(notificationIntent);
            }
        }, period);

    }

}
