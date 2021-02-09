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
import com.example.chulkify.inicio;
import com.example.chulkify.transacciones_pg.aportes.Aportar;

public class activity_solicitar_prestamo extends AppCompatActivity {

    private SharedPreferences preferences;

    private TextView valor_prestar, interes, diferido, valor_cuota,total_prestamo, fecha_limite;
    private EditText val_prestamo;
    private Button btn_actualizar, btn_cargar_datos, btn_soli_prestamo;
    private Spinner meses;
    private String fecha1, fecha2, hora1;
    private LinearLayout tarjeta1;
    private String fondo_us, fondo_comu;
    private double fondo_us2, fondo_comu2;
    private  int fd_us, fd_comu;

    //Variables para capturar preferencias
    private String ci_us_1, gp_1, cg_gp_1, taza,hoy_tran, maximo;


    //variables para capturar fecha
    private int  dia, mes, anio, hora, minutos, segundos;
    private String  diaS, mesS, anioS,horaS, minutosS, segundosS, minutosa;
    String valor_max;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_prestamo);

        val_prestamo=findViewById(R.id.edt_valor_prestamo);
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
        fondo_us=preferences.getString("fondos_usuario", null);
        fondo_comu=preferences.getString("fondos_comunidad", null);

        fondo_us2=(Double.parseDouble(fondo_us))*2;
        fondo_comu2=Double.parseDouble(fondo_comu);
        /*String fd_us2=String.valueOf(fondo_us2);
        String [] fd_us_aux=fd_us2.split(".");
        //String fd_us3=fd_us_aux[0]);

         */
        //fd_us=convertir_dooble(fondo_us2);
        //Toast.makeText(activity_solicitar_prestamo.this, fd_us, Toast.LENGTH_SHORT).show();
/*

        fd_us=Integer.parseInt(String.valueOf(fondo_us2));
        fondo_comu2=Math.floor(Double.parseDouble(fondo_comu));
        fd_comu=Integer.parseInt(String.valueOf(fondo_comu2));
        Toast.makeText(activity_solicitar_prestamo.this, fd_us, Toast.LENGTH_SHORT).show();



 */
        if (fondo_us2 > fondo_comu2){
            valor_max=String.valueOf(fondo_comu2);
            val_prestamo.setText(valor_max);
            //Toast.makeText(activity_solicitar_prestamo.this, "el fondo del usuario es mayor", Toast.LENGTH_SHORT).show();
        }else if (fondo_comu2 >= fondo_us2){
            valor_max=String.valueOf(fondo_us2);
            val_prestamo.setText(valor_max);
            //Toast.makeText(activity_solicitar_prestamo.this, "el fondo de la comunidad es mayor", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(activity_solicitar_prestamo.this, "error", Toast.LENGTH_SHORT).show();

        }

        Manejo_fechas mf= new Manejo_fechas();
        //captura la fecha
        fecha1=mf.fecha_actual();
        fecha2=mf.fechaYhora_actual();

       //if (fondo_us > fondo_comu)

        btn_cargar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double valprestar =Double.parseDouble(val_prestamo.toString());
                Double valmaximo_p =Double.parseDouble(valor_max);

                /*
                Double val31 = 0.05;

                Double interes= val11*val31;
                Double subtotal= val11+interes;
                Double auxiliar= subtotal/val21;
                Double val_cuota= Math.round(auxiliar*100.0)/100.0;

                Double total= val_cuota*val21;

                 */

                if (val_prestamo.getText().toString().isEmpty()) {
                    Toast.makeText(activity_solicitar_prestamo.this, "Hay campos en blanco ", Toast.LENGTH_SHORT).show();
                } else {
                    //if (valprestar > valmaximo_p)

                }


            }

        });



    }

    public static int convertir_dooble(double num){
        String strNum=num+"";
        int indice=strNum.indexOf(".");
        return Integer.parseInt(strNum.substring(0,  indice));
    }



}