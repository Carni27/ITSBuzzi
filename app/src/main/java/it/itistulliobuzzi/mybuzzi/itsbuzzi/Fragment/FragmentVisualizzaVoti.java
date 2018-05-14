package it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpVisualizzaVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by a31167 on 07/06/2016.
 */
public class FragmentVisualizzaVoti extends Fragment {

    private MainActivity mainActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HttpVisualizzaVoti httpVisualizzaVoti;

    public FragmentVisualizzaVoti() {
    }

    @SuppressLint("ValidFragment")
    public FragmentVisualizzaVoti(MainActivity mainActivity) {
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
        View view = inflater.inflate(R.layout.fragment_voti, container, false);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {

            mainActivity.setTitle("Media Voti");

            final HashMap<String, String> datiInvio = new HashMap<>();
            datiInvio.put(Constant.MATRICOLA, Functions.getMatricola());
            datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);

            final ListView lv = (ListView) view.findViewById(R.id.voti_list);
            final TextView nullaPresente = (TextView) view.findViewById(R.id.text_voti_nulla);
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_voti);

            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshScroll(datiInvio, lv, nullaPresente);
                }
            });

            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    refreshScroll(datiInvio, lv, nullaPresente);
                }
            });


        } catch (InflateException e) {
            Log.e("", e.toString());
            return null;
        }
        return view;
    }

    /**
     * Fetching movies json by making http call
     */
    private void refreshScroll(HashMap<String, String> datiInvio, ListView lv, TextView nullaPresente) {
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);
        httpVisualizzaVoti = new HttpVisualizzaVoti(mainActivity, datiInvio, lv, swipeRefreshLayout, nullaPresente);
        httpVisualizzaVoti.execute();
    }

    public void stopRefresh() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing() && httpVisualizzaVoti != null && !httpVisualizzaVoti.isCancelled()) {
            swipeRefreshLayout.setRefreshing(false);
            httpVisualizzaVoti.cancel(true);
        }
    }

}
