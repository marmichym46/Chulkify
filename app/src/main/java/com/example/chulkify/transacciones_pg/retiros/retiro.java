package com.example.chulkify.transacciones_pg.retiros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chulkify.Manejo_fechas;
import com.example.chulkify.R;
import com.example.chulkify.transacciones_pg.aportes.Aportar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class retiro extends AppCompatActivity {

    private SharedPreferences preferences;
    private AsyncHttpClient aportar_conm;


    private TextView fecha_report, usuario_report, retiro_report, taza_report, total_report, nomb_comu, dato_hoy;
    private EditText val_retiro;
    private Button btn_actualizar, btn_realizar_retiro, btn_confirmar_retiro;
    private String fecha1, fecha2, hora1;
    private LinearLayout tarjeta1;
    private String fondos_us;

    //Variables para capturar preferencias
    private String ci_us_1, gp_1, cg_gp_1, taza,hoy_tran, maximo;

    //variables para capturar fecha
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);

        //capturar preferencias
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        ci_us_1=preferences.getString("cedula_usuario", null);
        gp_1=preferences.getString("nombre_comu", null);
        cg_gp_1=preferences.getString("codigo_comu", null);
        taza=preferences.getString("taza", null);
        fondos_us=preferences.getString("fondos_us", null);
        hoy_tran=preferences.getString("retiro_hoy", null);
        maximo=preferences.getString("maximo", null);

        //captura la fecha
        Manejo_fechas mf=new Manejo_fechas();
        fecha1=mf.fecha_actual();
        hora1=mf.hora_actual_formato();

        consulta_datos();


        //istanciar controles del xml de la actividad
        fecha_report=findViewById(R.id.txt_fecha);
        usuario_report =findViewById(R.id.txt_usuario_tran);
        retiro_report=findViewById(R.id.txt_retiro_tran);
        taza_report=findViewById(R.id.txt_v_tran);
        total_report=findViewById(R.id.txt_total_tran);
        val_retiro=findViewById(R.id.edt_valor_aportar);
        btn_actualizar=findViewById(R.id.btn_refres);
        btn_realizar_retiro=findViewById(R.id.btn_realizar_retiro);
        btn_confirmar_retiro=findViewById(R.id.btn_confirmar_retiro);
        tarjeta1=(LinearLayout) findViewById(R.id.tarjeta1);
        nomb_comu=findViewById(R.id.tv_comunidad_m);
        dato_hoy=findViewById(R.id.tv_fecha);
        //LinearLayoutManager lm = new LinearLayoutManager(tarjeta1);

        cargar_datos();
//Boton realizar aporte
        btn_realizar_retiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double val11 =Double.parseDouble(hoy_tran);
                Double val21 =Double.parseDouble(maximo);
                Double val31 = val21-val11;

                Double val41 =Double.parseDouble(val_retiro.getText().toString());
                Double val51 =Double.parseDouble(taza);
                Double val61 =val41+val51;

                Double val71 =Double.parseDouble(fondos_us);


                if(val_retiro.getText().toString().isEmpty()){
                    Toast.makeText(retiro.this, "Hay campos en blanco ", Toast.LENGTH_SHORT).show();
                }else {
                    if (val31<(Double.parseDouble(val_retiro.getText().toString()))) {
                        Toast.makeText(retiro.this, "excedio el maximo de retiro en el dia ", Toast.LENGTH_SHORT).show();
                    } else {
                        if(val71 >=val61){
                            cargar_transaccion();
                        }
                        else {
                            Toast.makeText(retiro.this, "Los fondos son insuficientes ", Toast.LENGTH_SHORT).show();
                        }
                       }
                }






            }

        });

        //Boton confirmar aporte
        btn_confirmar_retiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultar_tarjeta();
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("val_aportar", val_retiro.getText().toString());
                editor.putString("fecha_t_ap", fecha1);
                editor.putString("hora_t_ap", hora1);
                editor.apply();
                //aportar();
                Intent intent = new Intent(retiro.this, cargar_retiro.class);
                startActivity(intent);
            }
        });

        //Boton refrescar
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultar_tarjeta();
                consulta_datos();
                cargar_datos();
            }
        });

    }


    public void cargar_datos(){

        String val_fecha="hoy: "+fecha1+"    Retiros de hoy: $"+hoy_tran;

        nomb_comu.setText(gp_1);
        dato_hoy.setText(val_fecha);

    }

    public void cargar_transaccion(){

        fecha_report.setText(fecha2);
        usuario_report.setText(ci_us_1);

        Double val1 =Double.parseDouble(val_retiro.getText().toString());

        Double val2 =Double.parseDouble(taza);
        Double val3 = val1 +val2;

        retiro_report.setText(String.valueOf(val1));
        taza_report.setText(String.valueOf(val2));
        total_report.setText(String.valueOf(val3));

        mostrar_tarjeta();

    }

    public void mostrar_tarjeta(){
        tarjeta1.setVisibility(View.VISIBLE);
    }

    public void ocultar_tarjeta(){
        tarjeta1.setVisibility(View.GONE);
    }

    public void consulta_datos(){
        //String ap="null";
        aportar_conm = new AsyncHttpClient();
        Manejo_fechas n_p =new Manejo_fechas();
        String fecha = n_p.fecha_actual();

        String ci_us = ci_us_1.toString().replace(" ", "%20");
        String fecha22 = fecha.toString().replace(" ", "%20");
        String l_consulta=getString(R.string.link_consulta_datos);
        String url = l_consulta+"?ci_us="+ci_us+"&fecha="+fecha22;

        aportar_conm.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(retiro.this, "Error ...!!", Toast.LENGTH_SHORT).show();
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

                            preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
                            hoy_tran=preferences.getString("retiro_hoy", null);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(retiro.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();
            }});
    }

    /*public void aportar() {
        aportar_conm = new AsyncHttpClient();

        final String ci_us = ci_us_1.trim();
        final String aporte = val_retiro.getText().toString().trim();
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
        RequestQueue requestQueue = Volley.newRequestQueue(retiro.this);
        requestQueue.add(stringRequest);



    }*/


}

