package com.example.k3vin.lineproapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    private Button btn_generar, btn_limpiar;
    private EditText no_var, no_RES;
//  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
    /*        = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


        //mTextMessage = (TextView) findViewById(R.id.message);
        btn_generar = (Button) findViewById(R.id.btn_generar);
        btn_limpiar = (Button) findViewById(R.id.btn_limpiar);
        no_var = (EditText) findViewById(R.id.no_variables);
        no_RES = (EditText) findViewById(R.id.no_restricciones);


        btn_generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valor = no_var.getText().toString() + "-" + no_RES.getText().toString();
                if(valor.length() == 1 || valor.length() == 2){
                    Toast.makeText(getApplicationContext(), "Ingresa la informacion faltante", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Principal.this, Formulario.class);
                    startActivity(intent.putExtra("resultado", valor));
                }
               // Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_SHORT).show();
            }
        });

        btn_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no_var.setText("");
                no_RES.setText("");
                no_var.requestFocus();
            }
        });
       // BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);
        return true;
    }

    public static class cuadroDialogo extends DialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("LINEPROAPP").setMessage("Proyecto IO1 - Grupo #13")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            return builder.create();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                DialogFragment ad = new cuadroDialogo();
                ad.show(getSupportFragmentManager(), "dialogo");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
