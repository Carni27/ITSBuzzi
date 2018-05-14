package it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.rey.material.widget.CheckBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanUser;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment.ErrorViewFragment;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpDeleteGcm;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.ObjectSerializer.ObjectSerializer;

/**
 * Questa classe si occupa di di racchiudere tutte
 * le funzioni generiche che vengono utilizzate
 * in altre classi.
 * <p>
 * Created by ricca on 10/02/16.
 */
public class Functions {

    /**
     * Controlla se &egrave; online o no
     *
     * @param activity Activity attiva
     * @return <ul type='disc'>
     * <li><b>True</b>: &Egrave; online</li>
     * <li><b>False</b>: &Egrave; offline</li>
     * </ul>
     */
    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * Cambia l'activity con un'altra activity con l'effetto fade
     *
     * @param i L'intent
     * @param a L'activity che deve terminare
     */
    public static void switchToActivity(Intent i, Activity a) {
        a.startActivity(i);
        a.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        a.finish();
    }

    /**
     * Controlla se &egrave; connesso ad internet, altrimenti blocca tutto (Non da la possibilità di navigare
     * nelle Navigation) e fa apparire la schermata ErrorVirwFragment
     *
     * @param i Intent che viene eseguito se &egrave; connesso
     * @param a Activity di base
     */
    public static void checkInternetConnectionGraphics(Intent i, final Activity a) {
        if (!Functions.isOnline(a)) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Functions.isOnline(a)) {
                        MainActivity.gotoNews();
                    }
                }
            };
            switchFragment(new ErrorViewFragment(a.getResources().getString(R.string.connection_error), listener), a);
            MainActivity.getmenuContacts().setVisibility(View.VISIBLE);
        } else {
            Functions.switchToActivity(i, a);
        }
    }

    /**
     * Controlla se &egrave; connesso ad internet, altrimenti blocca tutto (Non da la possibilità di navigare
     * nelle Navigation) e fa apparire la schermata ErrorVirwFragment
     *
     * @param f Fragment che viene mostrato se &egrave; connesso
     * @param a Activity di base
     */
    public static void checkInternetConnectionGraphics(Fragment f, final Activity a) {
        if (!Functions.isOnline(a)) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Functions.isOnline(a)) {
                        MainActivity.gotoNews();
                    }
                }
            };
            switchFragment(new ErrorViewFragment(a.getResources().getString(R.string.connection_error), listener), a);
            MainActivity.getmenuContacts().setVisibility(View.VISIBLE);
        } else {
            switchFragment(f, a);
        }
    }

    /**
     * Setta il fragment passato
     *
     * @param f fragment che mostra
     * @param a Activity dove viene mostrato il fragment
     */
    public static void switchFragment(Fragment f, Activity a) {
        FragmentTransaction ft = a.getFragmentManager().beginTransaction();
        ft.replace(R.id.container, f);
        ft.commit();
    }

    /**
     * @param inputStream Stream che verrà convertito in una stringa
     * @return restituisce la stringa convertita dall' inputStream
     * @throws IOException Nel caso ci siano errori di tipo Input/Output
     */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null)
            result.append(line);

        inputStream.close();
        return result.toString();

    }

    /**
     * Mostra una semplice dialog
     *
     * @param a       Activity dove deve essere mostrata
     * @param title   Il titolo della dialog
     * @param message Il messaggio della dialog
     */
    public static void showAlertDialog(Activity a, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(a).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    /**
     * Mostra una semplice dialog
     *
     * @param a        Activity dove deve essere mostrata
     * @param title    Il titolo della dialog
     * @param message  Il messaggio della dialog
     * @param positive Azione che deve essere eseguita al click del BUTTON_POSITIVE
     * @param negative Azione che deve essere eseguita al click del BUTTON_NEGATIVE
     */
    public static void showAlertDialog(Activity a, String title, String message,
                                       DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        AlertDialog alertDialog = new AlertDialog.Builder(a).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", positive);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", negative);
        alertDialog.show();
    }

    public static void showAlertDialogMostrabile(Activity a, String message, String idSave){
        if(message == null){
            message = "";
        }
        if(idSave == null){
            idSave = "1";
        }

        final String idConstant = idSave;

        LayoutInflater inflater = a.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.view_dialog_mostrabile, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(a);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Attenzione!");

        ((TextView) dialogView.findViewById(R.id.messgeDialogMostrare)).setText(message);

        ((CheckBox)dialogView.findViewById(R.id.checkBoxNonMostrare)).setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = Data.getSharedpreferences().edit();
                //Se spuntano la checkbox ho true in ischeck ma io lo salvo come false perche voglio che non lo mostri
                editor.putBoolean(Constant.DIALOG_MOSTRABILE + idConstant, !isChecked);
                editor.apply();
            }
        });

        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        if(Data.getSharedpreferences().getBoolean(Constant.DIALOG_MOSTRABILE + idConstant, true)){
            dialogBuilder.show();
        }
    }



    /**
     * @return Restituisce il nome dello studente
     */
    @Nullable
    public static String getNome() {
        boolean loggato = Data.getSharedpreferences().getBoolean(Constant.LOGGED, false);
        return loggato ? Data.getSharedpreferences().getString(Constant.NOME, "") : null;
    }

    /**
     * @return Restituisce il cognome dello studente
     */
    @Nullable
    public static String getCognome() {
        boolean loggato = Data.getSharedpreferences().getBoolean(Constant.LOGGED, false);
        return loggato ? Data.getSharedpreferences().getString(Constant.COGNOME, "") : null;
    }

    /**
     * @return String Restituisce la matricola dello studente
     */
    @Nullable
    public static String getMatricola() {
        boolean loggato = Data.getSharedpreferences().getBoolean(Constant.LOGGED, false);
        String user = loggato ? Data.getSharedpreferences().getString(Constant.USERNAME, "") : null;
        return user != null ? user.toLowerCase().replaceAll("a", "") : null;
    }

    /**
     * @return String Restituisce l'username dello studente
     */
    @Nullable
    public static String getUsername() {
        boolean loggato = Data.getSharedpreferences().getBoolean(Constant.LOGGED, false);
        return loggato ? Data.getSharedpreferences().getString(Constant.USERNAME, "") : null;
    }

    /**
     * Controlla se lo studente è loggato o meno
     *
     * @return <ul type='disc'>
     * <li><b>True</b>: se &egrave; loggato</li>
     * <li><b>False</b>: se non &egrave; loggato </li>
     * </ul>
     */
    public static boolean isLogged() {
        return Data.getSharedpreferences().getBoolean(Constant.LOGGED, false);
    }

    /**
     * Converte una stringa in MD5
     *
     * @param s Stringa da convertirte in MD5
     * @return Stringa convertita in MD5
     */
    @NonNull
    public static String MD5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Controlla se il dispositivo ha il Google Play Services APK.
     * Se non è presente, mostra all'utente una dialog per scaricare
     * l'APK.
     *
     * @param a {@link Activity} attiva.
     * @return <ul type='disc'>
     * <li><b>True</b>: se &egrave; presente</li>
     * <li><b>False</b>: se non &egrave; presente </li>
     * </ul>
     */
    public static boolean checkPlayServices(Activity a) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(a);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, a,
                        Constant.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("checkPlaySErvice", "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    /**
     * Send matricola and gcmId to server to remove this pair from Notifiche's table
     *
     * @param a Activity
     */
    public static void deleteGcm(Activity a) {
        HashMap<String, String> datiInvio = new HashMap<>();
        datiInvio.put(Constant.REG_ID, Data.getSharedpreferences().getString(Constant.REG_ID, ""));
        datiInvio.put(Constant.MATRICOLA, Functions.getMatricola());
        datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
        new HttpDeleteGcm(a, datiInvio).execute();
    }

    /**
     * @return Un array di UserBean con salvate tutte le credenziali
     */
    @Nullable
    public static ArrayList<BeanUser> getCredenzialiSalvate() {
        try {
            return (ArrayList) ObjectSerializer.deserialize(Data.getSharedpreferences().getString(Constant.ARRAY_CREDENZIALI_SALVATE, ObjectSerializer.serialize(new ArrayList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cancella lo BeanUser che ha lo stesso username dalle credenziali salvate
     *
     * @param user Nome dell'user
     */
    public static void removeCredenzialeSalvata(String user) {
        ArrayList<BeanUser> buA = getCredenzialiSalvate();
        if (buA != null) {
            for (BeanUser bu : buA) {
                if (bu.getUsername().equals(user)) {
                    removeCredenzialeSalvata(bu);
                    break;
                }
            }
        }
    }

    /**
     * Cancella lo BeanUser passato dalle credenziali salvate
     *
     * @param bu BeanUser da rimuovere
     */
    public static void removeCredenzialeSalvata(BeanUser bu) {
        ArrayList<BeanUser> buA = getCredenzialiSalvate();
        if (buA != null) {
            buA.remove(bu);
            setArrayUserTOSharedPreferences(buA);
        }
    }

    /**
     * Aggiunge un UserBean
     *
     * @param username Username del BeanUser
     * @param password Password del BeanUser
     */
    public static void addUsersToSharedPreferences(String username, String password) {
        addUsersToSharedPreferences(new BeanUser(username, password));
    }

    /**
     * Aggiunge un BeanUser
     *
     * @param bu BeanUser da aggiungere
     */
    public static void addUsersToSharedPreferences(BeanUser bu) {
        ArrayList<BeanUser> userList = Functions.getCredenzialiSalvate();
        if (userList == null) {
            userList = new ArrayList<>();
        }
        if (!userList.contains(bu)) {
            userList.add(bu);
        }
        setArrayUserTOSharedPreferences(userList);
    }

    /**
     * Imposta un array nelle credenziali salvate
     *
     * @param userList Array da impostare
     */
    public static void setArrayUserTOSharedPreferences(ArrayList<BeanUser> userList) {
        SharedPreferences.Editor editor = Data.getSharedpreferences().edit();
        try {
            editor.putString(Constant.ARRAY_CREDENZIALI_SALVATE, ObjectSerializer.serialize(userList));
            editor.apply();
        } catch (IOException e) {
            Log.e("HttpLogin", e.toString());
        }
    }

    /**
     * Restituisce il numero passato arrotondato alle cifre desiderate
     *
     * @param n            Numero da arrotondare
     * @param position     Indica quante cifre dopo la virgola
     * @param roundingMode Tipo di arrootondamento {@link RoundingMode}
     * @return Numero arrotondato
     */
    public static Double round(double n, int position, RoundingMode roundingMode) {
        StringBuilder sb = new StringBuilder();
        sb.append("#.");
        for (int i = 0; i < position; i++)
            sb.append("#");
        DecimalFormat df = new DecimalFormat(sb.toString());
        df.setRoundingMode(roundingMode);
        return Double.valueOf(df.format(n).replace(",", "."));
    }

    /**
     * SAlva un boolean nelle Prefereces
     *
     * @param cons Stringa sul quale salvare il nome
     * @param b Boolean da salvare
     */
    public static void saveInPrefereces(String cons, boolean b){
        SharedPreferences.Editor editor = Data.getSharedpreferences().edit();
        editor.putBoolean(cons, b);
        editor.apply();
    }


}
