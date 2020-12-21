package com.example.chulkify;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import static android.content.Context.MODE_PRIVATE;

public class Fragment_soli extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    private RecyclerView rv;
    private SolicitudesAdapter adapter;
    private List<Solicitudes> listasolicitudes;
    private LinearLayout layoutSinSolicitudes;
    //ArrayList<Solicitudes> listasolicitudes;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    ProgressDialog dialog;

    public SharedPreferences preferences;
    private String n_usuario="null";
    private String usuario, usu_soli;
    private AsyncHttpClient buscar_usuario_bd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_soli, container, false);
       listasolicitudes = new ArrayList<>();

       rv = (RecyclerView) v.findViewById(R.id.rcw_solicitudes);
       layoutSinSolicitudes = (LinearLayout)v.findViewById(R.id.layout_vacio_vicivility);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new SolicitudesAdapter(listasolicitudes,getContext());
        rv.setAdapter(adapter);

        preferences = getContext().getSharedPreferences("Preferences", MODE_PRIVATE);
        usuario = preferences.getString("cedula_usuario", null);
        request= Volley.newRequestQueue(getContext());
        buscar_usuario_bd =new AsyncHttpClient();
        cargarservice();

        verificar_solicitudes();

       return v;
    }
    public void cargarservice(){
        String cog_comu = usuario.replace(" ", "%20");
        String url="http://www.marlonmym.tk/chulki/consulta_solicitudes/capturar_solicitudes.php?ci_us="+cog_comu;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null, this,this);
        request.add(jsonObjectRequest);}

    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar" +error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());

    }

    public void onResponse(JSONObject response) {
        Solicitudes centrousu=null;

        JSONArray json=response.optJSONArray("solicitud");
        try {

            for (int i=0;i<json.length();i++){
                centrousu=new Solicitudes();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                String nnn=jsonObject.optString("10");
                String aaa=jsonObject.optString("11");

                String dt=jsonObject.optString("9");
                String aux="("+dt+")";

                String fch=jsonObject.optString("fecha_crea_notif");
                String[] parts = fch.split("/");
                String fecha=parts[0]+"/"+parts[1]+"/"+parts[2]+"   "+parts[3]+":"+parts[4]+":"+parts[5];
                int  id_us=jsonObject.optInt("id_us");
                //Toast.makeText(getContext(), fecha, Toast.LENGTH_SHORT).show();
                agregarTarjetasDeSolicitud(nnn,aaa,aux,fecha,id_us);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
        }

    }



    public  void verificar_solicitudes(){
        if (listasolicitudes.isEmpty()){
            layoutSinSolicitudes.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }
        else {
            layoutSinSolicitudes.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    public void agregarTarjetasDeSolicitud(String nombre_usu, String apellidi_usu, String usuario_usu, String fecha_crea,int id_us){

        Solicitudes solicitudes = new Solicitudes();
        solicitudes.setNombre_us(nombre_usu);
        solicitudes.setApellido_us(apellidi_usu);
        solicitudes.setUsuario_us(usuario_usu);
        solicitudes.setFecha_crea_notif(fecha_crea);
        solicitudes.setId_notif(id_us);
        listasolicitudes.add(solicitudes);
        actualizarTarjetas();
    }

    public void actualizarTarjetas(){
        adapter.notifyDataSetChanged();
        verificar_solicitudes();
    }






}