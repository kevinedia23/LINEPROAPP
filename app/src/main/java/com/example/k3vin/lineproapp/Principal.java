package com.example.k3vin.lineproapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
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
                Intent intent = new Intent(Principal.this , Formulario.class);
                startActivity(intent.putExtra("resultado", valor));
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

}
