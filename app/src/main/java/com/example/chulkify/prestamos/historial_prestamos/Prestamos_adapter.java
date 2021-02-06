package com.example.chulkify.prestamos.historial_prestamos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chulkify.R;
import com.example.chulkify.transacciones_pg.reportes.Fragment_histo_tran;
import com.example.chulkify.transacciones_pg.reportes.Transacciones;
import com.example.chulkify.transacciones_pg.reportes.TransaccionesAdapter;

import java.util.List;

public class Prestamos_adapter extends RecyclerView.Adapter<Prestamos_adapter.prestamosholder> {

    List<Prestamos> listPrestamos;
    Context context;
    Fragment_histo_tran f;

    public Prestamos_adapter(List<Prestamos> listPrestamos, Context context, Fragment_historial_prestamo f){
        this.listPrestamos = listPrestamos;
        this.context = context;
        this.f = f;

    }

    @NonNull
    @Override
    public prestamosholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_prestamos,parent,false);
        return new Prestamos_adapter.prestamosholder(v);


    }


    @Override
    public void onBindViewHolder(@NonNull Prestamos_adapter.prestamosholder holder, final int position) {
        holder.num_cuota.setText(listPrestamos.get(position).getNum_cuota());
        holder.valor_cuota.setText(listPrestamos.get(position).getValor_cuota());
        holder.valor_pago.setText(listPrestamos.get(position).getValor_pago());
        holder.fecha_ini_plazo.setText(listPrestamos.get(position).getFecha_ini_plazo());
        holder.fecha_fin_plazo.setText(listPrestamos.get(position).getFecha_fin_plazo());
        holder.estado_cuota.setText(listPrestamos.get(position).getEstado_cuota());
        holder.identificador= listPrestamos.get(position).getId_cuota();
        /*if ((listtransacciones.get(position).getTipo_tran())=="+"){
            holder.tipo_tran.setTextColor(Integer.parseInt("@color/verde"));
        }else if ((listtransacciones.get(position).getTipo_tran())=="-"){
            holder.tipo_tran.setTextColor(Integer.parseInt("@color/rojo"));
        }

         */


    }

    @Override
    public int getItemCount() {
        return listPrestamos.size();
    }

    public  class prestamosholder extends RecyclerView.ViewHolder{
        private TextView num_cuota,valor_cuota, valor_pago, fecha_ini_plazo, fecha_fin_plazo, estado_cuota;
        private CardView cardView;
        private Button ver;
        private int identificador=0;


        public prestamosholder(@NonNull View itemView) {
            super(itemView);
            num_cuota= (TextView) itemView.findViewById(R.id.txt_n_cuotas);
            valor_cuota= (TextView) itemView.findViewById(R.id.txt_valor_cuota);
            valor_pago= (TextView) itemView.findViewById(R.id.txt_valor_prestamo);
            fecha_ini_plazo= (TextView) itemView.findViewById(R.id.txt_fecha_inicio);
            fecha_fin_plazo= (TextView) itemView.findViewById(R.id.txt_fecha_fin);
            estado_cuota= (TextView) itemView.findViewById(R.id.txt_estado_p);
            ver= (Button) itemView.findViewById(R.id.btn_ver_detalle);




        }
    }


}


