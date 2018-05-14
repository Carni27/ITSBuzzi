package it.itistulliobuzzi.mybuzzi.itsbuzzi.GCM;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanNotif;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.ObjectSerializer.ObjectSerializer;

/**
 * Questa &egrave; una sottoclasse dalla calsse {@link IntentService}
 * e si occupa di gestire e mostrare le notifiche di tipo
 * push (GCM).
 * <p>
 * Created by ricca on 12/04/16.
 */

public class GCMNotificationIntentService extends IntentService {
    NotificationCompat.Builder builder;
    // Sets an ID for the notification, so it can be updated
    private final int notifyID = 0;
    private Boolean comunicazioni;
    private Boolean compiti;
    private Boolean verifiche;
    private ArrayList<BeanNotif> notifArray;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                sendNotification(extras.get(Constant.MSG_KEY) + "");
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Mostra la notifica e gestisce i vari casi
     *
     * @param msg Stringa contenente il messaggio inviato dal server sottoforma di JSON
     */
    private void sendNotification(String msg) {
        Log.e("Notifica", msg);
        try {

            notifArray = new ArrayList<BeanNotif>();

            ArrayList<BeanNotif> notifArrayCp = (ArrayList<BeanNotif>)ObjectSerializer.deserialize(getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).getString(Constant.ARRAY_NOTIFICATION, ObjectSerializer.serialize(new ArrayList<>())));

            comunicazioni = getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).getBoolean(Constant.SWITCH_COMUNICAZIONI, true);
            compiti = getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).getBoolean(Constant.SWITCH_ES_CASA, true);
            verifiche = getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).getBoolean(Constant.SWITCH_VERIFICA, true);

            //Agiunngo le notifiche nuove da JSON
            JSONArray jsonArray = new JSONArray(msg);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                BeanNotif bn = new BeanNotif(jsonObject.getString("nome"), jsonObject.getString("cognome"),
                                                jsonObject.getString("testo"), jsonObject.getString("id"),
                                                jsonObject.getString("data"),jsonObject.getString("compito"),
                                                jsonObject.getString("comunicazione")
                                            );
                addNotify(bn);
            }

            //Aggiungo le notifiche vecchie controllandole
            for (BeanNotif bn: notifArrayCp) {
                addNotify(bn);
            }

            //Creo l'Intent per andare alla app
            Intent resultIntent = new Intent(this, MainActivity.class);
            resultIntent.putExtra(Constant.INTENT_NOTIFICATION, true);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(getNotificationIcon())
                    .setColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
            if (notifArray.size() == 0) { //Se è vuoto finisco
                return;
            }else if (notifArray.size() == 1) {    // Nel caso ci sia solo una notifica
                String title = notifArray.get(0).getData().concat(" ").concat(notifArray.get(0).getNome())
                        .concat(" ").concat(notifArray.get(0).getCognome());
                String message = notifArray.get(0).getTesto();
                mNotifyBuilder.setContentTitle(title)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(message));
                //aggingo nel bundle dell'intent i dati per la mainactivity
                resultIntent.putExtra(Constant.INTENT_NOTIFICATION_DATA, notifArray.get(0).getData());
            } else {
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                inboxStyle.setBigContentTitle("MyBuzzi");
                for (BeanNotif bn: notifArray) {
                    inboxStyle.addLine(bn.getTesto());
                }
                mNotifyBuilder.setContentTitle("MyBuzzi")
                        .setContentText("Hai " + notifArray.size() + " nuove notifiche");
                mNotifyBuilder.setStyle(inboxStyle);
            }

            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                    resultIntent, PendingIntent.FLAG_ONE_SHOT);
            mNotifyBuilder.setContentIntent(resultPendingIntent);

            // Set Vibrate, Sound and Light
            int defaults = 0;
            defaults = defaults | Notification.DEFAULT_LIGHTS;
            defaults = defaults | Notification.DEFAULT_VIBRATE;
            defaults = defaults | Notification.DEFAULT_SOUND;

            mNotifyBuilder.setDefaults(defaults);
            // Set autocancel
            mNotifyBuilder.setAutoCancel(true);
            // Post a notification
            if (mNotificationManager != null)
                mNotificationManager.notify(notifyID, mNotifyBuilder.build());

            //Aggiorno l'id della notifica
            SharedPreferences.Editor editor = getSharedPreferences(Constant.SHAREDPREFERENCESKEY, Context.MODE_PRIVATE).edit();
            editor.putString(Constant.ARRAY_NOTIFICATION, ObjectSerializer.serialize(notifArray));
            editor.apply();

        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A seconda della versione del dispositivo restituisce l'icona
     * più consona
     *
     * @return Int l'id dell'immagine da mostrare
     */
    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.icon_noti : R.drawable.ic_launcher;
    }

    /**
     * Controlla se può mostrarlo in base alle impostazioni
     * @param bn BeanNotif da controllare
     */
    private void addNotify(BeanNotif bn){
        if(!comunicazioni && !bn.getComunicazione().equals("null")){
            return;
        }
        if(!compiti && bn.getCompito().equals("0")){
            return;
        }
        if(!verifiche && bn.getCompito().equals("1")){
            return;
        }
        if(!notifArray.contains(bn)){
            notifArray.add(bn);
        }
    }
}
