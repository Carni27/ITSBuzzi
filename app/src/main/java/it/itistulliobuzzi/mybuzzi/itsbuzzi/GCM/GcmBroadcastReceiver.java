package it.itistulliobuzzi.mybuzzi.itsbuzzi.GCM;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link WakefulBroadcastReceiver}
 * e si occupa di richiamare la classe che gestisce le notifiche
 * {@link GCMNotificationIntentService}
 *
 * Created by ricca on 12/04/16.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                GCMNotificationIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
