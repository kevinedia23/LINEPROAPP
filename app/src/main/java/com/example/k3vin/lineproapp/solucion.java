package com.example.k3vin.lineproapp;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import pl_metodos.metodo_simplex.simplex;

public class solucion extends AppCompatActivity {

    private TextView tv;
    private ArrayList<String> listado;
    private ArrayList<String> funcionObjetivo;
    private int variables;
    private int restricciones;
    private String metodo;
    private float[][] tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solucion);
        tv = (TextView) findViewById(R.id.tv_resultado);


        listado = new ArrayList<>();
        funcionObjetivo = new ArrayList<>();
        variables = getIntent().getIntExtra("variables", 0);
        restricciones = getIntent().getIntExtra("restricciones", 0);
        listado = getIntent().getStringArrayListExtra("lista");
        funcionObjetivo = getIntent().getStringArrayListExtra("funcion_objetivo");
        metodo = getIntent().getStringExtra("metodo");
        tabla = new float[restricciones + 1][variables + variables + 1];

        crearTabla();//Se estandariza la tabla para resolver simplex con maximizacion

        switch (metodo){
            case "simplex":
                exeSimplex();
                break;
        }

        ScrollView sv = new ScrollView(this);
        TableLayout tl = dibujarTabla();
        HorizontalScrollView hsv = new HorizontalScrollView(this);

        hsv.addView(tl);
        sv.addView(hsv);
        setContentView(sv);

        //dibujarTabla();
    }

    private void crearTabla(){
        int contador = 0;
        int iteracion = (listado.size() - variables) / (variables + 1);

        for(int i=0; i<iteracion; i++){
            for(int j=0; j<variables + 1; j++){
                tabla[i][j] = Float.valueOf(listado.get(contador));
                contador++;
            }
        }

        for(int i=0; i<funcionObjetivo.size(); i++){
            tabla[restricciones][i] = -Float.valueOf(funcionObjetivo.get(i));
        }

        estandarizar(tabla , iteracion);
        Toast.makeText(getApplicationContext(), funcionObjetivo.get(0), Toast.LENGTH_SHORT).show();
    }

    private TableLayout dibujarTabla(){
        TableLayout.LayoutParams paramsTl = new TableLayout.LayoutParams();
        TableLayout tl = new TableLayout(this);
        tl.setGravity(Gravity.CENTER);
        TableRow.LayoutParams paramsTr = new TableRow.LayoutParams();
        paramsTr.setMargins(1, 1, 1, 1);
        paramsTr.weight = 1;
        for(int i=0; i<restricciones + 1; i++){;
            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(Color.BLACK);
            for(int j=0; j<variables + variables + 1; j++){
                TextView tv = new TextView(this);
                tv.setTextSize(25);
                tv.setBackgroundColor(Color.WHITE);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(10, 10, 10, 10);
                tv.setText(String.valueOf(tabla[i][j]));
                tr.addView(tv , paramsTr);
            }

            tl.addView(tr , paramsTl);
        }

        //Toast.makeText(getApplicationContext(), metodo, Toast.LENGTH_SHORT).show();
        Snackbar.make(findViewById(android.R.id.content), "Metodo usado: " + metodo, Snackbar.LENGTH_LONG).show();
        return tl;
    }

    private void estandarizar(float[][] tabla, int iteracion){
        int contador = 0;

        for(int i=0; i<iteracion; i++){
            mover(tabla, i, contador);
            contador++;
        }
    }

    private void mover(float[][] tabla, int i, int contador){
        float s = tabla[i][variables];
        tabla[i][variables] = 0;
        valor(tabla, i, contador);
        tabla[i][variables + variables] = s;
    }

    private void valor(float[][] tabla, int i, int contador){
        if(variables + contador < variables + variables + 1)
            tabla[i][variables + contador] = 1;
    }

    private void exeSimplex(){//Metodo que ejecuta el metodo simplex
        boolean terminado = false;
        simplex sp = new simplex(restricciones, variables + variables);

        sp.llenarTabla(tabla);

        while(!terminado){
            simplex.ERROR err = sp.procesar();

            if(err == simplex.ERROR.OPTIMIZADO){
                Toast.makeText(this, "OPTIMIZADO", Toast.LENGTH_SHORT).show();
                terminado = true;
            }else if(err == simplex.ERROR.SIN_SOLUCION){
                Toast.makeText(this, "SIN SOLUCION", Toast.LENGTH_SHORT).show();
                terminado = true;
            }

        }

        tabla = sp.getTabla();

    }


}
