package com.example.chulkify.login;

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
import android.widget.Toast;


import com.example.chulkify.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class menu_inicio extends AppCompatActivity {

    //public Button btn_inisesion, btn_registrar;
    private SharedPreferences preferences;
    //variables para menu superior derecho
    private static final int PERMISSION_STORAGE_CODE = 1000;
    private String version_1="default", url="null",resp,res;
    private AsyncHttpClient buscar_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        version_1=preferences.getString("version", null);
        buscar_url =new AsyncHttpClient();
        String aux =(buscar_url(version_1));


        //Boton iniciar sesion
        Button btn_inisesion = (Button) findViewById(R.id.btn_ini_sesion);
        btn_inisesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(menu_inicio.this, Login.class));
                //Toast.makeText(menu_inicio.this, "url", Toast.LENGTH_SHORT).show();
            }
        });

        //Boton Registrarse
        Button btn_registrar = (Button) findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(menu_inicio.this, nuevo_usuario.class));
                //Toast.makeText(menu_inicio.this, "url", Toast.LENGTH_SHORT).show();
            }
        });

    }


    // menu superoir derecho
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
                Toast.makeText(menu_inicio.this, "la version "+version_1+" es la mas reciente", Toast.LENGTH_SHORT).show();
            }else if (url.equals("error")){
                Toast.makeText(menu_inicio.this, "Hay un error desconocido", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(menu_inicio.this, "Se inicio la descarga", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(menu_inicio.this, "Permiso denegado...!!!", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }

    public String buscar_url(String dato) {


        String ci_usuario = dato.replace(" ", "%20");
        String l_lvs= getString(R.string.link_actualizar);
        String url = l_lvs+"?url_ac="+ci_usuario;
        //Toast.makeText(Login.this, url, Toast.LENGTH_SHORT).show();

        buscar_url.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(menu_inicio.this, "Error al  buscar actualizaciones", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(menu_inicio.this, "Error Desconocido. Intentelo De Nuevo!!" + responseBody, Toast.LENGTH_SHORT).show();
            }


        });
        return resp;
    }


}