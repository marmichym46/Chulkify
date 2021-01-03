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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_soli extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    private RecyclerView rv;
    private SolicitudesAdapter adapter;
    private List<Solicitudes> listSolicitudes;
    private LinearLayout layoutSinSolicitudes;
    //private EventBus bus = EventBus.getDefault();

    //ArrayList<Solicitudes> listasolicitudes;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    ProgressDialog dialog;

    public SharedPreferences preferences;
    private String n_usuario="null";
    private String usuario, usu_soli;
    private AsyncHttpClient buscar_usuario_bd;



    private String fechacrea, fechacadu;
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa, usuario2;
    private int minutos_aux;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_soli, container, false);
       listSolicitudes = new ArrayList<>();

       rv = (RecyclerView) v.findViewById(R.id.rcw_solicitudes);
       layoutSinSolicitudes = (LinearLayout)v.findViewById(R.id.layout_vacio_vicivility);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new SolicitudesAdapter(listSolicitudes,getContext(),this);
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
        Toast.makeText(getContext(), "No hay solicitudes" , Toast.LENGTH_LONG).show();

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
                int  id_us=jsonObject.optInt("id_notif");
                String id_usuario = String.valueOf(id_us);


               //Toast.makeText(getContext(), id_usuario, Toast.LENGTH_SHORT).show();
                agregarTarjetasDeSolicitud(nnn,aaa,aux,fecha,id_us);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
        }

    }



    public  void verificar_solicitudes(){
        if (listSolicitudes.isEmpty()){
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
        listSolicitudes.add(0,solicitudes);
        actualizarTarjetas();
    }
    public void agregarTarjetasDeSolicitud(Solicitudes solicitudes){
        listSolicitudes.add(0,solicitudes);
        actualizarTarjetas();
    }


    public void actualizarTarjetas(){
        adapter.notifyDataSetChanged();
        verificar_solicitudes();
    }

    public void eliminarTarjeta(int id){
        //int id_t= Integer.p
        for(int i=0;i<listSolicitudes.size();i++){
            if(listSolicitudes.get(i).getId_notif()==id){
                listSolicitudes.remove(i);
                actualizarTarjetas();
            }
        }
    }



    public void aceptarSolicitud(final int identificador, final String usuario_soli){


        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();

        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        diaS= String.valueOf(dia);
        mesS = String.valueOf(mes);
        anioS = String.valueOf(anio);


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

        fechacrea = diaS+"/"+mesS+"/"+anioS+"/"+horaS+"/"+minutosS+"/"+segundosS;





        AsyncHttpClient aceptar  = new AsyncHttpClient();
        final int ident=identificador;
        String idt=String.valueOf(ident);
        String url = "http://www.marlonmym.tk/chulki/consulta_solicitudes/aceptar.php?id="+ident+"&fe="+fechacrea;
        aceptar.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(getContext(), "Error...!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {

                            JSONObject jsonObj = new JSONObject(respuesta);
                            String resp = jsonObj.getString("dato");

                            if (resp.equals("en_espera")){
                                Toast.makeText(getContext(), "Has aceptado la solicitud del usuario "+usuario_soli+", el usuario aun tiene solicitudes pendientes", Toast.LENGTH_SHORT).show();
                            }
                            else if (resp.equals("aceptado")){
                                Toast.makeText(getContext(), "Has aceptado la solicitud del usuario "+usuario_soli+", el usuario ya es integraante de tu comunidad...!", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(getContext(), "Error...!", Toast.LENGTH_SHORT).show();

                            }
                            eliminarTarjeta(ident);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();


            }


        });


    }


    public void cancelarSolicitud(final int identificador, final String usuario_soli){
        AsyncHttpClient rechazar  = new AsyncHttpClient();
        final int ident=identificador;
        String idt=String.valueOf(ident);
        String url = "http://www.marlonmym.tk/chulki/consulta_solicitudes/rechazar.php?id="+ident;
        rechazar.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String respuesta = new String(responseBody);
                    if (respuesta.equalsIgnoreCase("null")) {
                        Toast.makeText(getContext(), "Error...!!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            JSONObject jsonObj = new JSONObject(respuesta);
                            String resp = jsonObj.getString("dato");
                            if (resp.equals("rechazado")){
                                Toast.makeText(getContext(), "Has rechazado la solicitud del usuario "+usuario_soli+" de tu comunidad", Toast.LENGTH_SHORT).show();
                            }
                            else if (resp.equals("error")){
                                Toast.makeText(getContext(), "Error...!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "Error...!", Toast.LENGTH_SHORT).show();
                            }
                            eliminarTarjeta(ident);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } } } }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Error Desconocido. Intentelo De Nuevo!!"+responseBody, Toast.LENGTH_SHORT).show();
            }}); }






}