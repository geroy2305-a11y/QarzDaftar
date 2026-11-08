package uz.qarzdaftar.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.os.Build;

public class ReminderReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        int id = intent.getIntExtra("id", (int)System.currentTimeMillis());
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26 && nm.getNotificationChannel("qarz_reminders") == null) {
            nm.createNotificationChannel(new NotificationChannel("qarz_reminders", "Qarz muddati eslatmalari", NotificationManager.IMPORTANCE_HIGH));
        }
        Intent open = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        PendingIntent content = PendingIntent.getActivity(context, id, open, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Notification.Builder b = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(context, "qarz_reminders") : new Notification.Builder(context);
        b.setSmallIcon(android.R.drawable.ic_lock_idle_alarm).setContentTitle(title == null ? "Qarz Daftar" : title)
         .setContentText(message == null ? "Qarz muddati keldi" : message).setStyle(new Notification.BigTextStyle().bigText(message))
         .setAutoCancel(true).setContentIntent(content);
        nm.notify(id, b.build());
    }
}
