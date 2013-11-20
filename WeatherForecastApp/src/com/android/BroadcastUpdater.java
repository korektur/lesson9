package com.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created with IntelliJ IDEA.
 * User: Ruslan
 * Date: 20.11.13
 * Time: 18:50
 */
public class BroadcastUpdater extends BroadcastReceiver {
    private static String boot_act = "android.intent.action.BOOT_COMPLETED";
    private String link;

    public BroadcastUpdater(String link){
        super();
        this.link = link;
    }

    @Override
    public void onReceive(Context context, Intent broadcastIntent){
        if (broadcastIntent.getAction().equals(boot_act))
            BroadcastUpdater.this.begin(context);
        Intent intent = new Intent(context, Weather.class);
        context.startService(intent);
    }

    public void begin(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, Weather.class), 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 15000, pendingIntent);
    }

}
