package app.lifeplanner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent i) {
        String id = i.getStringExtra("id");
        String title = i.getStringExtra("title");
        String body = i.getStringExtra("body");
        if (id == null) return;
        if (title == null) title = "یادآوری";
        if (body == null) body = "";

        Reminders.ensureChannel(c);
        Intent open = new Intent(c, MainActivity.class);
        open.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent content = PendingIntent.getActivity(c, 0, open,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder b = new Notification.Builder(c, Reminders.CHANNEL)
                .setSmallIcon(R.drawable.ic_stat_bell)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new Notification.BigTextStyle().bigText(body))
                .setContentIntent(content)
                .setAutoCancel(true)
                .setCategory(Notification.CATEGORY_REMINDER);
        c.getSystemService(NotificationManager.class).notify(id.hashCode(), b.build());
    }
}
