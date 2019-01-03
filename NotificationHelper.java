package com.example.joeco.ecotaxitest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;


public class NotificationHelper extends ContextWrapper {
    public static final String ChanID = "Channel1";
    public static final String ChanName = "Channel 1";
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
      createChannels();
    }

    private void createChannels() {
        NotificationChannel channel = new NotificationChannel(ChanID, ChanName, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message) {
        Intent resIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:02100000001"));
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,1,resIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), ChanID)
                .setContentText(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
    }
}
