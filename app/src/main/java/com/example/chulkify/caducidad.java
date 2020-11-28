package com.example.chulkify;

import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class caducidad {

    private SharedPreferences preferences;
    private int dia, mes,anio,hora, minutos, segundos;
    private String diaS, mesS, anioS, fecha, horaS, minutosS, segundosS;
    public caducidad(){}
    public void gene_caducidad(){
        Calendar fecha_a = Calendar.getInstance();
        Date date = new Date();

        dia= fecha_a.get(Calendar.DAY_OF_MONTH);
        mes= fecha_a.get(Calendar.MONTH)+1;
        anio= fecha_a.get(Calendar.YEAR);

        diaS= String.valueOf(dia);
        mesS = String.valueOf(mes);
        anioS = String.valueOf(anio);
        fecha = diaS+"/"+mesS+"/"+anioS;

        SimpleDateFormat h= new SimpleDateFormat("kk");
        horaS=h.format(date);
        SimpleDateFormat hh = new SimpleDateFormat("k");
        hora=Integer.parseInt(hh.format(date));


        SimpleDateFormat m= new SimpleDateFormat("mm");
        minutosS=m.format(date);
        SimpleDateFormat mm= new SimpleDateFormat("m");
        minutos=Integer.parseInt(mm.format(date));


        SimpleDateFormat s= new SimpleDateFormat("ss");
        segundosS=s.format(date);
        SimpleDateFormat ss= new SimpleDateFormat("s");
        segundos=Integer.parseInt(ss.format(date));






        if ((anio%4)==0){
            if (mes == 1){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 2){
                if ((dia <= 27) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 28){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 3){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 4){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 5){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 6){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 7){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 8){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 9){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 10){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 11){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 12){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=1;
                    dia=1;
                    anio=anio+1;
                }
                else if (dia == 31){
                    mes=1;
                    dia=2;
                    anio=anio+1;
                }
            }
        }
        else {
            if (mes == 1){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 2){
                if ((dia <= 26) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 27){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 28){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 3){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 4){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 5){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 6){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 7){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 8){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 9){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 10){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 31){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 11){
                if ((dia <= 28) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 29){
                    mes=mes+1;
                    dia=1;
                }
                else if (dia == 30){
                    mes=mes+1;
                    dia=2;
                }
            }
            else if (mes == 12){
                if ((dia <= 29) && (dia >= 1)){
                    dia=dia+2;
                }
                else if (dia == 30){
                    mes=1;
                    dia=1;
                    anio=anio+1;
                }
                else if (dia == 31){
                    mes=1;
                    dia=2;
                    anio=anio+1;
                }
            }
        }


        String aaaa= String.valueOf(anio);
        String mmmm= String.valueOf(mes);
        String dddd= String.valueOf(dia);
        String hhhh= String.valueOf(hora);
        String mnt= String.valueOf(minutos);
        String ssss= String.valueOf(segundos);

        fecha = diaS+"/"+mesS+"/"+anioS+" - "+horaS+":"+minutosS+":"+segundosS+" -/- "+dddd+"/"+mmmm+"/"+aaaa+" - "+hhhh+":"+mnt+":"+ssss;

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("fecha_actual", fecha);
        editor.putInt("anio_cadu", anio);
        editor.putInt("mes_cadu", mes);
        editor.putInt("dia_cadu", dia);
        editor.putInt("hora_cadu", hora);
        editor.putInt("minuto_cadu", minutos);
        editor.putInt("segundo_cadu", segundos);

        editor.apply();


    }
}
