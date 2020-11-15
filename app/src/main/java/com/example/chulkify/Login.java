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
    private TextView tvw_registrar, tvw_recordar_pass;


    private AsyncHttpClient usuario_clien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_Logear = (Button) findViewById(R.id.btn_ingresar);
        et_usuario = (EditText) findViewById(R.id.txt_lg_user);
        et_contra = (EditText) findViewById(R.id.txt_lg_pass);

        usuario_clien = new AsyncHttpClient();
        botonLoguin(); }

    private void botonLoguin() {
        btn_Logear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_usuario.getText().toString().isEmpty() || et_contra.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Hay campos en blanco ", Toast.LENGTH_SHORT).show();
                } else {
                    String usuario = et_usuario.getText().toString();
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

                                        Logear_usuario user = new Logear_usuario();

                                        user.setCi(jsonObj.getString("cedula_us"));
                                        user.setNombre_usuario(jsonObj.getString("usuario_us"));
                                        user.setNombre(jsonObj.getString("nombre_us"));
                                        user.setTipo(jsonObj.getInt("tipo_us"));
                                        Toast.makeText(Login.this, "prueba entro6", Toast.LENGTH_SHORT).show();
                                        user.setPassword(jsonObj.getString("contrasena_us"));
                                        Toast.makeText(Login.this, "prueba entro5", Toast.LENGTH_SHORT).show();
                                        user.setApellidos(jsonObj.getString("apellidos_us"));
                                        Toast.makeText(Login.this, "prueba entro4", Toast.LENGTH_SHORT).show();
                                        user.setFondos(jsonObj.getDouble("fondos_us"));
                                        Toast.makeText(Login.this, "prueba entro3", Toast.LENGTH_SHORT).show();
                                        user.setEstado_grupo(jsonObj.getInt("estado_gru_us"));
                                        user.setId_cuenta(jsonObj.getInt("id_cuenta_us"));

                                        user.setGrupo(jsonObj.getInt("grupo_us"));

                                        Intent i = null;
                                        switch (user.getGrupo()) {
                                            case 0:
                                                Toast.makeText(Login.this, "prueba entro", Toast.LENGTH_SHORT).show();
                                                user.setEstado_grupo(0);
                                                i = new Intent(Login.this, MainActivity.class);
                                                break;

                                            default:
                                                Toast.makeText(getApplicationContext(), "PROBLEMA CON LA ASIGNACION DE GRUPO", Toast.LENGTH_SHORT).show();
                                                break;
                                        }
                                        //i.putExtra("")

                                       startActivity(i);
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



