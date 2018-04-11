package metrixapp.caseonit.blackscreen;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makePermissionOverlay();
        initialize();
    }

    private void initialize(){
        stopServiceIfActive();
        if (Settings.canDrawOverlays(this)) {
            Intent i = new Intent(MainActivity.this, MainService.class);
            startService(i);
            makeNotification();
        }
        finish();
    }

    private void stopServiceIfActive(){
        if(MainService.STATE == MainService.ACTIVE){
            Intent i=new Intent(MainActivity.this, MainService.class);
            stopService(i);
        }
    }

    private void makePermissionOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
    }

    private void makeNotification(){
        NotificationCompat.Builder nb=new NotificationCompat.Builder(this);
        nb.setSmallIcon(R.drawable.notif);
        nb.setContentTitle("BlackScreen Medux");
        nb.setContentText("Active");
        nb.setAutoCancel(true);
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        nb.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(0x355, nb.build());
        }
    }

}
