package it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.IOException;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.FileDownloader;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link Fragment}
 * e si occupa di scaricare e  mostrare PDF di un determinato
 * URL.
 * <p>
 * Per mostrare i PDF utiliziamo la classe {@link PDFView}
 */

public class PdfReader extends Fragment {


    private static View view;
    private static DownloadFile df;
    private MainActivity mainActivity;
    private String url;
    private String nameFile;
    private String nameFragment;
    private PDFView pdfView;
    private ProgressDialog pDialog;

    public PdfReader() {
    }

    @SuppressLint("ValidFragment")
    public PdfReader(MainActivity mainActivity, String url, String nameFile, String nameFragment) {
        Log.e("PdfReader", "PdfReader --- " + url);
        this.mainActivity = mainActivity;
        this.url = url;
        if (!nameFile.endsWith(".pdf"))
            nameFile += ".pdf";
        this.nameFile = nameFile;
        this.nameFragment = nameFragment;
    }

    /**
     * Concella il download se esiste
     */
    public static void cancelDownload() {
        if (df != null) {
            df.cancel(true);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.fragment_pdfreader, container, false);

            mainActivity.setTitle(nameFragment);

            pDialog = new ProgressDialog(mainActivity);
            pDialog.setMessage("Caricamento...");
            pDialog.setIndeterminate(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancelDownload();
                }
            });

            pdfView = (PDFView) view.findViewById(R.id.pdfview);
            df = new DownloadFile();
            df.execute(url, nameFile);

        } catch (InflateException e) {
            Log.e("", e.toString());
            return null;
        }
        return view;
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false

        }

        @Override
        protected String doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];

            try {
                final File dir = new File(mainActivity.getFilesDir() + "/downloadBuzzi");
                dir.mkdir();
                //Elimino tutti i  file per risparmiare memoria
                if (dir.isDirectory()) {
                    for (String child : dir.list()) {
                        new File(dir, child).delete();
                    }
                }
                File pdfFile = new File(dir.getAbsolutePath(), fileName);

                try {
                    pdfFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileDownloader.downloadFile(fileUrl, pdfFile, mainActivity);
                Log.e("Downloadfile", "path --- " + pdfFile.getAbsolutePath());
            } catch (Exception e1) {
                Functions.showAlertDialog(mainActivity, "Errore", e1.getMessage());
            }
            return fileName;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            try {
                File pdfFile = new File(mainActivity.getFilesDir() + "/downloadBuzzi/" + result);
                pdfView.fromFile(pdfFile)
                        .defaultPage(1)
                        .showMinimap(true)
                        .enableSwipe(true)
                        .load();
            } catch (Exception e) {
                Functions.showAlertDialog(mainActivity, "Errore", e.getMessage());
            }
        }
    }
}
