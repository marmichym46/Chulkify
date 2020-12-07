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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class menu_comunidad extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    private SharedPreferences preferences;
    private AsyncHttpClient buscar_url;


    private TextView nombre_comu, fecha_comu, mv_usuario, tv_codigo,tv_t_us;
    private String usuario, fecha_m;
    private String codg_comu, city_comu,  nom_comu, fecha_at;
    private AsyncHttpClient comu_clien;
    private  int id_comu;
    private String nomb,most_fecha, n_comu, m_usuario, conv_total_us,total_us_comu;
    private Button btn_actualizar;
    private EditText codigo_cop;

    private String fecha;
    private int  dia, mes, anio;
    private int  diaS, mesS, anioS;

    private String version, url="null", resp,res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_comunidad);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        version = preferences.getString("version", null);
        usuario = preferences.getString("cedula_usuario", null);
        n_comu = preferences.getString("nombre_comu", null);
        fecha_m = preferences.getString("fecha_union_grupo", null);
        m_usuario = preferences.getString("nombre_usuario" , null);
        codg_comu = preferences.getString("codigo_comu",null);
        fecha_at = preferences.getString("fecha_actual",null);
        total_us_comu= String.valueOf(preferences.getInt("total_usuario_comu",0));


        nombre_comu=findViewById(R.id.tv_comunidad_m);
        fecha_comu=findViewById(R.id.tv_fecha);
        mv_usuario=findViewById(R.id.txt_usuario_us);
        tv_codigo =findViewById(R.id.tv_codigo);
        tv_t_us   =findViewById(R.id.tv_t_us);
        codigo_cop = findViewById(R.id.edt_codigo);
        buscar_url =new AsyncHttpClient();
        String aux =(buscar_url(version));


        comu_clien = new AsyncHttpClient();

        Calendar fecha_a = Calendar.getInstance();
        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);
        String diaS= String.valueOf(dia);
        String mesS = String.valueOf(mes);
        String anioS = String.valueOf(anio);
        fecha = diaS+"/"+mesS+"/"+anioS;

        mostrar_datos();



        btn_actualizar = (Button) findViewById(R.id.btn_refres);
        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargar_datos();
            }
        });








    }

    private void cargar_datos(){
        String cog_comu = usuario.replace(" ", "%20");
        String url = "http://www.marlonmym.tk/chulki/consulta_comu.php?codigo_comu="+cog_comu;


        comu_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {



                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(menu_comunidad.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {

                            JSONObject jsonObj = new JSONObject(respuesta);

                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("ciudad_comu", jsonObj.getString("ciudad_comu"));
                            editor.putInt("id_comu", jsonObj.getInt("id_comu"));
                            editor.putString("nombre_comu", jsonObj.getString("nombre_comu"));
                            editor.putString("codigo_comu", jsonObj.getString("codigo_comu"));
                            editor.putInt("total_usuario_comu", jsonObj.getInt("total_usuario_comu"));
                            editor.apply();
                            preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
                            n_comu = preferences.getString("nombre_comu", null);
                            codg_comu = preferences.getString("codigo_comu",null);
                            total_us_comu= String.valueOf(preferences.getInt("total_usuario_comu",0));
                            mostrar_datos();




                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(menu_comunidad.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();

            }


        });
    }
    private void mostrar_datos(){

        most_fecha= fecha_m+"  -  "+fecha;
        nombre_comu.setText(n_comu);
        fecha_comu.setText(most_fecha);
        mv_usuario.setText(m_usuario);
        codigo_cop.setText(codg_comu);
        tv_codigo.setText(fecha_at);
        tv_t_us.setText(total_us_comu);
    }



    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_of, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.menu_iten_c_sesion){
            preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
            preferences.edit().clear().apply();
            startActivity(new Intent(menu_comunidad.this, inicio.class));
            //Intent intent= new Intent(menu_comunidad.this, Login.class);
        }
        else if (id == R.id.menu_actualizacion){
            String msj1=("ult_vs");
            String msj2=("error");

            url =(buscar_url(version));
            //buscar_url();
            //Toast.makeText(espera_soli.this, url, Toast.LENGTH_SHORT).show();

            if(url.equals("ult_vs")){
                Toast.makeText(menu_comunidad.this, "la version "+version+" es la mas reciente", Toast.LENGTH_SHORT).show();
            }else if (url.equals("error")){
                Toast.makeText(menu_comunidad.this, "Hay un error desconocido", Toast.LENGTH_SHORT).show();
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
        url =(buscar_url(version));
        String url_d= url.trim();

        File file = new File(getExternalFilesDir(null), "chulkify.apk");
        Toast.makeText(menu_comunidad.this, "Se inicio la descarga", Toast.LENGTH_SHORT).show();

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
        //

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
                    Toast.makeText(menu_comunidad.this, "Permiso denegado...!!!", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(menu_comunidad.this, "Error al  buscar actualizaciones", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(menu_comunidad.this, "Error Desconocido. Intentelo De Nuevo!!" + responseBody, Toast.LENGTH_SHORT).show();
            }


        });
        return resp;
    }







}