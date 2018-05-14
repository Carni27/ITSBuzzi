package it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpComunicazioni;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by g.panza on 20/04/2016.
 */
public class FragmentComunicazioni extends Fragment {

    private Activity mainActivity;

    public FragmentComunicazioni() {
    }

    @SuppressLint("ValidFragment")
    public FragmentComunicazioni(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*final View view = inflater.inflate(R.layout.fragment_valutazioni, container, false);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {
            mainActivity.setTitle("Comunicazioni");

            HashMap<String, String> datiInvio = new HashMap<>();
            Log.e("matricola", Functions.getMatricola());
            datiInvio.put(Constant.MATRICOLA, Functions.getMatricola());
            datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);

            ListView lv = (ListView) view.findViewById(R.id.list_valutazioni);

            HttpComunicazioni httpComunicazioni = new HttpComunicazioni(mainActivity, datiInvio, lv);
            httpComunicazioni.execute();

        } catch (InflateException e) {
            Log.e("", e.toString());
            return null;
        }
        return view;
        */
        return null;
    }
}
