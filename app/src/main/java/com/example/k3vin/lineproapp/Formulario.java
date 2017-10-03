package com.example.k3vin.lineproapp;


import android.content.Intent;
import android.renderscript.Float2;
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
    protected ArrayList<Spinner> listaSpinners = new ArrayList<>();
    protected ArrayList<EditText> funcionObjetivo = new ArrayList<>();
    protected String[][] tabla;

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


        final int variables = Integer.parseInt(valores[0]); //Guardamos el numero de variables
        final int restricciones = Integer.parseInt(valores[1]); //Guardamos el numero de restricciones

        ArrayList<String> items = new ArrayList<>(); //ArrayLista para el operando de las restricciones
        items.add("<=");
        items.add(">=");
        items.add("=");

        TableRow trO = new TableRow(this);
        trO.setLayoutParams(tabla_funcionO.getLayoutParams());
        encapsularFunciones(tabla_VAR_RES, null, items, variables, restricciones); //Construimos tabla para las restricciones
        encapsularFunciones(tabla_funcionO, trO, items, variables, 0); //Construimos tabla para funcion objetivo

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mayorQ = 0, menorQ = 0, igual = 0;
                Intent intent = new Intent(Formulario.this, solucion.class);
                intent.putExtra("variables", variables);//Adjuntamos el # de variables
                intent.putExtra("restricciones", restricciones);//Adjuntamos el # de restricciones

                for(Spinner spg:listaSpinners){//dependiendo de los operadores <= >= = se decidira si usar simplex u otro metodo
                    if(String.valueOf(spg.getSelectedItem()).equals("<=")){
                        menorQ++;
                    }else if(String.valueOf(spg.getSelectedItem()).equals(">=")){
                        mayorQ++;
                    }else{
                        igual++;
                    }
                }
                if(menorQ == restricciones){//Asignamos que se va a utilizar el metodo simplex
                    String valor = "simplex";
                    intent.putExtra("metodo" , valor);//adjuntamos el metodo simplex
                }else{
                    //Preguntar que metodo realizar
                }
                intent.putStringArrayListExtra("funcion_objetivo" , casteoFO());//adjuntamos funcion Objetivo
                startActivity(intent.putStringArrayListExtra("lista", casteo()));//adjuntamos lista de valores
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //crearTabla(listadoEditores, variables, restricciones);

            }
        });

    }

    private void crearTabla(ArrayList<EditText> listado, int variables, int restricciones){
        int contador = 0;
        int iteracion = listado.size() / (variables + 1);


        for(int it = 0; it<iteracion; it++){
            for(int i=0; i<variables + 1; i++){
                tabla[it][i] = listado.get(contador).getText().toString();
                contador++;
            }
        }
        String cadena = String.valueOf(tabla[0][0]) + String.valueOf(tabla[0][1]) + String.valueOf(tabla[0][2]) + String.valueOf(tabla[0][3]) + String.valueOf(tabla[0][4]);
        String cadena2 = String.valueOf(tabla[1][0]) + String.valueOf(tabla[1][1]);

        Toast.makeText(getApplicationContext(), cadena + "//" + cadena2, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> casteo(){
        ArrayList<String> lista = new ArrayList<>();

        for(int i=0; i<listadoEditores.size(); i++){
            lista.add(listadoEditores.get(i).getText().toString());
        }

        return lista;
    }

    private ArrayList<String> casteoFO(){
        ArrayList<String> lista = new ArrayList<>();

        for(int i=0; i<funcionObjetivo.size(); i++){
            lista.add(funcionObjetivo.get(i).getText().toString());
        }

        return lista;
    }



    private void encapsularFunciones(TableLayout tl, TableRow trO, ArrayList<String> items, int variables, int restricciones){

        if(restricciones == 0) {

            for (int var = 0; var < variables; var++) {
                    android.support.design.widget.TextInputLayout til = new android.support.design.widget.TextInputLayout(this);
                    EditText et = new EditText(this);
                    et.setHint("X" + String.valueOf(var + 1));
                    et.setTextSize(16);
                    et.setMaxLines(1);
                    et.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
                    et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    listadoEditores.add(et);
                    funcionObjetivo.add(et);
                    til.addView(et);
                    trO.addView(til);

            }
            tl.addView(trO);
        }else{
            tabla = new String[restricciones + 1][variables + variables + 1];
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
                        listadoEditores.add(et);
                        til.addView(et);
                        Spinner sp = new Spinner(this);
                        sp.setMinimumHeight(tr.getHeight());
                        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
                        sp.setAdapter(adaptador);
                        listaSpinners.add(sp);
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
                        listadoEditores.add(et);
                        til.addView(et);
                        tr.addView(til);
                    }
                }

                tl.addView(tr);
            }
        }

    }
}
