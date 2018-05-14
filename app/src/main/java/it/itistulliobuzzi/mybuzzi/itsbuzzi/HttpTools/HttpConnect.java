package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Premessa: abbiamo utilizzato delle classi per la connessione deprecate nella versione 6(Marshmellow)
 * di android perchè non ci sono abbastanza guide e perchè sappiamo che queste funzionano
 * e che non presentano errori.
 * <p>
 * Questa &egrave; una sottoclasse dalla classe {@link AsyncTask} e sioccupa dell' invio e ricezione dei dati
 * tra l'applicazione e il server.
 * Si occupa anche della gestione dei vari errori causati d server.
 * <p>
 * Ricordiamo inoltre che un oggetto JSON &egrave; rappresentato delle '{}', invege un array JSON dalle '[]'.
 * <p>
 * Created by ricca on 01/03/16.
 */
public abstract class HttpConnect extends AsyncTask<Void, String, HttpResponse> {

    View.OnClickListener listener;
    private ProgressDialog progressDialog;
    private String result = null;
    private String url = "";
    private String messageInsideDialog = "";
    private Activity a;
    private HashMap<String, String> datiInvio;
    private HttpOptions httpOptions;

    public HttpConnect(Activity a, String url, HashMap<String, String> datiInvio, String messageInsideDialog) {
        this.a = a;
        this.url = url;
        this.datiInvio = datiInvio;
        this.messageInsideDialog = messageInsideDialog;
        this.httpOptions = new HttpOptions();
    }

    public HttpConnect(Activity a, String url, HashMap<String, String> datiInvio, String messageInsideDialog, HttpOptions httpOptions) {
        this.a = a;
        this.url = url;
        this.datiInvio = datiInvio;
        this.messageInsideDialog = messageInsideDialog;
        this.httpOptions = httpOptions;
    }

    @Override
    protected void onPreExecute() {
        if (httpOptions.getShowProgressBar()) {
            progressDialog = new ProgressDialog(a);
            progressDialog.setMessage(messageInsideDialog);
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    HttpConnect.this.cancel(true);
                    cancelRequest();
                    stopLoading();
                }
            });
        }
        listener = getListnerError();
    }

    @Override
    protected HttpResponse doInBackground(Void... voids) {
        try {
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 5000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);
            HttpPost httppost = new HttpPost(url);

            if (datiInvio != null) {
                List<NameValuePair> nameValuePairs = new ArrayList();
                if (httpOptions.getSendWithJson()) {
                    JSONObject jsonobj = new JSONObject();
                    for (Map.Entry<String, String> entry : datiInvio.entrySet()) {
                        jsonobj.accumulate(entry.getKey(), entry.getValue());
                    }
                    Log.e("HttpConnect", jsonobj.toString());
                    nameValuePairs.add(new BasicNameValuePair("req", jsonobj.toString()));

                } else {
                    for (Map.Entry<String, String> entry : datiInvio.entrySet()) {
                        nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                    }
                }
                // Use UrlEncodedFormEntity to send in proper format which we need
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            InputStream inputStream = response.getEntity().getContent();
            result = Functions.convertInputStreamToString(inputStream);
            return response;

        } catch (ConnectTimeoutException e) {
            return null;
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(HttpResponse response) {
        if (response != null) {
            Log.e("Connect", result);
            Log.e("Connect", String.valueOf(httpOptions.isErrorActivity()));
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                handlerResponse(result);
            } else {
                if (statusCode == 404) { // When Http response code is '404'
                    result = "Code:404 Requested resource not found";
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    result = "Code:500 Something went wrong at server end";
                }
                // When Http response code other than 404, 500
                else {
                    result = "Code:" + String.valueOf(statusCode) + "Unexpected Error occcured! [Most common Error: Device might "
                            + "not be connected to Internet or remote server is not up and running], check for other errors as well";
                }
                /*if (httpOptions.isErrorActivity()) {
                    Functions.switchToActivity(new Intent(a, MainActivity.class), a);
                    Functions.switchFragment(new ErrorViewFragment(result, listener), a);
                } else {
                    Functions.switchFragment(new ErrorViewFragment(result, listener), a);
                }*/
            }
        } else if(httpOptions.getShowErrorView()) {
           /* if (httpOptions.isErrorActivity()) {
                Functions.switchToActivity(new Intent(a, MainActivity.class), a);
                Functions.switchFragment(new ErrorViewFragment("Errore di connessione. Probabilmente il server è offline oppure la tua connessione è lenta", listener), a);
            } else {
                Functions.switchFragment(new ErrorViewFragment("Errore di connessione. Probabilmente il server è offline oppure la tua connessione è lenta", listener), a);
            }*/
        }
        stopLoading();
        return;
    }

    public String getResult(){ return this.result; }

    /**
     * Deve essere implementata per gestire la risposta del server.
     * Qui la getsione dei codici diversi da 200 &egrave; già stata fatta quindi
     * non necessitano controlli.
     *
     * @param s La stringa di risposta dal server nel caso il codice sia 200
     */
    protected abstract void handlerResponse(String s);

    /**
     * Eseguito quando si clicca nel pulsante della view
     *
     * @return View.OnClickListener
     * @Link{Fragment.ErrorViewFragment}
     */
    protected abstract View.OnClickListener getListnerError();

    /**
     * Serve per fermare il caricamento
     * (ideato per fermare i vari tipi di caricamento).
     * <p>
     * Di default ferma la progress dialog presente in questa classe
     * se nelle opzioni &egrave; settato di usarla, altrimenti non
     * esegue nulla.
     */
    public void stopLoading() {
        if (httpOptions.getShowProgressBar())
            this.progressDialog.dismiss();
    }

    /**
     * @return HashMap di dati da inviare
     */
    public HashMap<String, String> getDatiInvio() {
        return this.datiInvio;
    }

    /**
     * Quando viene cancellato questo {@link AsyncTask}
     * viene eseguito tutto ciò che &egrave; in questo metodo insieme
     * a metodo stopLoading e l'istruzione che cancella questo
     * {@link AsyncTask}.
     * <p>
     * Viene utilizzato per dirottare l'utente su altre sezioni
     * della applicazione quando viene cacellata questa richiesta.
     */
    protected void cancelRequest() {
        MainActivity.gotoNews();
    }
}
