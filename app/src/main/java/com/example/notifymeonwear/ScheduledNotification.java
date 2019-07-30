package com.example.notifymeonwear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ScheduledNotification extends BroadcastReceiver {
    int requestCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is a default notification");
            notificationManager.createNotificationChannel(channel);
        }

        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, i, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Action action = new
                NotificationCompat.Action.Builder(
                R.mipmap.ic_launcher,
                "Click to view",
                pendingIntent).build();

        NotificationCompat.WearableExtender extender = new
                NotificationCompat.WearableExtender();
        extender.addAction(action);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle(name);
        builder.setContentText(desc);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.extend(extender);

        Notification n = builder.build();
        notificationManager.notify(123, n);
    }
}
