package it.itistulliobuzzi.mybuzzi.itsbuzzi.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItems;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpRegistro;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Questa &egrave; una sottoclasse dalla classe {@link Fragment}
 * e si occupa di gestire i dati del registro e
 * di fare rischiesta di esse richiamando la
 * classe {@link HttpRegistro}
 */

public class FragmentRegistro extends Fragment {

    private Activity mainActivity;
    private Calendar mCurrentDate, mPrecDate;
    private ViewPagerItemAdapter adapter;
    private ViewPager viewPager;
    private EditText mDateEditText;
    private int timeRepeated;
    private HttpRegistro httpRegistro;

    public FragmentRegistro() {
    }

    @SuppressLint("ValidFragment")
    public FragmentRegistro(Activity mainActivity) {
        this.mainActivity = mainActivity;
        this.timeRepeated = 0;
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_registro, container, false);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {
            mainActivity.setTitle("Registro");

            mCurrentDate = Calendar.getInstance();
            mPrecDate = Calendar.getInstance();

            mDateEditText = (EditText) view.findViewById(R.id.txtDateEntered);
            updateEditText();
            mDateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mYear = mCurrentDate.get(Calendar.YEAR);
                    int mMonth = mCurrentDate.get(Calendar.MONTH);
                    int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker = new DatePickerDialog(mainActivity, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                            // Update the editText to display the selected date
                            mDateEditText.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                            // Set the mCurrentDate to the selected date-month-year
                            mPrecDate.setTime(mCurrentDate.getTime());
                            mCurrentDate.set(selectedYear, selectedMonth, selectedDay);
                            aggiornaRegistro();
                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.show();
                }
            });

            adapter = new ViewPagerItemAdapter(
                    ViewPagerItems.with(mainActivity)
                            .add("Comunicazioni", R.layout.fragment_list)
                            .add("Compiti", R.layout.fragment_list)
                            .add("Avvisi", R.layout.fragment_list)
                            .add("Argomenti", R.layout.fragment_list)
                            .create());

            viewPager = (ViewPager) view.findViewById(R.id.viewpager);
            viewPager.setAdapter(adapter);

            SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
            viewPagerTab.setViewPager(viewPager);

            view.findViewById(R.id.back_date).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Indietro
                    mPrecDate.setTime(mCurrentDate.getTime());
                    mCurrentDate.add(Calendar.DAY_OF_MONTH, -1);
                    updateEditText();
                    aggiornaRegistro();
                }
            });

            view.findViewById(R.id.over_date).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Avanti
                    mPrecDate.setTime(mCurrentDate.getTime());
                    mCurrentDate.add(Calendar.DAY_OF_MONTH, 1);
                    updateEditText();
                    aggiornaRegistro();
                }
            });
            //Vado ad una determinata data se è stata passata negli arguments
            if (this.getArguments() != null) {
                String date = this.getArguments().getString(Constant.INTENT_NOTIFICATION_DATA);
                if (date != null && !date.isEmpty()) {
                    String splitDate[] = date.split("-");
                    mCurrentDate.set(Integer.valueOf(splitDate[0]), Integer.valueOf(splitDate[1]), Integer.valueOf(splitDate[2]));
                }
            }

            aggiornaRegistro();

        } catch (InflateException e) {
            Log.e("", e.toString());
            return null;
        }
        return view;
    }


    private void updateEditText() {
        int mYear = mCurrentDate.get(Calendar.YEAR);
        int mMonth = mCurrentDate.get(Calendar.MONTH);
        int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mDateEditText.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
    }

    private void aggiornaRegistro() {
        if (Functions.isOnline(mainActivity)) {
            HashMap<String, String> datiInvio = new HashMap<>();
            datiInvio.put(Constant.USERNAME, Functions.getUsername());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
            Log.e("HttpRegistro", sdf.format(mCurrentDate.getTime()));
            datiInvio.put(Constant.DATA, sdf.format(mCurrentDate.getTime()));
            datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);
            httpRegistro = new HttpRegistro(mainActivity, this, datiInvio, adapter, viewPager,
                    timeRepeated++);
            httpRegistro.execute();
        }
    }

    public void cancel() {
        Log.e("FragmentRegistro", "entrato");
        mCurrentDate.setTime(mPrecDate.getTime());
        updateEditText();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_registro, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem itemMenu) {
        int id = itemMenu.getItemId();
        if (id == R.id.share_registro) {
            int item = viewPager.getCurrentItem();
            String text = "";
            if (item == 0) {    //Comunicazioni
                text = httpRegistro.getMessaggioComunicazioni();
            } else if (item == 1) {//Compiti
                text = httpRegistro.getMessaggioCompiti();
            } else if (item == 2) {//Avvisi
                text = httpRegistro.getMessaggioAvvisi();
            } else if (item == 3) {//Argomenti
                text = httpRegistro.getMessaggioArgomenti();
            }
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

            return true;
        }
        return super.onOptionsItemSelected(itemMenu);
    }

}
