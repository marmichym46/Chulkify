package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AportesActivity extends AppCompatActivity {


    private SharedPreferences preferences;
    private AsyncHttpClient aportar_conm;

    //Variables para capturar preferencias
    private String ci_us_1, gp_1, cg_gp_1, taza,hoy_tran, retiros_hoy, maximo,nombre_us_usu,fecha_hoy,fondos_us, linea_aportes;
    private TextView tv_comunidad, tv_fecha, tv_usuario_us, tv_fondos, tv_ap_hoy, tv_mensaje, tv_rt_hoy;
    private String msj_prestamo;
    private Button btn_actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aportes);

        //capturar preferencias
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        ci_us_1=preferences.getString("cedula_usuario", null);
        nombre_us_usu=preferences.getString("nombre_usuario", null);
        gp_1=preferences.getString("nombre_comu", null);
        cg_gp_1=preferences.getString("codigo_comu", null);
        taza=preferences.getString("taza", null);
        maximo=preferences.getString("maximo", null);
        fecha_hoy=preferences.getString("fecha_actual", null);
        hoy_tran=preferences.getString("aportes_hoy", null);
        retiros_hoy=preferences.getString("retiro_hoy", null);
        fondos_us=preferences.getString("fondos_us", null);
        linea_aportes=preferences.getString("linea_ap", null);
        String ln_ap=linea_aportes;
        String[] parts = ln_ap.split("/");
        if (parts[0]=="0"){
            msj_prestamo="Aun no ha hecho ningun aporte, recuerde necesita 6 meses activo para acceder a prestamos";
        }else if(parts[0]=="2"||parts[0]=="3"||parts[0]=="1"||parts[0]=="4"){
            msj_prestamo="Tiene"+parts[0]+" meses de aportes, necesita 6 meses para acceder a prestamos, ultimo aporte fue en: "+parts[1]+"/"+parts[2];
        } else if(parts[0]=="5"){
            msj_prestamo="Tiene 5 meses de aportes, necesita 1 mas para acceder a prestamos, ultimo aporte fue en: "+parts[1]+"/"+parts[2];
        } else {
            msj_prestamo="Tiene"+parts[0]+" meses de aportes, puede acceder a prestamos, ultimo aporte fue en: "+parts[1]+"/"+parts[2];
        }

        tv_comunidad=findViewById(R.id.tv_comunidad_m);
        tv_fecha=findViewById(R.id.tv_fecha);
        tv_usuario_us=findViewById(R.id.txt_usuario_us);
        tv_fondos=findViewById(R.id.tv_fondos);
        tv_ap_hoy=findViewById(R.id.tv_ap_hoy);
        tv_rt_hoy=findViewById(R.id.tv_rt_hoy);
        tv_mensaje=findViewById(R.id.tv_mensaje);
        cargar_datos();
        //Boton aportar
        ImageButton btn_aportar = (ImageButton) findViewById(R.id.btn_aportar);
        btn_aportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AportesActivity.this, Aportar.class));
                //Toast.makeText(menu_inicio.this, "url", Toast.LENGTH_SHORT).show();
            }
        });

        //boton retiros
        ImageButton btn_retiros = (ImageButton) findViewById(R.id.btn_retiros);
        btn_retiros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AportesActivity.this, retiro.class));
                //Toast.makeText(menu_inicio.this, "url", Toast.LENGTH_SHORT).show();
            }
        });


        //boton historial
        ImageButton btn_historial = (ImageButton) findViewById(R.id.btn_historial);
        btn_historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AportesActivity.this, Activity_historial_transaccion.class));
                //Toast.makeText(menu_inicio.this, "url", Toast.LENGTH_SHORT).show();
            }
        });




        //Boton refrescar
        btn_actualizar=findViewById(R.id.btn_refres);
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar_fondos();
                consulta_hoy();
                T_retiros_hoy();
                verificar_pres_dispo();
                cargar_datos();
            }
        });
    }

    public  void cargar_datos(){

        tv_mensaje.setVisibility(View.VISIBLE);
        tv_mensaje.setText(msj_prestamo);
        tv_comunidad.setText(gp_1);
        tv_fecha.setText(fecha_hoy);
        tv_usuario_us.setText(nombre_us_usu);
        tv_fondos.setText(fondos_us);
        tv_ap_hoy.setText(hoy_tran);
        tv_rt_hoy.setText(retiros_hoy);
    }


    public void consultar_fondos(){
        //String ap="null";
        aportar_conm = new AsyncHttpClient();


        String ci_us = ci_us_1.toString().replace(" ", "%20");

        String url = "http://www.marlonmym.tk/chulki/aportar/consultar_fondos.php?ci_us="+ci_us;

        aportar_conm.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(AportesActivity.this, "Error ...!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {


                            JSONObject jsonObj = new JSONObject(respuesta);
                            String ap = jsonObj.getString("dato");
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("fondos_us", ap);
                            editor.apply();



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AportesActivity.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


            }});

        // preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        // hoy_tran=preferences.getString("aportes_hoy", null);
        // Toast.makeText(Aportar.this, hoy_tran, Toast.LENGTH_SHORT).show();

    }
    public void consulta_hoy(){
        //String ap="null";
        aportar_conm = new AsyncHttpClient();


        String ci_us = ci_us_1.toString().replace(" ", "%20");
        String fecha22 = fecha_hoy.toString().replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/aportar/aporte_hoy.php?ci_us="+ci_us+"&fecha="+fecha22;

        aportar_conm.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(AportesActivity.this, "Error ...!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {


                            JSONObject jsonObj = new JSONObject(respuesta);
                            String ap = jsonObj.getString("dato");
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("aportes_hoy", ap);
                            editor.apply();
                            preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
                            hoy_tran=preferences.getString("aportes_hoy", null);




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AportesActivity.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


            }});

        // preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        // hoy_tran=preferences.getString("aportes_hoy", null);
        // Toast.makeText(Aportar.this, hoy_tran, Toast.LENGTH_SHORT).show();

    }
    public void T_retiros_hoy(){
        //String ap="null";
        aportar_conm = new AsyncHttpClient();


        String ci_us = ci_us_1.toString().replace(" ", "%20");
        String fecha22 = fecha_hoy.toString().replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/retirar/retiro_hoy.php?ci_us="+ci_us+"&fecha="+fecha22;

        aportar_conm.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(AportesActivity.this, "Error ...!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {


                            JSONObject jsonObj = new JSONObject(respuesta);
                            String ap = jsonObj.getString("dato");
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("retiro_hoy", ap);
                            editor.apply();
                            preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
                            retiros_hoy=preferences.getString("retiro_hoy", null);




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AportesActivity.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


            }});

        // preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        // hoy_tran=preferences.getString("aportes_hoy", null);
        // Toast.makeText(Aportar.this, hoy_tran, Toast.LENGTH_SHORT).show();

    }
    public void verificar_pres_dispo(){
        //String ap="null";
        aportar_conm = new AsyncHttpClient();


        String ci_us = ci_us_1.toString().replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/prestamos/consulta_bandera_prestamo.php?ci_us="+ci_us;

        aportar_conm.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(AportesActivity.this, "Error ...!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {


                            JSONObject jsonObj = new JSONObject(respuesta);
                            String ap = jsonObj.getString("dato");
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("linea_ap", ap);
                            editor.apply();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AportesActivity.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


            }});

        // preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        // hoy_tran=preferences.getString("aportes_hoy", null);
        // Toast.makeText(Aportar.this, hoy_tran, Toast.LENGTH_SHORT).show();

    }




}