package com.example.chulkify.prestamos.historial_prestamos;

public class Prestamos{
   private int id_cuota;
    private String valor_cuota;
    private String valor_pago;
    private String fecha_fin_plazo;
    private String fecha_ini_plazo;
    private String num_cuota;
    private String estado_cuota;


    public Prestamos(int id_cuota, String valor_cuota, String valor_pago, String fecha_fin_plazo, String fecha_ini_plazo, String num_cuota, String estado_cuota) {
        this.id_cuota = id_cuota;
        this.valor_cuota = valor_cuota;
        this.valor_pago = valor_pago;
        this.fecha_fin_plazo = fecha_fin_plazo;
        this.fecha_ini_plazo = fecha_ini_plazo;
        this.num_cuota = num_cuota;
        this.estado_cuota = estado_cuota;
    }


    public void setId_cuota(int id_cuota) {
        this.id_cuota = id_cuota;
    }

    public void setValor_cuota(String valor_cuota) {
        this.valor_cuota = valor_cuota;
    }

    public void setValor_pago(String valor_pago) {
        this.valor_pago = valor_pago;
    }

    public void setFecha_fin_plazo(String fecha_fin_plazo) {
        this.fecha_fin_plazo = fecha_fin_plazo;
    }

    public void setFecha_ini_plazo(String fecha_ini_plazo) {
        this.fecha_ini_plazo = fecha_ini_plazo;
    }

    public void setNum_cuota(String num_cuota) {
        this.num_cuota = num_cuota;
    }

    public void setEstado_cuota(String estado_cuota) {
        this.estado_cuota = estado_cuota;
    }

    public int getId_cuota() {
        return id_cuota;
    }

    public String getValor_cuota() {
        return valor_cuota;
    }

    public String getValor_pago() {
        return valor_pago;
    }

    public String getFecha_fin_plazo() {
        return fecha_fin_plazo;
    }

    public String getFecha_ini_plazo() {
        return fecha_ini_plazo;
    }

    public String getNum_cuota() {
        return num_cuota;
    }

    public String getEstado_cuota() {
        return estado_cuota;
    }
}
