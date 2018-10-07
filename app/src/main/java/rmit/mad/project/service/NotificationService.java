package rmit.mad.project.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import rmit.mad.project.R;
import rmit.mad.project.model.Tracking;
import rmit.mad.project.model.TrackingDAO;
import rmit.mad.project.receiver.SnoozeNotificationDetector;
import rmit.mad.project.view.TrackingEditActivity;

public class NotificationService extends IntentService {

    static final String NOTIFICATION_ID_KEY = "notificationID";
    static final String NOTIFICATION_CHANNEL = "notificationID";

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("NotificationService", "Running");

        checkForPendingNotifications();

        stopSelf();
    }

    private void checkForPendingNotifications() {
        List<Tracking> trackings = TrackingDAO.getInstance().getAll();
        for(Tracking tracking: trackings) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int period = prefs.getInt("settings_snooze", 1);
            int minutes2Meeting = prefs.getInt("settings_notification", 1);

            Calendar futureTime = Calendar.getInstance();
            futureTime.set(Calendar.MINUTE, futureTime.get(Calendar.MINUTE) + minutes2Meeting);
            futureTime.set(Calendar.SECOND, 0);
            futureTime.set(Calendar.MILLISECOND, 0);

            Calendar meetingTime = Calendar.getInstance();
            meetingTime.setTime(tracking.getMeetTime());
            meetingTime.set(Calendar.SECOND, 0);
            meetingTime.set(Calendar.MILLISECOND, 0);


            if(futureTime.getTime().equals(meetingTime)) {
                createNotification(tracking, minutes2Meeting, period);
            }
        }
    }


    private void createNotification(Tracking tracking, int minutes2Meeting, int snoozePeriod) {
        Intent cancelIntent = new Intent(this, TrackingEditActivity.class);
        cancelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, cancelIntent, 0);

        int randomId = (int) Math.abs(Math.random() * 1000);

        Intent snoozeIntent = new Intent(this, SnoozeNotificationDetector.class);
        snoozeIntent.putExtra(NOTIFICATION_ID_KEY, randomId);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.calendar)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_content, tracking.getTitle(), minutes2Meeting))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_info_black_24dp, getString(R.string.notification_snooze, snoozePeriod), snoozePendingIntent)
                .addAction(R.drawable.ic_info_black_24dp, getString(R.string.notification_cancel), snoozePendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(randomId, mBuilder.build());
    }

    public void reCreateNotification(int notificaitonId, int time) {

    }
}
