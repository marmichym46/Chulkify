package com.example.chulkify.transacciones_pg.retiros;

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
import com.example.chulkify.R;
import com.example.chulkify.transacciones_pg.transaccionesActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class cargar_retiro extends AppCompatActivity {

    private SharedPreferences preferences;
    private AsyncHttpClient comu_clien;
    private String usuario;
    private AsyncHttpClient buscar_url;
    private AsyncHttpClient aportar_conm;
    public String ci_us_1,val_aportar,fecha1,hora1,gp_1,cg_gp_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_retiro);


        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("comunidad", null);
        ci_us_1= preferences.getString("cedula_usuario", null);
        val_aportar= preferences.getString("val_aportar", null);
        fecha1= preferences.getString("fecha_t_ap", null);
        hora1= preferences.getString("hora_t_ap", null);
        gp_1= preferences.getString("nombre_comu", null);
        cg_gp_1= preferences.getString("codigo_comu", null);
        //Toast.makeText(cargar3.this, usuario, Toast.LENGTH_SHORT).show();
        aportar();


        comu_clien = new AsyncHttpClient();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(cargar_retiro.this, transaccionesActivity.class);
                startActivity(intent);
                //cargar3.this.finish();
            }
        },07000);
    }

    private void cargar_datos(){

        consultar_datos();

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("val_aportar", "null");
        editor.putString("fecha_t_ap", "null");
        editor.putString("hora_t_ap", "null");
        editor.apply();

    }

    public void aportar() {
        aportar_conm = new AsyncHttpClient();

        final String ci_us = ci_us_1.trim();
        final String aporte = val_aportar.trim();
        final String fecha = fecha1.toString().trim();
        final String hora = hora1.toString().trim();
        final String gp = gp_1.replace(" ", "%20").trim();
        final String cg_gp = cg_gp_1.trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.marlonmym.tk/chulki/retirar/retirar_us.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("transaccion_correcta")) {
                    Toast.makeText(getApplicationContext(), "la transaccion se realizo con exito ...!!!", Toast.LENGTH_SHORT).show();
                    cargar_datos();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> parametros = new HashMap<String, String>();


                parametros.put("ci_us", ci_us);
                parametros.put("aporte", aporte);
                parametros.put("fecha", fecha);
                parametros.put("hora", hora);
                parametros.put("gp", gp);
                parametros.put("cg_gp", cg_gp);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(cargar_retiro.this);
        requestQueue.add(stringRequest);



    }

    public void consultar_datos(){
        //String ap="null";
        aportar_conm = new AsyncHttpClient();


        String ci_us = ci_us_1.toString().replace(" ", "%20");
        String fecha22 = fecha1.toString().replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/consultas/consultas_ap_rt_fd_lap.php?ci_us="+ci_us+"&fecha="+fecha22;

        aportar_conm.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(cargar_retiro.this, "Error ...!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {


                            JSONObject jsonObj = new JSONObject(respuesta);
                            String ap = jsonObj.getString("dato");
                            String[] parts=ap.split("%%");

                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("fondos_us", parts[0]);
                            editor.putString("aportes_hoy", parts[1]);
                            editor.putString("retiro_hoy", parts[2]);
                            editor.putString("linea_ap", parts[3]);
                            editor.apply();



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(cargar_retiro.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


            }});

    }


}
