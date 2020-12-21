package com.example.chulkify;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class Fratments_solicitudes extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

     RecyclerView recyclersolicitudes;
    //private SolicitudesAdapter adapter;
    ArrayList<Solicitudes> listasolicitudes;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    ProgressDialog dialog;

    private SharedPreferences preferences;
    private String n_usuario="null";
    private String usuario, usu_soli;
    private AsyncHttpClient buscar_usuario_bd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_solicitudes_1);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("cedula_usuario", null);




        //Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_LONG).show();

        listasolicitudes=new ArrayList<>();
        recyclersolicitudes = (RecyclerView) findViewById(R.id.rcw_solicitudes);
        recyclersolicitudes.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclersolicitudes.setHasFixedSize(true);

        request= Volley.newRequestQueue(getApplicationContext());
        buscar_usuario_bd =new AsyncHttpClient();
        cargarservice();
    }

    public void cargarservice(){
        String cog_comu = usuario.replace(" ", "%20");
        String url="http://www.marlonmym.tk/chulki/consulta_solicitudes/capturar_solicitudes.php?ci_us="+cog_comu;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se puede conectar" +error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {
        Solicitudes centrousu=null;

        JSONArray json=response.optJSONArray("solicitud");
        try {
            for(int i=0;i<json.length();i++){}


            for (int i=0;i<json.length();i++){
                centrousu=new Solicitudes();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);
                centrousu.setNombre_us(jsonObject.optString("10"));
                centrousu.setApellido_us(jsonObject.optString("11"));

                String dt=jsonObject.optString("9");
                String aux="("+dt+")";
                centrousu.setUsuario_us(aux);

                String fch=jsonObject.optString("fecha_crea_notif");
                String[] parts = fch.split("/");
                String fecha=parts[0]+"/"+parts[1]+"/"+parts[2]+"   "+parts[3]+":"+parts[4]+":"+parts[5];
                centrousu.setFecha_crea_notif(fecha);
                listasolicitudes.add(centrousu);
            }
            SolicitudesAdapter adapter=new SolicitudesAdapter(listasolicitudes, getApplicationContext());
            recyclersolicitudes.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
        }

    }

}