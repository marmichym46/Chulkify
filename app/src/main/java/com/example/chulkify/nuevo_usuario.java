package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import cz.msebera.android.httpclient.Header;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class nuevo_usuario extends AppCompatActivity {

    private EditText edt_cedula, edt_usuario, edt_correo, edt_nombre,
            edt_apellido, edt_contrasena1, edt_contrasena2, edt_direccion,
            edt_ciudad, edt_telefono;

    private String fecha;
    private int  dia, mes, anio;
    private int  diaS, mesS, anioS;
    private Button btn_guardar;
    private AsyncHttpClient cliente;
    private Date fecha_dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        Calendar fecha_a = Calendar.getInstance();
        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);
        String diaS= String.valueOf(dia);
        String mesS = String.valueOf(mes);
        String anioS = String.valueOf(anio);
        fecha = diaS+"/"+mesS+"/"+anioS;


        edt_cedula=(EditText) findViewById(R.id.txt_nu_ci);
        edt_usuario=(EditText) findViewById(R.id.txt_nu_user);
        edt_correo=(EditText) findViewById(R.id.txt_nu_correo);
        edt_nombre=(EditText) findViewById(R.id.txt_nu_nombre);
        edt_apellido=(EditText) findViewById(R.id.txt_nu_apellido);
        edt_contrasena1=(EditText) findViewById(R.id.txt_nu_pass);
        edt_contrasena2=(EditText) findViewById(R.id.txt_nu_cpass);
        edt_direccion=(EditText) findViewById(R.id.txt_nu_direccion);
        edt_ciudad=(EditText) findViewById(R.id.txt_nu_ciudad);
        edt_telefono=(EditText) findViewById(R.id.txt_nu_telefono);




        cliente=new AsyncHttpClient();


        btn_guardar=(Button) findViewById(R.id.btn_registrar);
        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarregistro();
            }
        });
    }
    public void ejecutarregistro() {


        final String cedula = edt_cedula.getText().toString().trim();
        final String usuario_new = edt_usuario.getText().toString().trim();
        final String correo = edt_correo.getText().toString().trim();
        final String nombre = edt_nombre.getText().toString().trim();
        final String apellido = edt_apellido.getText().toString().trim();
        final String pass = edt_contrasena1.getText().toString().trim();
        final String pass2 = edt_contrasena2.getText().toString().trim();
        final String direccion = edt_direccion.getText().toString().trim();
        final String ciudad = edt_ciudad.getText().toString().trim();
        final String telefono = edt_telefono.getText().toString().trim();
        final String fcha = fecha.trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        if (usuario_new.isEmpty()) {
            edt_usuario.setError("complete los campos");
        } else if (correo.isEmpty()) {
            edt_correo.setError("complete los campos");
        } else if (pass.isEmpty()) {
            edt_contrasena1.setError("complete los campos");
        } else if (pass2.isEmpty()) {
            edt_contrasena2.setError("complete los campos");
        }  else if (nombre.isEmpty()) {
            edt_nombre.setError("complete los campos");
        } else if (apellido.isEmpty()) {
            edt_apellido.setError("complete los campos");
        } else if (cedula.isEmpty()) {
            edt_cedula.setError("complete los campos");
        }  else if (ciudad.isEmpty()) {
            edt_ciudad.setError("complete los campos");
        } else if (direccion.isEmpty()) {
            edt_direccion.setError("complete los campos");
        } else if (telefono.isEmpty()) {
            edt_telefono.setError("complete los campos");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.marlonmym.tk/chulki/add_us.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("El usuario  se guardo con exito")){
                        Toast.makeText(getApplicationContext(), "El usuario  se guardo con exito", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else {
                        Toast.makeText(getApplicationContext(), response , Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams()throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("cedula_us",cedula);
                    parametros.put("nombre_us",nombre);
                    parametros.put("apellidos_us",apellido);
                    parametros.put("correo.us",correo);
                    parametros.put("usuario_us",usuario_new);
                    parametros.put("contrasena_us", pass);
                    parametros.put("telefono_us",telefono);
                    parametros.put("direccion_us",direccion);
                    parametros.put("ciudad_us",ciudad);
                    parametros.put("fecha_inicio_us",fcha);

                    return parametros;
                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(nuevo_usuario.this);
            requestQueue.add(stringRequest);


        }


    }

}
