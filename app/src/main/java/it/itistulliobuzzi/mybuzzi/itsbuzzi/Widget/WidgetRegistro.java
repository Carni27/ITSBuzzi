package it.itistulliobuzzi.mybuzzi.itsbuzzi.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;


import java.util.Calendar;
import java.util.Random;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetRegistro extends AppWidgetProvider {

    public static String WIDGET_BUTTON_OVER = "WIDGET_BUTTON_OVER";
    public static String WIDGET_BUTTON_BACK = "WIDGET_BUTTON_BACK";

    //static così mi resta aggiornata a tutti gli update
    public static Calendar mDate =  Calendar.getInstance();

    public static int randomNumber = new Random().nextInt();

    @Override
    public void onUpdate(Context context, AppWidgetManager
            appWidgetManager,int[] appWidgetIds) {

        /*int[] appWidgetIds holds ids of multiple instance
         * of your widget
         * meaning you are placing more than one widgets on
         * your homescreen*/
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = updateWidgetView(context,
                    appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i],
                    remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetView(Context context,
                                             int appWidgetId) {
        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),R.layout.widget_registro);

        //setto la data
        int mYear = mDate.get(Calendar.YEAR);
        int mMonth = mDate.get(Calendar.MONTH);
        int mDay = mDate.get(Calendar.DAY_OF_MONTH);
        Log.e("Widget",  String.valueOf(mDay + "-" + (mMonth + 1) + "-" + mYear));
        remoteViews.setTextViewText(R.id.txtDateWidget, String.valueOf(mDay + "-" + (mMonth + 1) + "-" + mYear)); //setto la data

        //Gestisco i pulsanti avanti e indietro per la data
        Intent intentOver = new Intent(context, getClass());
        intentOver.setAction(WIDGET_BUTTON_OVER);
        PendingIntent pendingIntentOver = PendingIntent.getBroadcast(context, 0, intentOver, 0);
        remoteViews.setOnClickPendingIntent(R.id.over_date_widget, pendingIntentOver);

        Intent intentBack = new Intent(context, getClass());
        intentBack.setAction(WIDGET_BUTTON_BACK);
        PendingIntent pendingIntentBack = PendingIntent.getBroadcast(context, 0, intentBack, 0);
        remoteViews.setOnClickPendingIntent(R.id.back_date_widget, pendingIntentBack );

        //Adapter per la ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        svcIntent.setData(Uri.fromParts("content", String.valueOf(appWidgetId+randomNumber), null));

        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,
                svcIntent);
        return remoteViews;
    }

    @Override
    public void onEnabled(Context context) {  }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WIDGET_BUTTON_OVER.equals(intent.getAction())) {
            Log.e("Widget", "over");
            mDate.add(Calendar.DAY_OF_MONTH, 1);
            onUpdate(context);
        }else if (WIDGET_BUTTON_BACK.equals(intent.getAction())) {
            Log.e("Widget", "back");
            mDate.add(Calendar.DAY_OF_MONTH, -1);
            onUpdate(context);
        }else {
            super.onReceive(context, intent);
        }
    }

    /**
     * A general technique for calling the onUpdate method,
    * requiring only the context parameter.
    */
    private void onUpdate(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance
                (context);

        // Uses getClass().getName() rather than MyWidget.class.getName() for
        // portability into any App Widget Provider Class
        ComponentName thisAppWidgetComponentName =
                new ComponentName(context.getPackageName(),getClass().getName()
                );
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                thisAppWidgetComponentName);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listViewWidget);

        onUpdate(context, appWidgetManager, appWidgetIds);
    }

}

