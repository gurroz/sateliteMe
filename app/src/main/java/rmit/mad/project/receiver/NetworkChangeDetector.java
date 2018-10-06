package rmit.mad.project.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import rmit.mad.project.service.SuggestionService;

public class NetworkChangeDetector  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(isOnline(context)) {
            Intent suggestionIntent = new Intent(context, SuggestionService.class);
            context.startService(suggestionIntent);
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }
}
