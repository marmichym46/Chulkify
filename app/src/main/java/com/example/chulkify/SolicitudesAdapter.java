package com.example.chulkify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.solicitudesholder> {

    List<Solicitudes> listSolicitudes;
    Context context;
    Fragment_soli f;


    public SolicitudesAdapter(List<Solicitudes> listSolicitudes, Context context, Fragment_soli f){
        this.listSolicitudes = listSolicitudes;
        this.context = context;
        this.f = f;
    }

    @NonNull
    @Override
    public solicitudesholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_solicitudes,parent,false);
        return new SolicitudesAdapter.solicitudesholder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull solicitudesholder holder, final int position) {
        holder.nonbre_us.setText(listSolicitudes.get(position).getNombre_us());
        holder.apellido_us.setText(listSolicitudes.get(position).getApellido_us());
        holder.nombre_lis.setText(listSolicitudes.get(position).getUsuario_us());
        holder.fecha_crea.setText(listSolicitudes.get(position).getFecha_crea_notif());
        holder.identificador=listSolicitudes.get(position).getId_notif();

       holder.cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f.cancelarSolicitud(listSolicitudes.get(position).getId_notif(),listSolicitudes.get(position).getUsuario_us());
            }
        });

        holder.aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f.aceptarSolicitud(listSolicitudes.get(position).getId_notif(),listSolicitudes.get(position).getUsuario_us() );
            }
        });




    }

    @Override
    public int getItemCount() {
        return listSolicitudes.size();
    }



    public  class solicitudesholder extends RecyclerView.ViewHolder{
        private TextView nombre_lis,fecha_crea,nonbre_us,apellido_us;
        private Button aceptar;
        private Button cancelar;
        private CardView cardView;
        private int identificador=5;

        public solicitudesholder(@NonNull View itemView) {
            super(itemView);
            nonbre_us = (TextView) itemView.findViewById(R.id.txt_n_nombre_soli);
            apellido_us = (TextView) itemView.findViewById(R.id.txt_a_nombre_soli);
            nombre_lis = (TextView) itemView.findViewById(R.id.txt_nombre_soli);
            fecha_crea = (TextView) itemView.findViewById(R.id.txt_n_fecha_soli);
            aceptar =(Button) itemView.findViewById(R.id.btn_acep);
            cancelar=(Button) itemView.findViewById(R.id.btn_rech);


        }
    }

}
