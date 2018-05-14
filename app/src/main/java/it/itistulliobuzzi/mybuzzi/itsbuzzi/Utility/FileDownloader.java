package it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility;

/**
 * Created by ricca on 09/02/16.
 */

import android.app.Activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Questa classe si occupa di scaricare unn qualsiasi
 * file da un url e di salvarlo in una qualsiasi cartella.
 */

public class FileDownloader {
    private static final int MEGABYTE = 1024 * 1024;

    public static void downloadFile(String fileUrl, File directory, Activity a) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {

            URL url = new URL(fileUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.setRequestMethod("GET");
            //urlConnection.setDoOutput(true);
            //urlConnection.setReadTimeout(2*1000);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            fileOutputStream = new FileOutputStream(directory);

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Functions.showAlertDialog(a, "Errore", e.getMessage());
        } catch (MalformedURLException e) {
            Functions.showAlertDialog(a, "Errore", e.getMessage());
        } catch (IOException e) {
            Functions.showAlertDialog(a, "Errore", e.getMessage());
        } catch (Exception e) {
            Functions.showAlertDialog(a, "Errore", e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
