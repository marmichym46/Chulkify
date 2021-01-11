package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class Aportar extends AppCompatActivity {


    private SharedPreferences preferences;
    private AsyncHttpClient aportar_conm;


    private TextView fecha_report, usuario_report, aporte_report, taza_report, total_report, nomb_comu, dato_hoy;
    private EditText val_aportar;
    private Button btn_actualizar, btn_realizar_aporte, btn_confirmar_aporte;
    private String fecha1, fecha2, hora1;
    private LinearLayout tarjeta1;

    //Variables para capturar preferencias
    private String ci_us_1, gp_1, cg_gp_1, taza,hoy_tran, maximo;

    //variables para capturar fecha
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aportar);

        //capturar preferencias
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        ci_us_1=preferences.getString("cedula_usuario", null);
        gp_1=preferences.getString("nombre_comu", null);
        cg_gp_1=preferences.getString("codigo_comu", null);
        taza=preferences.getString("taza", null);
        maximo=preferences.getString("maximo", null);

        //captura la fecha
        fecha1=devolver_fecha();
        fecha2=devolver_fecha_formato();
        hora1=devolver_hora();

        calcular_ap();
        calcular_ap();


        //istanciar controles del xml de la actividad
        fecha_report=findViewById(R.id.txt_fecha);
        usuario_report =findViewById(R.id.txt_usuario_tran);
        aporte_report=findViewById(R.id.txt_aporte_tran);
        taza_report=findViewById(R.id.txt_v_tran);
        total_report=findViewById(R.id.txt_total_tran);
        val_aportar=findViewById(R.id.edt_valor_aportar);
        btn_actualizar=findViewById(R.id.btn_refres);
        btn_realizar_aporte=findViewById(R.id.btn_reaizar_aporte);
        btn_confirmar_aporte=findViewById(R.id.btn_confirmar_aporte);
        tarjeta1=(LinearLayout) findViewById(R.id.tarjeta1);
        nomb_comu=findViewById(R.id.tv_comunidad_m);
        dato_hoy=findViewById(R.id.tv_fecha);
        //LinearLayoutManager lm = new LinearLayoutManager(tarjeta1);

        cargar_datos();
        cargar_datos();
//Boton realizar aporte
        btn_realizar_aporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double val11 =Double.parseDouble(hoy_tran);
                Double val21 =Double.parseDouble(maximo);
                Double val31 = val21-val11;


               if(val31<(Double.parseDouble(val_aportar.getText().toString()))){
                   Toast.makeText(Aportar.this, "exedio el maximo de aporte en el dia ", Toast.LENGTH_SHORT).show();
               }else {


                if (val_aportar.getText().toString().isEmpty()) {
                    Toast.makeText(Aportar.this, "Hay campos en blanco ", Toast.LENGTH_SHORT).show();
                } else {



                    cargar_transaccion();}
            }
            }

        });

        //Boton confirmar aporte
        btn_confirmar_aporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultar_tarjeta();
                aportar();
            }
        });

        //Boton refrescar
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultar_tarjeta();
                calcular_ap();
                cargar_datos();
            }
        });

    }

    public  String devolver_fecha(){
        String f="0";
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();
        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        diaS= String.valueOf(dia);
        mesS = String.valueOf(mes);
        anioS = String.valueOf(anio);

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

        f = diaS+"/"+mesS+"/"+anioS;

        return f;
    }

    public  String devolver_hora(){
        String f="0";
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();
        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        diaS= String.valueOf(dia);
        mesS = String.valueOf(mes);
        anioS = String.valueOf(anio);

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

        f = horaS+":"+minutosS+":"+segundosS;

        return f;
    }

    public  String devolver_fecha_formato(){
        String f="0";
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();
        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        diaS= String.valueOf(dia);
        mesS = String.valueOf(mes);
        anioS = String.valueOf(anio);

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

        f = diaS+"/"+mesS+"/"+anioS+"  -  "+horaS+":"+minutosS+":"+segundosS;

        return f;
    }

    public void cargar_datos(){

        String val_fecha="hoy: "+fecha1+"    Aporte: $"+hoy_tran;

        nomb_comu.setText(gp_1);
        dato_hoy.setText(val_fecha);

    }

    public void cargar_transaccion(){

        fecha_report.setText(fecha2);
        usuario_report.setText(ci_us_1);

        Double val1 =Double.parseDouble(val_aportar.getText().toString());

        Double val2 =Double.parseDouble(taza);
        Double val3 = val1-val2;

        aporte_report.setText(String.valueOf(val3));
        taza_report.setText(String.valueOf(val2));
        total_report.setText(String.valueOf(val1));

        mostrar_tarjeta();

    }

    public void mostrar_tarjeta(){
        tarjeta1.setVisibility(View.VISIBLE);
    }

    public void ocultar_tarjeta(){
        tarjeta1.setVisibility(View.GONE);
    }

    public void aportar() {
        aportar_conm = new AsyncHttpClient();

        final String ci_us = ci_us_1.trim();
        final String aporte = val_aportar.getText().toString().trim();
        final String fecha = fecha1.toString().trim();
        final String hora = hora1.toString().trim();
        final String gp = gp_1.replace(" ", "%20").trim();
        final String cg_gp = cg_gp_1.trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.marlonmym.tk/chulki/aportar/aportar_us.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("transaccion_correcta")) {

                    Intent intent = new Intent(Aportar.this, AportesActivity.class);
                    startActivity(intent);
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
        RequestQueue requestQueue = Volley.newRequestQueue(Aportar.this);
        requestQueue.add(stringRequest);



    }

    public void calcular_ap(){
        //String ap="null";
        aportar_conm = new AsyncHttpClient();


        String ci_us = ci_us_1.toString().replace(" ", "%20");
        String fecha = fecha1.toString().replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/aportar/aporte_hoy.php?ci_us="+ci_us+"&fecha="+fecha;

        aportar_conm.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(Aportar.this, "Error ...!!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Aportar.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


            }});

       // preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
       // hoy_tran=preferences.getString("aportes_hoy", null);
       // Toast.makeText(Aportar.this, hoy_tran, Toast.LENGTH_SHORT).show();

    }


}