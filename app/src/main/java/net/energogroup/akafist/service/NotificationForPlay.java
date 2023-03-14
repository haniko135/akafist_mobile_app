package net.energogroup.akafist.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import net.energogroup.akafist.R;
import net.energogroup.akafist.models.LinksModel;

public class NotificationForPlay {
    public static final String CHANNEL_ID="playing_audios";

    public static final String ACTION_PLAY="actionplay";

    public static Notification notification;

    public static void createNotification(Context context, LinksModel linksModel, int playButton){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        Log.e("LINKS_MODEL_IN", linksModel.getName());

        Bitmap image = BitmapFactory.decodeResource(context.getResources(), linksModel.getImage());

        Intent playIntent = new Intent(context, NotificationActionService.class).setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay;
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                    playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                    playIntent, PendingIntent.FLAG_IMMUTABLE);
        }

        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(linksModel.getName())
                .setLargeIcon(image)
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .addAction(playButton, "Включить", pendingIntentPlay)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManagerCompat.notify(1, notification);

    }

}
