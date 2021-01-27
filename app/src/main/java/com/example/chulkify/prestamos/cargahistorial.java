package com.example.chulkify.prestamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.chulkify.Manejo_fechas;
import com.example.chulkify.R;
import com.example.chulkify.envio_solicitud_comu.cargar_2;
import com.example.chulkify.inicio;
import com.example.chulkify.login.Logear_usuario;
import com.example.chulkify.menus_usuarios.menu_comunidad;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class cargahistorial extends AppCompatActivity {
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
        setContentView(R.layout.activity_cargahistorial);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("nombre_comu", null);
        usuario2 =preferences.getString("cedula_usuario", null);
        Manejo_fechas fc = new Manejo_fechas();
        fechacrea=fc.fechaYhora_actual();
        fechacadu=fc.caducidad();
        comu_clien = new AsyncHttpClient();

        //enviar_solicitudes();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datos_us();
                cargahistorial.this.finish();
            }
        },25000);

    }
    private void datos_us(){
        String cedula = usuario2.replace(" ", "%20");
        String cog_comu = usuario.replace(" ", "%20");
        String url_link = getString(R.string.link_consultar_prestamos);
        String url = url_link+"?ci="+cog_comu+"&ci_us"+cedula;
        comu_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(cargahistorial.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jsonObj = new JSONObject(respuesta);
                            res=jsonObj.getString("dato");
                            if(res.equals("NO_HAY")){
                                SharedPreferences.Editor editor= preferences.edit();
                                editor.putString("estado_prestamos","No_prestamos");
                                editor.apply();
                                startActivity(new Intent(cargahistorial.this, Activity_menu_prestamos.class));
                            }
                            else if((res.equals("PAGADO"))||(res.equals("LIQUIDADO"))||(res.equals("PAGADO_R"))) {

                                enviar_solicitudes();


                            }
                            else if((res.equals("PAGANDO"))||(res.equals("MORA"))||(res.equals("REFINANCIADO"))){
                                SharedPreferences.Editor editor= preferences.edit();
                                editor.putString("estado_prestamos","si_prestamos_pagando");
                                editor.apply();
                                startActivity(new Intent(cargahistorial.this, Activity_menu_prestamos.class));
                            }
                            else {
                                Toast.makeText(cargahistorial.this, "Error al cargar datos...!!"+responseBody, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(cargahistorial.this, menu_comunidad.class));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(cargahistorial.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enviar_solicitudes(){

        String ci_emisor = usuario2.replace(" ", "%20");


        String url_link2 = getString(R.string.link_estado_solicitud_prestamos);
        String url = url_link2+"?ci_us="+ci_emisor;

        comu_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(cargahistorial.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jsonObj = new JSONObject(respuesta);
                            String respt=jsonObj.getString("dato");

                            SharedPreferences.Editor editor= preferences.edit();
                            editor.putString("estado_prestamos","si_prestamos_pagados");
                            editor.putString("estado_solicitud_prestamos", respt);
                            editor.apply();
                            startActivity(new Intent(cargahistorial.this, Activity_menu_prestamos.class));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(cargahistorial.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();
            }
        });
    }
}