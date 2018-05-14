package it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link Fragment}
 * e si occupa di mostrare la schermatea di errore
 * con una eventuale scritta e un pulsante per eseguire qualcosa.
 * <p>
 * Created by ricca on 12/07/2015.
 */
public class ErrorViewFragment extends Fragment {

    private static View view;
    private String msg;
    private View.OnClickListener listener;

    public ErrorViewFragment() {
    }

    @SuppressLint("ValidFragment")
    public ErrorViewFragment(String msg, View.OnClickListener listener) {
        this.listener = listener;
        this.msg = msg;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.error_layout, container, false);
            view.findViewById(R.id.retry_error_view_btn).setOnClickListener(listener);
            ((TextView) view.findViewById(R.id.msg_error_view)).setText(msg);
        } catch (InflateException e) {
            Log.e("", e.toString());
        }
        return view;
    }
}
