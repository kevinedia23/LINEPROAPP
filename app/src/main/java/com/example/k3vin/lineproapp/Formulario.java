package com.example.k3vin.lineproapp;


import android.renderscript.ScriptGroup;
import android.support.constraint.solver.SolverVariable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Formulario extends AppCompatActivity {

    private TableLayout tabla_funcionO;
    private TableLayout tabla_VAR_RES;
    private Button calcular;
    private Button limpiar;

    protected ArrayList<EditText> listadoEditores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        tabla_funcionO = (TableLayout) findViewById(R.id.tabla_funcionO);
        tabla_VAR_RES = (TableLayout) findViewById(R.id.tabla_VAR_RES);
        calcular = (Button) findViewById(R.id.btn_calcularF);
        limpiar = (Button) findViewById(R.id.btn_limpiarF);

        String respuesta = getIntent().getStringExtra("resultado"); //Obtenemos la respuesta de la Actividad principal
        String[] valores = respuesta.split("-"); //Vector con valores[0] = variables, valores[1] = restricciones


        int variables = Integer.parseInt(valores[0]); //Guardamos el numero de variables
        int restricciones = Integer.parseInt(valores[1]); //Guardamos el numero de restricciones

        ArrayList<String> items = new ArrayList<>(); //ArrayLista para el operando de las restricciones
        items.add("<=");
        items.add(">=");
        items.add("=");

        TableRow trO = new TableRow(this);
        trO.setLayoutParams(tabla_funcionO.getLayoutParams());
        encapsularFunciones(tabla_funcionO, trO, items, variables, 0); //Construimos tabla para funcion objetivo
        encapsularFunciones(tabla_VAR_RES, null, items, variables, restricciones); //Construimos tabla para las restricciones

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valor = listadoEditores.size();

                Toast.makeText(getApplicationContext(), String.valueOf(valor), Toast.LENGTH_SHORT).show();
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cadena = "";
                for(EditText et:listadoEditores){
                    cadena +=" " + et.getText();
                }

                Toast.makeText(getApplicationContext(), cadena , Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void encapsularFunciones(TableLayout tl, TableRow trO, ArrayList<String> items, int variables, int restricciones){

        if(restricciones == 0) {

            for (int var = 0; var < variables + 1; var++) {
                //TextView tvO = new TextView(this);
                //tvO.setPadding(20,20,20,20);
                if (var == variables) {
                    android.support.design.widget.TextInputLayout til = new android.support.design.widget.TextInputLayout(this);
                    EditText et = new EditText(this);
                    et.setTextSize(16);
                    et.setMaxLines(1);
                    et.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    listadoEditores.add(et);
                    til.addView(et);
                    Spinner sp = new Spinner(this);
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
                    sp.setAdapter(adaptador);
                    trO.addView(sp);
                    trO.addView(til);



                    //EditText et = new EditText(this);
                    //et.setText("FIN");
                    //trO.addView(et);
                } else {
                    android.support.design.widget.TextInputLayout til = new android.support.design.widget.TextInputLayout(this);
                    EditText et = new EditText(this);
                    et.setHint("X" + String.valueOf(var + 1));
                    et.setTextSize(16);
                    et.setMaxLines(1);
                    et.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    listadoEditores.add(et);
                    til.addView(et);
                    trO.addView(til);
                }

            }
            tl.addView(trO);
        }else{
            for(int i=0; i<restricciones; i++){
                TableRow tr = new TableRow(this);
                TableRow.LayoutParams parametroRow = new TableRow.LayoutParams(tabla_VAR_RES.getLayoutParams());
                tr.setLayoutParams(parametroRow);
                for(int j=0; j<variables + 1; j++){

                    if(j == variables){
                        android.support.design.widget.TextInputLayout til = new android.support.design.widget.TextInputLayout(this);
                        EditText et = new EditText(this);
                        et.setTextSize(16);
                        et.setMaxLines(1);
                        et.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                        et.setInputType(InputType.TYPE_CLASS_NUMBER);
                        til.addView(et);
                        Spinner sp = new Spinner(this);
                        sp.setMinimumHeight(tr.getHeight());
                        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
                        sp.setAdapter(adaptador);
                        tr.addView(sp);
                        tr.addView(til);
                    } else{
                        android.support.design.widget.TextInputLayout til = new android.support.design.widget.TextInputLayout(this);
                        EditText et = new EditText(this);
                        et.setHint("X" + String.valueOf(j + 1));
                        et.setTextSize(16);
                        et.setMaxLines(1);
                        et.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                        et.setInputType(InputType.TYPE_CLASS_NUMBER);
                        til.addView(et);
                        tr.addView(til);
                    }

                    /*TextView tv = new TextView(this);
                    EditText et = new EditText(this);
                    et.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    tv.setPadding(20,20,20,20);
                    tv.setText("X" + String.valueOf(j));

                    tr.addView(et);
                    tr.addView(tv);*/
                }

                tl.addView(tr);
            }
        }

    }
}
