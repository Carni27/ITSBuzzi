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
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.Adapter.ListAdapterNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpNews;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link Fragment}
 * e si occupa di mostrare la lista delle news e
 * di fare rischiesta di esse richiamando la
 * classe {@link HttpNews}
 * <p>
 * Created by ricca on 07/01/16.
 */
public class FragmentNews extends Fragment {

    private MainActivity mainActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    private HttpNews httpNews;

    public FragmentNews() {
    }

    @SuppressLint("ValidFragment")
    public FragmentNews(MainActivity mainActivity) {
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
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {

            mainActivity.setTitle("News");

            final HashMap<String, String> datiInvio = new HashMap<>();
            datiInvio.put(Constant.OFFSET_NEWS, "0");
            datiInvio.put(Constant.NUMERO_NEWS, "30");
            datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);

            final ListView lv = view.findViewById(R.id.home_list);
            final TextView nullaPresente = view.findViewById(R.id.text_news_nulla);
            swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_news);

            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshScroll(datiInvio, lv, nullaPresente);
                }
            });

            if (Data.getArrayNews() == null) {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        refreshScroll(datiInvio, lv, nullaPresente);
                    }
                });
            } else {
                if (Data.getArrayNews().isEmpty()) {
                    nullaPresente.setVisibility(View.VISIBLE);
                    lv.setAdapter(null);
                } else {
                    nullaPresente.setVisibility(View.GONE);
                    lv.setAdapter(new ListAdapterNews(mainActivity, Data.getArrayNews(), null));
                }
            }

            //Controllo quando "scrolla" la listView
            lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                private int mLastFirstVisibleItem;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    //Se scorre in basso nascondo i menu
                    if (mLastFirstVisibleItem < firstVisibleItem) {
                        Log.i("SCROLLING DOWN", "TRUE");
                        MainActivity.getmenuContacts().collapse();
                        MainActivity.getmenuContacts().setVisibility(View.INVISIBLE);
                    }
                    //Se scorre in alto faccio riapparire menu
                    if (mLastFirstVisibleItem > firstVisibleItem) {
                        Log.i("SCROLLING UP", "TRUE");
                        MainActivity.getmenuContacts().setVisibility(View.VISIBLE);
                    }
                    mLastFirstVisibleItem = firstVisibleItem;
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
        httpNews = new HttpNews(mainActivity, datiInvio, lv, swipeRefreshLayout, nullaPresente);
        httpNews.execute();
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public HttpNews getHttpNews() {
        return httpNews;
    }

    public void stopRefresh(){
        SwipeRefreshLayout srl = getSwipeRefreshLayout();
        HttpNews hN = getHttpNews();
        if (srl != null && srl.isRefreshing() && hN != null && !hN.isCancelled()) {
            srl.setRefreshing(false);
            hN.cancel(true);
        }
    }

}
