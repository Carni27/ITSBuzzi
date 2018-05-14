package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity.LoginActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link HttpConnect}
 * e si occupa di inviare la matricola e la password
 * per poter fare il login e gestisce il caso nel quale
 * qualcosa sia sbagliato
 * <p>
 * Created by ricca on 01/03/16.
 */
public class HttpLogin extends HttpConnect {

    private Activity a;
    private boolean salvaCredenziali;
    private GoogleCloudMessaging gcmObj;
    private String regId = "";
    private HashMap<String, String> datiInvio;

    public HttpLogin(Activity a, HashMap<String, String> datiInvio, boolean salvaCredenziali) {
        super(a, Constant.URL_BUZZI + "login.php", datiInvio, "Controllando i tuoi dati...");
        this.a = a;
        this.datiInvio = datiInvio;
        this.salvaCredenziali = salvaCredenziali;
    }

    @Override
    protected void handlerResponse(String result) {
        //parse JSON data
        if (result != null) {
            try {
                Log.e("HttpLogin", "result --- " + result);
                boolean esiste = false;
                if (!result.equals("[]")) {
                    JSONArray jsonArray = new JSONArray(result);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    esiste = true;
                    SharedPreferences.Editor editor = Data.getSharedpreferences().edit();
                    editor.putBoolean(Constant.LOGGED, true);
                    editor.putString(Constant.USERNAME, datiInvio.get(Constant.USERNAME));
                    editor.putString(Constant.PASSWORD, datiInvio.get(Constant.PASSWORD));
                    editor.putString(Constant.NOME, jsonObject.getString(Constant.NOME));
                    editor.putString(Constant.COGNOME, jsonObject.getString(Constant.COGNOME));
                    if (salvaCredenziali) {
                        Functions.addUsersToSharedPreferences(datiInvio.get(Constant.USERNAME), datiInvio.get(Constant.PASSWORD));
                    }
                    editor.commit();
                }
                stopLoading();
                if (esiste) {
                    if (Functions.checkPlayServices(a)) {
                        // Register Device in GCM Server
                        registerInBackground(datiInvio.get(Constant.USERNAME));
                    }
                } else {
                    Functions.showAlertDialog(a, "Errore di autenticazione", "Username o password non corretti");
                }
            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            }
        }
    }

    @Override
    protected void cancelRequest() {
    }

    //Lo metto qui perchè va fatto solo al momento del login
    // AsyncTask to register Device in GCM Server
    private void registerInBackground(final String id) {
        new AsyncTask<Void, Void, String>() {

            private ProgressDialog progressDialog;

            protected void onPreExecute() {
                progressDialog = new ProgressDialog(a);
                progressDialog.setMessage("Ottenimento Gcm...");
                progressDialog.show();
                progressDialog.setCancelable(false);
            }

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(a.getBaseContext());
                    }
                    regId = gcmObj
                            .register(Constant.GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                this.progressDialog.dismiss();
                // Store RegId created by GCM Server in SharedPref
                storeRegIdinSharedPref(regId);
            }

        }.execute(null, null, null);
    }


    private void storeRegIdinSharedPref(String regId) {
        SharedPreferences.Editor editor = Data.getSharedpreferences().edit();
        editor.putString(Constant.REG_ID, regId);
        editor.commit();
        HashMap<String, String> datiInvio = new HashMap<>();
        datiInvio.put(Constant.REG_ID, regId);
        datiInvio.put(Constant.MATRICOLA, Functions.getMatricola());
        datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
        new HttpRegisterGcm(a, datiInvio).execute();
    }

    @Override
    protected View.OnClickListener getListnerError() {
        return listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.checkInternetConnectionGraphics(new Intent(a, LoginActivity.class), a);

            }
        };
    }

}
