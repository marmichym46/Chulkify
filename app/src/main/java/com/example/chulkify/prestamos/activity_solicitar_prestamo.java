package com.example.chulkify.prestamos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chulkify.Manejo_fechas;
import com.example.chulkify.R;
import com.example.chulkify.transacciones_pg.aportes.Aportar;

public class activity_solicitar_prestamo extends AppCompatActivity {

    private SharedPreferences preferences;

    private TextView valor_prestar, interes, diferido, valor_cuota,total_prestamo, fecha_limite;
    private EditText val_prestamo;
    private Button btn_actualizar, btn_cargar_datos, btn_soli_prestamo;
    private Spinner meses;
    private String fecha1, fecha2, hora1;
    private LinearLayout tarjeta1;

    //Variables para capturar preferencias
    private String ci_us_1, gp_1, cg_gp_1, taza,hoy_tran, maximo;


    //variables para capturar fecha
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_prestamo);

        valor_prestar=findViewById(R.id.txt_v_prestar);
        interes=findViewById(R.id.txt_v_interes);
        diferido=findViewById(R.id.txt_diferido);
        valor_cuota=findViewById(R.id.txt_v_cuota);
        total_prestamo=findViewById(R.id.txt_total_prestamo);
        fecha_limite=findViewById(R.id.txt_f_limite);
        btn_actualizar=findViewById(R.id.btn_refres);
        btn_cargar_datos=findViewById(R.id.btn_cargar_prestamo);
        btn_soli_prestamo=findViewById(R.id.btn_soli_prestamo);
        meses=findViewById(R.id.smeses);

        //capturar preferencias
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        ci_us_1=preferences.getString("cedula_usuario", null);
        gp_1=preferences.getString("nombre_comu", null);
        cg_gp_1=preferences.getString("codigo_comu", null);
        taza=preferences.getString("taza", null);
        hoy_tran=preferences.getString("aportes_hoy", null);
        maximo=preferences.getString("maximo", null);

        Manejo_fechas mf= new Manejo_fechas();

        //captura la fecha
        fecha1=mf.fecha_actual();
        fecha2=mf.fechaYhora_actual();

        btn_cargar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double val11 =Double.parseDouble(valor_prestar.toString());
                Double val21 =Double.parseDouble(meses.getSelectedItem().toString());
                Double val31 = 0.05;

                Double interes= val11*val31;
                Double subtotal= val11+interes;
                Double auxiliar= subtotal/val21;
                Double val_cuota= Math.round(auxiliar*100.0)/100.0;

                Double total= val_cuota*val21;



            }

        });



    }
}