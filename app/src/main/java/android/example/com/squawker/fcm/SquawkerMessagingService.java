package android.example.com.squawker.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.example.com.squawker.MainActivity;
import android.example.com.squawker.R;
import android.example.com.squawker.data.Squawk;
import android.example.com.squawker.data.SquawkDatabase;
import android.example.com.squawker.provider.SquawkContract;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.threeten.bp.Instant;

import java.util.Map;


public class SquawkerMessagingService extends FirebaseMessagingService {

    private static final String JSON_KEY_AUTHOR = SquawkContract.COLUMN_AUTHOR;
    private static final String JSON_KEY_AUTHOR_KEY = SquawkContract.COLUMN_AUTHOR_KEY;
    private static final String JSON_KEY_MESSAGE = SquawkContract.COLUMN_MESSAGE;
    private static final String JSON_KEY_DATE = SquawkContract.COLUMN_DATE;

    private static final int NOTIFICATION_MAX_CHARACTERS = 30;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        final Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0) {
            final Squawk squawk = getSquawk(data);
            insertSquawk(squawk);
            sendNotification(squawk);
        }
    }

    private Squawk getSquawk(Map<String, String> data) {
        final long instant = Long.parseLong(data.get(JSON_KEY_DATE));
        return new Squawk(data.get(JSON_KEY_AUTHOR),
                data.get(JSON_KEY_AUTHOR_KEY),
                data.get(JSON_KEY_MESSAGE),
                Instant.ofEpochMilli(instant));
    }

    private void sendNotification(final Squawk squawk) {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String author = squawk.getAuthor();
        String message = squawk.getMessage();

        if (message.length() > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026";
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_duck)
                .setContentTitle(String.format(getString(R.string.notification_message), author))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void insertSquawk(final Squawk squawk) {
        final Squawk roomSquawk = new Squawk(squawk.getAuthor(), squawk.getAuthorKey(), squawk.getMessage(), squawk.getTimestamp());
        SquawkDatabase.getInstance(getApplicationContext()).squawkDAO().insertSquawk(roomSquawk);
    }
}
