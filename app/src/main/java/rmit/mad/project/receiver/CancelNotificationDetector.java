package rmit.mad.project.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static rmit.mad.project.service.NotificationService.NOTIFICATION_ID_KEY;

public class CancelNotificationDetector extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CancelNotificationctor", "REceiving");

        int notificationId = intent.getIntExtra(NOTIFICATION_ID_KEY, 0);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
    }

}
