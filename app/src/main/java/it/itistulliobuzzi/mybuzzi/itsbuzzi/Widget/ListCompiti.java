package it.itistulliobuzzi.mybuzzi.itsbuzzi.Widget;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpWidgetRegistro;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;

/**
 * Created by Riccardo on 03/06/2016.
 */
public class ListCompiti implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<JSONObject> listItemList;
    private Context context = null;
    private String username;

    public ListCompiti(Context context, Intent intent, String username) {
        this.context = context;
        this.username = username;
    }

    @Override
    public void onCreate() {

        listItemList = new ArrayList<>();
        try {
            boolean messageSet = false;

            if(username != null) {
                HashMap<String, String> datiInvio = new HashMap<>();
                datiInvio.put(Constant.USERNAME, username);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
                datiInvio.put(Constant.DATA, sdf.format(WidgetRegistro.mDate.getTime()));
                datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);

                Log.e("WidgetService", "chiede");
                HttpWidgetRegistro httpWidgetRegistro = new HttpWidgetRegistro(datiInvio);
                HttpResponse response = null;
                Log.e("WidgetService", "riceve");
                response = httpWidgetRegistro.execute().get();

                if(response == null) {
                    listItemList.add(new JSONObject().put("testo", "Nessuna connessione o problema al server").put("Nome", "").put("Cognome", ""));
                    messageSet = true;
                }else {
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode != 200) {
                        listItemList.add(new JSONObject().put("testo", "Errore").put("Nome", "").put("Cognome", ""));
                        messageSet = true;
                    } else {
                        String result = httpWidgetRegistro.getResult();
                        Log.e("WidgetService", result);
                        if (result != null) {
                            if (!result.equals("[]")) {
                                JSONArray jsonArray = new JSONArray(result);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if (jsonObject != null) {
                                        if (!jsonObject.get("compito").equals("")) {
                                            listItemList.add(jsonObject);
                                            messageSet = true;
                                        }
                                    }
                                }
                            }else{
                                messageSet = true;
                                listItemList.add(new JSONObject().put("testo", "Nessun compito da fare").put("Nome", "").put("Cognome", ""));
                            }
                        }
                    }
                }

            }else{
                messageSet = true;
                listItemList.add(new JSONObject().put("testo", "Fai l'accesso").put("Nome", "").put("Cognome", ""));
            }
            //Nel caso ci siano comunicazioni o altro ma non i compiti
            if(!messageSet){
                listItemList.add(new JSONObject().put("testo", "Nessun compito da fare").put("Nome", "").put("Cognome", ""));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("WidgetService", "fine");
    }

    @Override
    public void onDataSetChanged() {
        onCreate();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
    * Similar to getView of Adapter where instead of View
    * we return RemoteViews
    */
    @Override
    public RemoteViews getViewAt(int position) {

        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.item_compito);
        try{
            remoteView.setTextViewText(R.id.text_compito, Html.fromHtml(listItemList.get(position).getString("testo")));
            remoteView.setTextViewText(R.id.name_prof_compito, Html.fromHtml(listItemList.get(position).getString("Nome") + " " + listItemList.get(position).getString("Cognome")));
            remoteView.setViewVisibility(R.id.layout_letter_name_prof_compito, View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return listItemList.size();
    }
}
