package it.itistulliobuzzi.mybuzzi.itsbuzzi.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpInserimentoVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools.HttpUpdateVoti;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.MainActivity;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.R;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Constant;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Data;
import it.itistulliobuzzi.mybuzzi.itsbuzzi.Utility.Functions;

/**
 * Created by a31167 on 08/06/2016.
 */
public class InserimentoVotiActivity extends BaseActivity {

    private EditText mDateEditText, mNoteEditText, mVotoEditText;
    private Calendar mCurrentDate;
    private Spinner tipo, materia, quadr;
    private HashMap<String, String> materie, tipoVoto, quadrs;


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_inserimento_voti);
    }


    @Override
    protected void load() {

        Intent intent = getIntent();

        mVotoEditText = findViewById(R.id.editTextVoti);
        if (intent.getStringExtra(Constant.MOD_VOTI_VOTO) != null) {
            mVotoEditText.setText(intent.getStringExtra(Constant.MOD_VOTI_VOTO));
        }
        mVotoEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (Double.valueOf(s.toString()) > 10) {
                        mVotoEditText.setText("10");
                        mVotoEditText.setSelection(mVotoEditText.getText().length());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        mCurrentDate = Calendar.getInstance();
        mDateEditText = (EditText) findViewById(R.id.txtDateEnteredVoti);
        updateEditText();
        if (intent.getStringExtra(Constant.MOD_VOTI_DATA) != null) {
            String[] data = intent.getStringExtra(Constant.MOD_VOTI_DATA).split("-");
            int mYear = Integer.parseInt(data[0]);
            int mMonth = Integer.parseInt(data[1]);
            int mDay = Integer.parseInt(data[2]);
            mDateEditText.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
            mCurrentDate.set(mYear, mMonth, mDay);
        }
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear = mCurrentDate.get(Calendar.YEAR);
                int mMonth = mCurrentDate.get(Calendar.MONTH);
                int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(InserimentoVotiActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        mDateEditText.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
                        mCurrentDate.set(selectedYear, selectedMonth, selectedDay);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();
            }
        });

        materia = (Spinner) findViewById(R.id.materia_voti_spn);
        materie = Data.getMaterie();
        String selectedMateria = "";
        if (intent.getStringExtra(Constant.MOD_VOTI_MATERIA) != null) {
            selectedMateria = intent.getStringExtra(Constant.MOD_VOTI_MATERIA);
        }
        int iSelectedMateria = -1;
        String[] itemsMaterie = new String[materie.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : materie.entrySet()) {
            itemsMaterie[i] = entry.getKey();
            if (itemsMaterie[i].equals(selectedMateria)) {
                iSelectedMateria = i;
            }
            i++;
        }
        if (iSelectedMateria != -1) {
            String appo = itemsMaterie[0];
            itemsMaterie[0] = itemsMaterie[iSelectedMateria];
            itemsMaterie[iSelectedMateria] = appo;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_spn, itemsMaterie);
        materia.setAdapter(adapter);

        tipo = (Spinner) findViewById(R.id.tipo_voti_spn);
        tipoVoto = Data.getTipoVoti();
        String selectedTipoVoto = "";
        if (intent.getStringExtra(Constant.MOD_VOTI_TIPO) != null) {
            selectedTipoVoto = intent.getStringExtra(Constant.MOD_VOTI_TIPO);
        }
        int iSelectedTipoVoto = -1;
        String[] itemsTipoVoto = new String[tipoVoto.size()];
        i = 0;
        for (Map.Entry<String, String> entry : tipoVoto.entrySet()) {
            itemsTipoVoto[i] = entry.getKey();
            if (itemsTipoVoto[i].equals(selectedTipoVoto)) {
                iSelectedTipoVoto = i;
            }
            i++;
        }
        if (iSelectedTipoVoto != -1) {
            String appo = itemsTipoVoto[0];
            itemsTipoVoto[0] = itemsTipoVoto[iSelectedTipoVoto];
            itemsTipoVoto[iSelectedTipoVoto] = appo;
        }
        adapter = new ArrayAdapter<String>(this, R.layout.row_spn, itemsTipoVoto);
        tipo.setAdapter(adapter);

        quadr = (Spinner) findViewById(R.id.quadr_voti_spn);
        quadrs = Data.getQuadr();
        String selectedQuadr = "";
        if (intent.getStringExtra(Constant.MOD_QUADR_TIPO) != null) {
            selectedQuadr = intent.getStringExtra(Constant.MOD_QUADR_TIPO);
        }
        int iSelectedQuadr = -1;
        String[] itemsQuadr = new String[quadrs.size()];
        i = 0;
        for (Map.Entry<String, String> entry : quadrs.entrySet()) {
            itemsQuadr[i] = entry.getKey();
            String cntl = entry.getValue();
            Log.e("Quad Ciclo", itemsQuadr[i]);
            if (cntl.equals(selectedQuadr)) {
                iSelectedQuadr = i;
            }
            i++;
        }
        if (iSelectedQuadr != -1) {
            String appo = itemsQuadr[0];
            itemsQuadr[0] = itemsQuadr[iSelectedQuadr];
            itemsQuadr[iSelectedQuadr] = appo;
        }
        adapter = new ArrayAdapter<>(this, R.layout.row_spn, itemsQuadr);
        quadr.setAdapter(adapter);


        mNoteEditText = (EditText) findViewById(R.id.txtNoteVoti);
        if (intent.getStringExtra(Constant.MOD_VOTI_NOTE) != null) {
            mNoteEditText.setText(intent.getStringExtra(Constant.MOD_VOTI_NOTE));
        }
    }

    private void updateEditText() {
        int mYear = mCurrentDate.get(Calendar.YEAR);
        int mMonth = mCurrentDate.get(Calendar.MONTH);
        int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mDateEditText.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ins_voti, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String voto = mVotoEditText.getText().toString();
        if (id == R.id.addVoto && voto.matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            HashMap<String, String> datiInvio = new HashMap<>();
            datiInvio.put("matricola", Functions.getMatricola());
            datiInvio.put("idtipo", tipoVoto.get(tipo.getSelectedItem()));
            datiInvio.put("idmateria", materie.get(materia.getSelectedItem()));
            datiInvio.put("voto", mVotoEditText.getText().toString());
            String date[] = mDateEditText.getText().toString().split("-");
            datiInvio.put("datavoto", date[2] + '-' + ((date[1].length() == 1) ? '0' +date[1] : date[1]) + '-' + ((date[0].length() == 1) ? '0' +date[0] : date[0]));
            datiInvio.put("note", mNoteEditText.getText().toString());
            datiInvio.put("idquadrimestre", quadrs.get(quadr.getSelectedItem()));
            datiInvio.put(Constant.PASSWORD_PAGINA, Constant.PASSWPRD_PAGINA_VALORE);

            if (getIntent().getBooleanExtra(Constant.MOD_VOTI, false)) {
                datiInvio.put("idvoti", String.valueOf(getIntent().getIntExtra(Constant.MOD_VOTI_ID, 0)));
                HttpUpdateVoti httpUpdateVoti = new HttpUpdateVoti(this, datiInvio);
                httpUpdateVoti.execute();
            } else {
                HttpInserimentoVoti httpInserimentoVoti = new HttpInserimentoVoti(this, datiInvio);
                httpInserimentoVoti.execute();
            }
            return true;
        }else{
            Functions.showAlertDialog(this, "Attenzione!", "Voto non valido");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(Constant.EXIT_INS_VOTI_ACTIVITY, true);
        Functions.switchToActivity(i, this);
    }
}
