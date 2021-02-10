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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Activity_enviar_solicitud_prestamo extends AppCompatActivity {

    private SharedPreferences preferences;
    private AsyncHttpClient comu_clien;

    private String usuario, cg_comu, val_prestamo, plaz_prestamo;
    private String res, nnn;


    private String fechacrea, fechacadu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_solicitud_prestamo);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
       cg_comu = preferences.getString("codigo_comu_solicitud", null);
        usuario =preferences.getString("cedula_usuario", null);
        val_prestamo=preferences.getString("valor_prestar", null);
        plaz_prestamo=preferences.getString("diferido_prestamo", null);

        Manejo_fechas fc = new Manejo_fechas();
        fechacrea=fc.fechaYhora_actual();
        fechacadu=fc.caducidad();
        comu_clien = new AsyncHttpClient();
        //enviar_solicitudes();
        //datos_us();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(Activity_enviar_solicitud_prestamo.this, inicio.class);
                startActivity(intent);


            }
        },25000);

    }

    private void datos_us(){


        String cog_comu = cg_comu.replace(" ", "%20");
        String url_link = getString(R.string.link_buscar_usuarios);
        String url = url_link+"?codigo_comu="+cog_comu;
        //Toast.makeText(cargar_2.this, url , Toast.LENGTH_SHORT).show();
        comu_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(Activity_enviar_solicitud_prestamo.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {

                            JSONObject jsonObj = new JSONObject(respuesta);
                            res=jsonObj.getString("dato");
                            //Toast.makeText(cargar_2.this, "entro:"+res , Toast.LENGTH_SHORT).show();
                            String[] parts = res.split("/");
                            if (parts[1] == "dato_null"){
                                Toast.makeText(Activity_enviar_solicitud_prestamo.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                            }else {
                                for (int i = 1; i < parts.length; i++) {
                                    nnn = parts[i];
                                    String[] parts2 = nnn.split("%%");
                                    String usuario_soli=parts2[1];
                                    String ci_soli=parts2[0];
                                    enviar_solicitudes(ci_soli);
                                    Toast.makeText(Activity_enviar_solicitud_prestamo.this, "Solicitud enviada a:" + usuario_soli, Toast.LENGTH_SHORT).show();

                                }
                                Toast.makeText(getApplicationContext(), "las solicitudes fueron enviadas con exito ...!!!", Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Activity_enviar_solicitud_prestamo.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void enviar_solicitudes(String dato){

        String ccc= "%22"+cg_comu+"%22";
        String cog_comu = ccc.replace(" ", "%20");
        String ci_emisor = usuario.replace(" ", "%20");
        String cg_cm = usuario.replace(" ", "%20");
        String ci_receptor = dato.replace(" ", "%20");
        String fecha_crea = fechacrea.replace(" ", "%20");
        String fecha_cadu = fechacadu.replace(" ", "%20");
        String v_pres = val_prestamo.replace(" ", "%20");
        String p_pres = plaz_prestamo.replace(" ", "%20");

        String url_link2 = getString(R.string.link_generar_solicitud_prestamos);
        String url = url_link2+"?ci_emisor="+ci_emisor+"&cg_cm="+cg_cm+"&ci_receptor="+ci_receptor+"&fecha_crea="+fecha_crea+"&fecha_cadu="+fecha_cadu+"&p_pres="+p_pres+"&v_pres="+v_pres;

        comu_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {

                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(Activity_enviar_solicitud_prestamo.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(Activity_enviar_solicitud_prestamo.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();
            }
        });
    }


}