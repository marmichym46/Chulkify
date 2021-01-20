package com.example.chulkify.envio_solicitud_comu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chulkify.Manejo_fechas;
import com.example.chulkify.R;
import com.example.chulkify.inicio;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class cargar_2 extends AppCompatActivity {

    private SharedPreferences preferences;
    private AsyncHttpClient comu_clien;
    private String usuario;
    private String res, nnn;


    private String fechacrea, fechacadu;
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa, usuario2;
    private int minutos_aux;
    private int m_cadu=0;
    private String mnt="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_2);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("codigo_comu_solicitud", null);
        usuario2 =preferences.getString("cedula_usuario", null);
        comu_clien = new AsyncHttpClient();
        //enviar_solicitudes();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Manejo_fechas fc = new Manejo_fechas();
                fechacrea=fc.fechaYhora_actual();
                fechacadu=fc.caducidad();

                datos_us();

                cargar_2.this.finish();
            }
        },15000);

    }




    private void datos_us(){
        String ccc= "%22"+usuario+"%22";
        String cog_comu = ccc.replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/buscar_usu_per_comu.php?codigo_comu="+cog_comu;



        comu_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {




                if (statusCode == 200) {

                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(cargar_2.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jsonObj = new JSONObject(respuesta);
                            res=jsonObj.getString("dato");

                            String[] parts = res.split("/");
                            if (parts[1] == "dato_null"){
                                Toast.makeText(cargar_2.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();

                            }else {

                                for (int i = 1; i < parts.length; i++) {
                                    nnn = parts[i];
                                    Toast.makeText(cargar_2.this, "datos2=" + nnn, Toast.LENGTH_SHORT).show();
                                    enviar_solicitudes();
                                }
                                Intent intent= new Intent(cargar_2.this, inicio.class);
                                startActivity(intent);
                            }





                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(cargar_2.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();

            }


        });
    }



    private void enviar_solicitudes(){


        final String fecha_crea_notif = fechacrea.trim();
        final String fecha_cadu_notif = fechacadu.trim();
        final String ci_soli_env_notif = usuario2.trim();
        //Toast.makeText(cargar_2.this, fecha_crea_notif+" - "+fecha_cadu_notif, Toast.LENGTH_SHORT).show();
        final String codigo_comu_notif = usuario.trim();
        //Toast.makeText(cargar_2.this, codigo_comu_notif, Toast.LENGTH_SHORT).show();
        final String ci_us_notif = nnn.trim();
        //Toast.makeText(cargar_2.this, ci_us_notif, Toast.LENGTH_SHORT).show();


        final ProgressDialog progressDialog = new ProgressDialog(this);



            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.marlonmym.tk/chulki/add_notificacion.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("lasolicitud fue enviada con exito ...!!!")){

                        //Intent intent= new Intent(cargar_2.this, cargar_1.class);
                        //startActivity(intent);
                        Toast.makeText(getApplicationContext(), "lasolicitud fue enviada con exito ...!!!", Toast.LENGTH_SHORT).show();
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
                    parametros.put("ci_us_notif",ci_us_notif);
                    parametros.put("codigo_comu_notif",codigo_comu_notif);
                    parametros.put("fecha_crea_notif",fecha_crea_notif);
                    parametros.put("fecha_cadu_notif",fecha_cadu_notif);
                    parametros.put("ci_soli_env_notif", ci_soli_env_notif);
                    return parametros;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(cargar_2.this);
            requestQueue.add(stringRequest);





    }



}