package uz.qarzdaftar.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.provider.Settings;
import android.Manifest;
import android.content.pm.PackageManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MainActivity extends Activity {
    private WebView webView;
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 33 && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
        }
        createChannel();
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new ReminderBridge(this), "AndroidReminder");
        setContentView(webView);
        webView.loadUrl("file:///android_asset/qarz_daftar.html");
    }
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("qarz_reminders", "Qarz muddati eslatmalari", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Qarz qaytarish muddati haqida eslatmalar");
            ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).createNotificationChannel(channel);
        }
    }
    public class ReminderBridge {
        Context context;
        ReminderBridge(Context c) { context = c; }
        @JavascriptInterface public void schedule(String id, long whenMillis, String title, String message) {
            AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(context, ReminderReceiver.class);
            intent.putExtra("title", title); intent.putExtra("message", message); intent.putExtra("id", id.hashCode());
            PendingIntent pi = PendingIntent.getBroadcast(context, id.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            if (Build.VERSION.SDK_INT >= 31 && !am.canScheduleExactAlarms()) {
                try { context.startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)); } catch (Exception ignored) {}
            }
            if (Build.VERSION.SDK_INT >= 23) am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, whenMillis, pi);
            else am.setExact(AlarmManager.RTC_WAKEUP, whenMillis, pi);
        }
        @JavascriptInterface public void cancel(String id) {
            Intent intent = new Intent(context, ReminderReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, id.hashCode(), intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
            if (pi != null) ((AlarmManager)context.getSystemService(ALARM_SERVICE)).cancel(pi);
        }
    }
}
