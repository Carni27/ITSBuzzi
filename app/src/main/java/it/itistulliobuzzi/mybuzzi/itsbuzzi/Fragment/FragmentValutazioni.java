package it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpValutazioni;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

public class FragmentValutazioni extends Fragment {

    private MainActivity mainActivity;

    public FragmentValutazioni() {
    }

    @SuppressLint("ValidFragment")
    public FragmentValutazioni(MainActivity mainActivity) {
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
            mainActivity.setTitle("Valutazioni");

            HashMap<String, String> datiInvio = new HashMap<>();
            Log.e("matricola", Functions.getMatricola());
            datiInvio.put(Constant.MATRICOLA, Functions.getMatricola());
            datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);

            ListView lv = (ListView) view.findViewById(R.id.list_valutazioni);

            HttpValutazioni httpValutazioni = new HttpValutazioni(mainActivity, datiInvio, lv);
            httpValutazioni.execute();

        } catch (InflateException e) {
            Log.e("", e.toString());
            return null;
        }

        return view;
        */
        return null;
    }

}
