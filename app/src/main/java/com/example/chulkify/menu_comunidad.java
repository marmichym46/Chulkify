package com.example.chulkify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class menu_comunidad extends AppCompatActivity {

    private SharedPreferences preferences;
    private TextView nombre_comu, fecha_comu, mv_usuario, tv_codigo,tv_t_us;
    private String usuario, fecha_m;
    private String codg_comu, city_comu,  nom_comu;
    private AsyncHttpClient comu_clien;
    private  int id_comu;
    private String nomb,most_fecha, n_comu, m_usuario, conv_total_us,total_us_comu;
    private Button btn_actualizar;

    private String fecha;
    private int  dia, mes, anio;
    private int  diaS, mesS, anioS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_comunidad);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("cedula_usuario", null);
        n_comu = preferences.getString("nombre_comu", null);
        fecha_m = preferences.getString("fecha_union_grupo", null);
        m_usuario = preferences.getString("nombre_usuario" , null);
        codg_comu = preferences.getString("codigo_comu",null);
        total_us_comu= String.valueOf(preferences.getInt("total_usuario_comu",0));


        nombre_comu=findViewById(R.id.tv_comunidad_m);
        fecha_comu=findViewById(R.id.tv_fecha);
        mv_usuario=findViewById(R.id.txt_usuario_us);
        tv_codigo =findViewById(R.id.tv_codigo);
        tv_t_us   =findViewById(R.id.tv_t_us);


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
        tv_codigo.setText(codg_comu);
        tv_t_us.setText(total_us_comu);
    }


}