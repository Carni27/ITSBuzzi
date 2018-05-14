package it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Beans.BeanNews;

/**
 * Questa classe contiene dati generici che
 * possono servire durante il cicl di vita
 * dell'applicazione.
 * Questi dati verranno calcellati con il
 * terminarsi della applicazione
 * <p>
 * Created by ricca on 06/11/2015.
 */
public class Data {

    private static SharedPreferences sharedpreferences;
    private static ArrayList<BeanNews> arrayNews;
    private static HashMap<String, String> materie;
    private static HashMap<String, String> tipoVoti;
    private static HashMap<String, String> quadr;

    public static HashMap<String, String> getMaterie() {
        return materie;
    }

    public static void setMaterie(HashMap<String, String> materie) {
        Data.materie = materie;
    }

    public static HashMap<String, String> getTipoVoti() {
        return tipoVoti;
    }

    public static void setTipoVoti(HashMap<String, String> tipoVoti) {
        Data.tipoVoti = tipoVoti;
    }

    public static HashMap<String, String> getQuadr() {
        return quadr;
    }

    public static void setQuadr(HashMap<String, String> quadr) {
        Data.quadr = quadr;
    }


    public static ArrayList<BeanNews> getArrayNews() {
        return arrayNews;
    }

    public static void setArrayNews(ArrayList<BeanNews> arrayNews) {
        Data.arrayNews = arrayNews;
    }

    public static SharedPreferences getSharedpreferences() {
        return sharedpreferences;
    }

    public static void setSharedpreferences(SharedPreferences sharedpreferences) {
        Data.sharedpreferences = sharedpreferences;
    }

}
