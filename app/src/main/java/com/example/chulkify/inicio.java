package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class inicio extends AppCompatActivity {

    private SharedPreferences preferences;
    private AsyncHttpClient comu_clien,usuario_clien;


    private AsyncHttpClient buscar_version;
    private  String  resp_2;

    private String usuario;
    //variables para fecha
    private String fecha;
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa;
    private int minutos_aux;
    private int m_cadu=0;
    private String mnt="0";

    //variables para clave y usuario
    private String us, pw;
    private String codigo1;

    //version
    private String version_1="v2.1", taza_transacion="0.25", maximo="500";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("cedula_usuario", null);

        comu_clien = new AsyncHttpClient();

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("version", version_1);
        editor.putString("taza", taza_transacion);
        editor.putString("maximo", maximo);
        editor.apply();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Toast.makeText(inicio.this, version_1, Toast.LENGTH_SHORT).show();

                verificar_sesion();
                //Intent intent= new Intent(inicio.this, menu_comunidad.class);
                //startActivity(intent);
                inicio.this.finish();
            }
        },07000);




    }

    private void verificar_sesion(){
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        if ((preferences.getString("nombre_usuario", null) != null)&&(preferences.getString("pass", null) != null)){


            if((preferences.getInt("anio_cadu", 0) != 0)&&(preferences.getInt("mes_cadu", 0) != 0)&&(preferences.getInt("dia_cadu", 0) != 0)){
                int aaa=anio_actual();
                int mmm=mes_actual();
                int ddd=dia_actual();
                int hhh=hora_actual();
                int mnt=minuto_actual();
                int sss=segundo_actual();

                int aux_anio = preferences.getInt("anio_cadu", 0);
                int aux_mes = preferences.getInt("mes_cadu", 0);
                int aux_dia = preferences.getInt("dia_cadu", 0);
                int aux_hora = preferences.getInt("hora_cadu", 0);
                int aux_mnt = preferences.getInt("minuto_cadu", 0);
                int aux_segundo = preferences.getInt("segundo_cadu", 0);


                if (aaa >= aux_anio){
                    if (mmm >= aux_mes){
                        if (ddd >= aux_dia){
                            if (hhh >= aux_hora){
                                if (mnt >= aux_mnt){
                                    preferences.edit().clear().apply();
                                    Toast.makeText(inicio.this, "La session ah caducado", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(inicio.this, menu_inicio.class));

                                }
                                else{
                                    us=preferences.getString("nombre_usuario", null);
                                    pw=preferences.getString("pass", null);

                                    usuario_clien = new AsyncHttpClient();
                                    ini_seccion();
                                }
                            }
                            else{
                                us=preferences.getString("nombre_usuario", null);
                                pw=preferences.getString("pass", null);

                                usuario_clien = new AsyncHttpClient();
                                ini_seccion();
                            }
                        }
                        else{
                            us=preferences.getString("nombre_usuario", null);
                            pw=preferences.getString("pass", null);

                            usuario_clien = new AsyncHttpClient();
                            ini_seccion();
                        }
                    }
                    else{
                        us=preferences.getString("nombre_usuario", null);
                        pw=preferences.getString("pass", null);

                        usuario_clien = new AsyncHttpClient();
                        ini_seccion();
                    }

                }

                else{
                    us=preferences.getString("nombre_usuario", null);
                    pw=preferences.getString("pass", null);

                    usuario_clien = new AsyncHttpClient();
                    ini_seccion();
                }
            }
            else{
                us=preferences.getString("nombre_usuario", null);
                pw=preferences.getString("pass", null);

                usuario_clien = new AsyncHttpClient();
                ini_seccion();


            }




        }
        else {
            startActivity(new Intent(inicio.this, menu_inicio.class));
            //Toast.makeText(inicio.this, version_1, Toast.LENGTH_SHORT).show();
        }

    }




    private void ini_seccion() {



        String usuario = us.replace(" ", "%20");
        String password =   pw.replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/login.php?usuario_us="+usuario+"&contrasena_us="+password;
        //Toast.makeText(Login.this, url, Toast.LENGTH_SHORT).show();

        usuario_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {



                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(inicio.this, "Error De Usuario y/o Contraseña!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            caducidad();
                            JSONObject jsonObj = new JSONObject(respuesta);

                            Logear_usuario u = new Logear_usuario();
                            u.setId(jsonObj.getInt("id_us"));
                            u.setTipo(jsonObj.getInt("tipo_us"));
                            int n_tp= jsonObj.getInt("tipo_us");


                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("cedula_usuario", jsonObj.getString("cedula_us"));
                            editor.putString("nombre_usuario", jsonObj.getString("usuario_us"));
                            editor.putInt("id", jsonObj.getInt("id_us"));
                            editor.putString("comunidad", jsonObj.getString("grupo_us"));
                            codigo1 = jsonObj.getString("grupo_us");
                            editor.putInt("tipo", jsonObj.getInt("tipo_us"));
                            editor.putString("fecha_ini", jsonObj.getString("fecha_inicio_us"));
                            editor.putString("pass", jsonObj.getString("contrasena_us"));
                            editor.putString("fecha_union_grupo", jsonObj.getString("fecha_union_comu_us"));
                            editor.putInt("estado_usuario", jsonObj.getInt("estado_gru_us"));
                            editor.apply();

                            Intent intent = null;
                            switch (n_tp) {
                                case 0:
                                    intent= new Intent(inicio.this, MainActivity.class);
                                    break;
                                case 1:
                                    intent= new Intent(inicio.this, cargar_1.class);
                                    break;
                                case 2:
                                    intent= new Intent(inicio.this, cargar_1.class);
                                    break;
                                case 3:
                                    intent= new Intent(inicio.this, cargar3.class);

                                    break;

                            }

                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(inicio.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();

            }


        });



    }


    private int fyh_actual(){
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();

        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        diaS= String.valueOf(dia);
        mesS = String.valueOf(mes);
        anioS = String.valueOf(anio);
        fecha = diaS+"/"+mesS+"/"+anioS;

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

        return minutos;

    }
    private int anio_actual(){
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();
        anio= fecha_a.get(Calendar.YEAR);
        return anio;
    }
    private int mes_actual(){
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();
        mes= fecha_a.get(Calendar.MONTH)+1;
        return mes;
    }
    private int dia_actual(){
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();
        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        return dia;
    }
    private int hora_actual(){
        Date date = new Date();
        SimpleDateFormat hh = new SimpleDateFormat("k");
        hora=Integer.parseInt(hh.format(date));
        return hora;
    }
    private int minuto_actual(){
        Date date = new Date();
        SimpleDateFormat mm= new SimpleDateFormat("m");
        minutos=Integer.parseInt(mm.format(date));
        return minutos;
    }
    private int segundo_actual(){
        Date date = new Date();
        SimpleDateFormat ss= new SimpleDateFormat("s");
        segundos=Integer.parseInt(ss.format(date));
        return segundos;
    }
    private void caducidad(){
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();

        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        diaS= String.valueOf(dia);
        mesS = String.valueOf(mes);
        anioS = String.valueOf(anio);
        fecha = diaS+"/"+mesS+"/"+anioS;

        SimpleDateFormat h= new SimpleDateFormat("kk");
        horaS=h.format(date);
        SimpleDateFormat hh = new SimpleDateFormat("k");
        hora=Integer.parseInt(hh.format(date));


        SimpleDateFormat m= new SimpleDateFormat("mm");
        minutosS=m.format(date);
        SimpleDateFormat mm= new SimpleDateFormat("m");
        minutos=Integer.parseInt(mm.format(date));


        SimpleDateFormat s= new SimpleDateFormat("ss");
        segundosS=s.format(date);
        SimpleDateFormat ss= new SimpleDateFormat("s");
        segundos=Integer.parseInt(ss.format(date));






        if ((anio%4)==0){
            if (mes == 1){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 2){
                if ((dia <= 27) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 28){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 3){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 4){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 5){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 6){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 7){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 8){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 9){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 10){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 11){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 12){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=1;
                    dia=1;
                    anio=anio+1;
                }
                else if (dia == 31){
                    mes=1;
                    dia=2;
                    anio=anio+1;
                }
            }
        }
        else {
            if (mes == 1){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 2){
                if ((dia <= 26) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 27){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 28){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 3){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 4){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 5){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 6){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 7){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 8){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 9){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 10){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 11){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 12){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=1;
                    dia=1;
                    anio=anio+1;
                }
                else if (dia == 31){
                    mes=1;
                    dia=2;
                    anio=anio+1;
                }
            }
        }


        String aaaa= String.valueOf(anio);
        String mmmm= String.valueOf(mes);
        String dddd= String.valueOf(dia);
        String hhhh= String.valueOf(hora);
        String mnt= String.valueOf(minutos);
        String ssss= String.valueOf(segundos);

        fecha = diaS+"/"+mesS+"/"+anioS+" - "+horaS+":"+minutosS+":"+segundosS+" -/- "+dddd+"/"+mmmm+"/"+aaaa+" - "+hhhh+":"+mnt+":"+ssss;

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("fecha_actual", fecha);
        editor.putInt("anio_cadu", anio);
        editor.putInt("mes_cadu", mes);
        editor.putInt("dia_cadu", dia);
        editor.putInt("hora_cadu", hora);
        editor.putInt("minuto_cadu", minutos);
        editor.putInt("segundo_cadu", segundos);

        editor.apply();


    }


}