package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Login extends AppCompatActivity {

    private EditText et_usuario, et_contra;
    private Button btn_Logear;
    private TextView tv1;
    private TextView tvw_registrar, tvw_recordar_pass;
    private ImageButton  btn_new_us;
    private AsyncHttpClient usuario_clien;
    private SharedPreferences preferences;

    private String codigo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_Logear = (Button) findViewById(R.id.btn_ingresar);
        et_usuario = (EditText) findViewById(R.id.txt_lg_user);
        et_contra = (EditText) findViewById(R.id.txt_lg_pass);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);


        if (preferences.getString("nombre_usuario", null) != null){
            preferences.edit().clear().apply();

        }

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);






        btn_new_us = (ImageButton) findViewById(R.id.new_us);
        btn_new_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, nuevo_usuario.class));


            }
        });

        usuario_clien = new AsyncHttpClient();
        botonLoguin(); }

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
                                        editor.putString("fecha_union_grupo", jsonObj.getString("fecha_union_comu_us"));
                                        editor.apply();

                                        Intent intent = null;
                                        switch (n_tp) {
                                            case 0:
                                                intent= new Intent(Login.this, MainActivity.class);
                                                break;
                                            case 1:
                                                Toast.makeText(Login.this, "1 :la actividad aun esta en mantnimiento", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 2:
                                                intent= new Intent(Login.this, cargar_1.class);
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

    }}



