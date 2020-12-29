package com.example.chulkify;

import android.content.Context;
import android.widget.Toast;

import static android.widget.Toast.*;

public class Consultas {

    private Context context;

    public void aceptarsoli(int identificador){
        String ident="id: "+identificador;


        Toast.makeText(context, "ident", Toast.LENGTH_SHORT).show();


    }
}
