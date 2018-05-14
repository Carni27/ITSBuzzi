package it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Questa &egrave; una sottoclasse della classe {@link TextView}
 * e si occupa di mostrare l'effetto di scrittura
 * in una view di tipo '<it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.TextViewTyper>'
 * <p>
 * Created by ricca on 28/03/16.
 */
public class TextViewTyper extends AppCompatTextView {

    public TextViewTyper(Context context) {
        super(context);
    }

    public TextViewTyper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewTyper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Effect typer Text View
     *
     * @param millisInFuture Millisecondi per tutto l'effetto
     * @param text           Testo da scrivere
     */
    public void typer(int millisInFuture, String text) {
        executeTyper((long) (millisInFuture / text.length()), text, this);
    }

    /**
     * Effect typer Text View
     *
     * @param text              Testo da scrivere
     * @param countDownInterval Millisecondi per ogni lettera
     */
    public void typer(String text, int countDownInterval) {
        executeTyper((long) (countDownInterval), text, this);
    }

    private void executeTyper(final long countDownInterval, final String text, final TextView tv) {
        new AsyncTask<Void, String, Void>() {

            @Override
            protected void onProgressUpdate(String... progress) {
                tv.setText(progress[0]);
            }

            @Override
            protected Void doInBackground(Void... params) {
                for (int i = 0; i <= text.length(); i++) {
                    publishProgress(text.substring(0, i));
                    try {
                        Thread.sleep(countDownInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();
    }
}
