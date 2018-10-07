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
import rmit.mad.project.receiver.CancelNotificationDetector;
import rmit.mad.project.receiver.SnoozeNotificationDetector;
import rmit.mad.project.view.TrackingEditActivity;

import static rmit.mad.project.enums.IntentModelEnum.TRACKABLE_ID;
import static rmit.mad.project.enums.IntentModelEnum.TRACKING_ID;

public class NotificationService extends IntentService {

    public static final String NOTIFICATION_ID_KEY = "notificationID";
    static final String NOTIFICATION_CHANNEL = "notificationCH";

    private TrackableTrackingsService trackableTrackingsServiceInstance;
    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("NotificationService", "Running");

        String trackingId = intent.getStringExtra(TRACKING_ID.name());
        trackableTrackingsServiceInstance = TrackableTrackingsService.getInstance();
        trackableTrackingsServiceInstance.init(getApplicationContext()); // In case no access to databse

        if(trackingId != null) {
            reSendNotification(trackingId);
        } else {
            checkForPendingNotifications();
        }

        stopSelf();
    }

    private void checkForPendingNotifications() {
        List<Tracking> trackings = trackableTrackingsServiceInstance.obtainAllSortedByDate();
        for(Tracking tracking: trackings) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int period = Integer.valueOf(prefs.getString("settings_snooze", "1"));
            int minutes2Meeting = Integer.valueOf(prefs.getString("settings_notification", "1"));

            Calendar futureTime = Calendar.getInstance();
            futureTime.set(Calendar.MINUTE, futureTime.get(Calendar.MINUTE) + minutes2Meeting);
            futureTime.set(Calendar.SECOND, 0);
            futureTime.set(Calendar.MILLISECOND, 0);

            Calendar meetingTime = Calendar.getInstance();
            meetingTime.setTime(tracking.getMeetTime());
            meetingTime.set(Calendar.SECOND, 0);
            meetingTime.set(Calendar.MILLISECOND, 0);


            Log.d("NotificationService", "Future time: " + futureTime.getTime().toString() + " MeetingTime: " + meetingTime.getTime().toString());
            if(futureTime.getTime().equals(meetingTime.getTime())) {
                createNotification(tracking, minutes2Meeting, period);
            }
        }
    }


    private void createNotification(Tracking tracking, int minutes2Meeting, int snoozePeriod) {
        Log.d("NotificationService", "Sending notification: " + tracking.toString());

        Intent trackingIntent = new Intent(getApplicationContext(), TrackingEditActivity.class);
        trackingIntent.putExtra(TRACKABLE_ID.name(), tracking.getIdTrackable());
        trackingIntent.putExtra(TRACKING_ID.name(), tracking.getId());
        trackingIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        PendingIntent trackingPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, trackingIntent, 0);

        int randomId = (int) Math.abs(Math.random() * 1000);

        Intent cancelIntent = new Intent(getApplicationContext(), CancelNotificationDetector.class);
        cancelIntent.putExtra(NOTIFICATION_ID_KEY, randomId);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, cancelIntent, 0);

        Intent snoozeIntent = new Intent(getApplicationContext(), SnoozeNotificationDetector.class);
        snoozeIntent.putExtra(NOTIFICATION_ID_KEY, randomId);
        snoozeIntent.putExtra(TRACKING_ID.name(), tracking.getId());
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, snoozeIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder( getApplicationContext(), NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.calendar)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_content, tracking.getTitle(), minutes2Meeting))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(trackingPendingIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_info_black_24dp, getString(R.string.notification_snooze, snoozePeriod), snoozePendingIntent)
                .addAction(R.drawable.ic_info_black_24dp, getString(R.string.notification_cancel), cancelPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(randomId, mBuilder.build());
    }

    public void reSendNotification(String trackingId) {
        Tracking tracking = trackableTrackingsServiceInstance.getTrackingById(trackingId);

        if(tracking != null) {
            Log.d("NotificationService", "Sending reSendNotification");

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int period = Integer.valueOf(prefs.getString("settings_snooze", "1"));
            int minutes2Meeting = Integer.valueOf(prefs.getString("settings_notification", "1"));

            createNotification(tracking, minutes2Meeting, period);
        }

    }
}
