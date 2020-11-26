package com.example.chulkify;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private TextView tv1;
    private ImageButton new_comu, uni_comu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=findViewById(R.id.txt_mostrar);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        String usuario_us = preferences.getString("nombre_usuario", null);

        if (usuario_us != null){
            tv1.setText("Bienvenido "+usuario_us);
        }

        new_comu = (ImageButton) findViewById(R.id.btn_new_comu);
        uni_comu = (ImageButton) findViewById(R.id.btn_uni_comu);
        new_comu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, nueva_comunidad.class));

            }
        });
        uni_comu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, unir_comunidad.class));

            }
        });



    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_of, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_iten_c_sesion){
            preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
            preferences.edit().clear().apply();
            startActivity(new Intent(MainActivity.this, Login.class));
            //Intent intent= new Intent(menu_comunidad.this, Login.class);

        }
        return super.onOptionsItemSelected(item);
    }
}