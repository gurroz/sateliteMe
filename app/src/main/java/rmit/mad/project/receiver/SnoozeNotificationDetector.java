package rmit.mad.project.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import rmit.mad.project.service.NotificationService;

import static rmit.mad.project.enums.IntentModelEnum.TRACKING_ID;
import static rmit.mad.project.service.NotificationService.NOTIFICATION_ID_KEY;

public class SnoozeNotificationDetector extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SnoozeNotificationctor", "REceiving");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        long period = Long.valueOf(prefs.getString("settings_snooze", "1")) * 60 * 1000;

        final String trackingId =  intent.getStringExtra(TRACKING_ID.name());
        Log.d("SnoozeNotificationctor", "Repeating notification: " + period + " to tracking: " + trackingId);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent notificationIntent = new Intent(context, NotificationService.class);
                notificationIntent.putExtra(TRACKING_ID.name(), trackingId);
                context.startService(notificationIntent);
            }
        }, period);

        int notificationId = intent.getIntExtra(NOTIFICATION_ID_KEY, 0);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }

}
