package com.example.chulkify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.Header;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Login extends AppCompatActivity {

    //private GoogleApiClient googleApiClient;

    private static final int PERMISSION_STORAGE_CODE = 1000;

    private EditText et_usuario, et_contra;
    private Button btn_Logear;
    private TextView tv1;
    private ImageButton  btn_new_us;
    private AsyncHttpClient usuario_clien, buscar_url;
    private SharedPreferences preferences;
    private String us, pw;

    private String codigo1;

    private String fecha;
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa;
    private int minutos_aux;
    private int m_cadu=0;
    private String mnt="0";
    private String version_1="default", url="null",resp,res;
    private Double taza_transacion=0.25;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //fyh_actual2();

        btn_Logear = (Button) findViewById(R.id.btn_ingresar);
        et_usuario = (EditText) findViewById(R.id.txt_lg_user);
        et_contra = (EditText) findViewById(R.id.txt_lg_pass);



        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        version_1=preferences.getString("version", null);
        Toast.makeText(Login.this, version_1, Toast.LENGTH_SHORT).show();

        buscar_url =new AsyncHttpClient();
        String aux =(buscar_url(version_1));

        usuario_clien = new AsyncHttpClient();
        botonLoguin();
    }

    private void botonLoguin() {
        btn_Logear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_usuario.getText().toString().isEmpty() || et_contra.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Hay campos en blanco ", Toast.LENGTH_SHORT).show();
                } else {
                    String usuario = et_usuario.getText().toString().replace(" ", "%20");
                    String password = et_contra.getText().toString().replace(" ", "%20");
                    String url = "http://www.marlonmym.tk/chulki/login.php?usuario_us="+usuario+"&contrasena_us="+password;
                    //Toast.makeText(Login.this, url, Toast.LENGTH_SHORT).show();

                    usuario_clien.post(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {



                            if (statusCode == 200) {
                                String respuesta = new String(responseBody);
                                if (respuesta.equalsIgnoreCase("null")) {
                                    Toast.makeText(Login.this, "Error De Usuario y/o Contraseña!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {


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
                                                intent= new Intent(Login.this, MainActivity.class);
                                                break;
                                            case 1:
                                                intent= new Intent(Login.this, cargar_1.class);
                                                break;
                                            case 2:
                                                intent= new Intent(Login.this, cargar_1.class);
                                                break;

                                            case 3:
                                                intent= new Intent(Login.this, cargar3.class);
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
                            Toast.makeText(Login.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


                            et_usuario.setText("");
                            et_contra.setText("");
                        }


                    });
                }

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

        fecha = diaS+"/"+mesS+"/"+anioS+" - "+horaS+":"+minutosS+":"+segundosS+" -/- "+dddd+"/"+mmmm+"/"+aaaa+" - "+hhhh+":"+mnt+"/"+ssss;

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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_ini, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_actualizacion){
            String msj1=("ult_vs");
            String msj2=("error");

            url =(buscar_url(version_1));
            //buscar_url();
            //Toast.makeText(espera_soli.this, url, Toast.LENGTH_SHORT).show();

            if(url.equals("ult_vs")){
                Toast.makeText(Login.this, "la version "+version_1+" es la mas reciente", Toast.LENGTH_SHORT).show();
            }else if (url.equals("error")){
                Toast.makeText(Login.this, "Hay un error desconocido", Toast.LENGTH_SHORT).show();
            }else{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    }else {
                        StartDownloading();
                    }
                }else {StartDownloading();}



            }





        }
        return super.onOptionsItemSelected(item);
    }
    private void StartDownloading() {
        url =(buscar_url(version_1));
        String url_d= url.trim();
        File file = new File(getExternalFilesDir(null), "chulkify.apk");
        Toast.makeText(Login.this, "Se inicio la descarga", Toast.LENGTH_SHORT).show();
        DownloadManager.Request request =null;
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            request = new DownloadManager.Request(Uri.parse(url_d));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle("Download");
            request.setDescription("Downloading apk...");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationUri(Uri.fromFile(file));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "chulkify.apk");
            request.setRequiresCharging(false);
            request.setAllowedOverMetered(true);
            request.setAllowedOverRoaming(true);
        }else{
            request = new DownloadManager.Request(Uri.parse(url_d));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle("Download");
            request.setDescription("Downloading apk...");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationUri(Uri.fromFile(file));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "chulkyfy.apk");
            request.setAllowedOverRoaming(true);
        }
        DownloadManager manager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  PERMISSION_STORAGE_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    StartDownloading();
                }
                else {
                    Toast.makeText(Login.this, "Permiso denegado...!!!", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }

    public String buscar_url(String dato) {


        String ci_usuario = dato.replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/links/version.php?url_ac="+ci_usuario;
        //Toast.makeText(Login.this, url, Toast.LENGTH_SHORT).show();

        buscar_url.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(Login.this, "Error al  buscar actualizaciones", Toast.LENGTH_SHORT).show();
                    } else {
                        try {

                            JSONObject jsonObj = new JSONObject(respuesta);
                            resp = jsonObj.getString("dato");
                            //Toast.makeText(espera_soli.this, resp, Toast.LENGTH_SHORT).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Login.this, "Error Desconocido. Intentelo De Nuevo!!" + responseBody, Toast.LENGTH_SHORT).show();
            }


        });
        return resp;
    }


}



