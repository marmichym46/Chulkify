package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class nueva_comunidad extends AppCompatActivity {

    private SharedPreferences preferences;
    private EditText edt_nombre, edt_codigo, edt_ciudad;
    private String fecha;


    private String fechacrea, fechacadu;
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa, usuario2;
    private int minutos_aux;



    private Button btn_guardar;
    private AsyncHttpClient cliente;
    private Date fecha_dt;
    private String ci_usuario_us;
    private TextView tv1;
    private String cog,cdg1;


    private String codigo_guardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_comunidad);

        tv1=findViewById(R.id.txt_ci);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        ci_usuario_us = preferences.getString("cedula_usuario", null);
        if (ci_usuario_us != null){
           tv1.setText(ci_usuario_us);
        }

        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();
        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        String diaS= String.valueOf(dia);
        String mesS = String.valueOf(mes);
        String anioS = String.valueOf(anio);

        SimpleDateFormat h= new SimpleDateFormat("kk");
        horaS=h.format(date);
        hora=Integer.valueOf(horaS);
        SimpleDateFormat m= new SimpleDateFormat("mm");
        minutosS=m.format(date);
        SimpleDateFormat mm= new SimpleDateFormat("m");
        minutos=Integer.parseInt(mm.format(date));
        minutosa=String.valueOf(minutos);
        SimpleDateFormat s= new SimpleDateFormat("ss");
        segundosS=s.format(date);
        segundos=Integer.valueOf(segundosS);

        fechacrea = diaS+"/"+mesS+"/"+anioS+"/"+horaS+"/"+minutosS+"/"+segundosS;


        edt_nombre=(EditText) findViewById(R.id.txt_nombre_comu);
        edt_codigo=(EditText) findViewById(R.id.txt_codigo_comu);
        edt_ciudad=(EditText) findViewById(R.id.txt_ciudad_comu);

        cliente=new AsyncHttpClient();



        btn_guardar=(Button) findViewById(R.id.btn_registrar);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarregistro();
            }
        });
    }
    public void ejecutarregistro() {

        final String nombre_comu = edt_nombre.getText().toString().trim();
        final String codigo_comu = edt_codigo.getText().toString().trim();
        final String ciudad_comu = edt_ciudad.getText().toString().trim();
        final String fechaI_comu = fechacrea.trim();
        final String cedula_comu = ci_usuario_us.trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        if (nombre_comu.isEmpty()) {
            edt_nombre.setError("complete los campos");
        } else if (codigo_comu.isEmpty()) {
            edt_codigo.setError("complete los campos");
        } else if (ciudad_comu.isEmpty()) {
            edt_ciudad.setError("complete los campos");
        } else {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.marlonmym.tk/chulki/new_comunidad/add_comunidad.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("la comunidad se creo con exito")){

                        Intent intent= new Intent(nueva_comunidad.this, cargar_1.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "la comunidad se creo con exito ...!!!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams()throws AuthFailureError {


                    Map<String, String> parametros = new HashMap<String, String>();

                    String nomb_comu =nombre_comu.toString().replace(" ", "%20");
                    parametros.put("nombre_comu",nomb_comu);
                    parametros.put("codigo_comu",codigo_comu);
                    parametros.put("ciudad_comu",ciudad_comu);
                    parametros.put("admin_comu",cedula_comu);
                    parametros.put("fecha_inicio_comu",fechaI_comu);
                    return parametros;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(nueva_comunidad.this);
            requestQueue.add(stringRequest);




        }
    }

}