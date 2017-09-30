package com.example.k3vin.lineproapp;


import android.renderscript.ScriptGroup;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Formulario extends AppCompatActivity {

    private TableLayout tabla_funcionO;
    private TableLayout tabla_VAR_RES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        tabla_funcionO = (TableLayout) findViewById(R.id.tabla_funcionO);
        tabla_VAR_RES = (TableLayout) findViewById(R.id.tabla_VAR_RES);

        String respuesta = getIntent().getStringExtra("resultado");
        String[] valores = respuesta.split("-");

        int variables = Integer.parseInt(valores[0]);
        int restricciones = Integer.parseInt(valores[1]);

        ArrayList<String> items = new ArrayList<>();
        items.add("<=");
        items.add(">=");
        items.add("=");

        TableRow trO = new TableRow(this);
        trO.setLayoutParams(tabla_funcionO.getLayoutParams());
        for(int var=0; var<variables + 1; var++){
            //TextView tvO = new TextView(this);
            //tvO.setPadding(20,20,20,20);
            if(var == variables){
                android.support.design.widget.TextInputLayout til = new android.support.design.widget.TextInputLayout(this);
                EditText et = new EditText(this);
                et.setTextSize(16);
                et.setMaxLines(1);
                et.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                til.addView(et);
                Spinner sp = new Spinner(this);
                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
                sp.setAdapter(adaptador);
                trO.addView(sp);
                trO.addView(til);
                //EditText et = new EditText(this);
                //et.setText("FIN");
                //trO.addView(et);
            }

            else {
                android.support.design.widget.TextInputLayout til = new android.support.design.widget.TextInputLayout(this);
                EditText et = new EditText(this);
                et.setHint("X" + String.valueOf(var + 1));
                et.setTextSize(16);
                et.setMaxLines(1);
                et.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                til.addView(et);
                //tvO.setText("X" + String.valueOf(var + 1));
                trO.addView(til);
            }
        }
        tabla_funcionO.addView(trO);


        for(int i=0; i<restricciones; i++){
            TableRow tr = new TableRow(this);
            TableRow.LayoutParams parametroRow = new TableRow.LayoutParams(tabla_VAR_RES.getLayoutParams());
            tr.setLayoutParams(parametroRow);
            for(int j=0; j<variables + 1; j++){

                TextView tv = new TextView(this);
                EditText et = new EditText(this);
                et.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tv.setPadding(20,20,20,20);
                tv.setText("X" + String.valueOf(j));

                tr.addView(et);
                tr.addView(tv);
            }

            tabla_VAR_RES.addView(tr);
        }

    }
}
