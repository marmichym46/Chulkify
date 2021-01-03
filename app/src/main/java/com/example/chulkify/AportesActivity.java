package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AportesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aportes);


        //Boton notificaciones
        ImageButton btn_aportar = (ImageButton) findViewById(R.id.btn_aportar);
        btn_aportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AportesActivity.this, Aportar.class));
                //Toast.makeText(menu_inicio.this, "url", Toast.LENGTH_SHORT).show();
            }
        });
    }
}