package com.example.chulkify.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.chulkify.R;
import com.example.chulkify.menus_usuarios.menu_comunidad;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class cargar_1 extends AppCompatActivity {

    private SharedPreferences preferences;
    private AsyncHttpClient comu_clien;
    private String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_1);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("comunidad", null);

        comu_clien = new AsyncHttpClient();
        cargar_datos();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(cargar_1.this, menu_comunidad.class);
                startActivity(intent);
                cargar_1.this.finish();
            }
        },07000);
    }

    private void cargar_datos(){
        String cog_comu = usuario.replace(" ", "%20");
        String l_c_comunidad=getString(R.string.link_consultar_comunidad);
        String url = l_c_comunidad+"?codigo_comu="+cog_comu;

        comu_clien.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(cargar_1.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {

                            JSONObject jsonObj = new JSONObject(respuesta);
                            SharedPreferences.Editor editor=preferences.edit();

                            String aux1="dt1"+jsonObj.getInt("id_comu")+"dt2"+jsonObj.getString("nombre_comu")+"dt3"+jsonObj.getString("codigo_comu")+"dt4"+ jsonObj.getInt("total_usuario_comu");
                            editor.putInt("id_comu", jsonObj.getInt("id_comu"));
                            editor.putString("nombre_comu", jsonObj.getString("nombre_comu"));
                            editor.putString("codigo_comu", jsonObj.getString("codigo_comu"));
                            editor.putInt("total_usuario_comu", jsonObj.getInt("total_usuario_comu"));
                            //Toast.makeText(cargar_1.this, "ENTRO 4545    -"+aux1, Toast.LENGTH_SHORT).show();

                            editor.apply();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(cargar_1.this, "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();
            }
        });
    }
}