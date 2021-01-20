package com.example.chulkify.envio_solicitud_comu.solicitudes;

public class Solicitudes {
    private int id_notif;
    private String ci_us_notif;
    private String ci_soli_env_notif;
    private String codigo_comu_notif;
    private int estado_notif;
    private int estado_general_notif;
    private int tipo_notif;
    private String fecha_crea_notif;
    private String fecha_cadu_notif;
    private String nombre_us;
    private String apellido_us;
    private String usuario_us;

    public Solicitudes(){}

    public Solicitudes(int id_notif, String ci_us_notif, String ci_soli_env_notif, String codigo_comu_notif, int estado_notif, int estado_general_notif, int tipo_notif, String fecha_crea_notif, String fecha_cadu_notif, String nombre_us, String apellido_us, String usuario_us) {
        this.id_notif = id_notif;
        this.ci_us_notif = ci_us_notif;
        this.ci_soli_env_notif = ci_soli_env_notif;
        this.codigo_comu_notif = codigo_comu_notif;
        this.estado_notif = estado_notif;
        this.estado_general_notif = estado_general_notif;
        this.tipo_notif = tipo_notif;
        this.fecha_crea_notif = fecha_crea_notif;
        this.fecha_cadu_notif = fecha_cadu_notif;
        this.nombre_us = nombre_us;
        this.apellido_us = apellido_us;
        this.usuario_us = usuario_us;
    }

    public int getId_notif() {
        return id_notif;
    }

    public void setId_notif(int id_notif) {
        this.id_notif = id_notif;
    }

    public String getCi_us_notif() {
        return ci_us_notif;
    }

    public void setCi_us_notif(String ci_us_notif) {
        this.ci_us_notif = ci_us_notif;
    }

    public String getCi_soli_env_notif() {
        return ci_soli_env_notif;
    }

    public void setCi_soli_env_notif(String ci_soli_env_notif) {
        this.ci_soli_env_notif = ci_soli_env_notif;
    }

    public String getCodigo_comu_notif() {
        return codigo_comu_notif;
    }

    public void setCodigo_comu_notif(String codigo_comu_notif) {
        this.codigo_comu_notif = codigo_comu_notif;
    }

    public int getEstado_notif() {
        return estado_notif;
    }

    public void setEstado_notif(int estado_notif) {
        this.estado_notif = estado_notif;
    }

    public int getEstado_general_notif() {
        return estado_general_notif;
    }

    public void setEstado_general_notif(int estado_general_notif) {
        this.estado_general_notif = estado_general_notif;
    }

    public int getTipo_notif() {
        return tipo_notif;
    }

    public void setTipo_notif(int tipo_notif) {
        this.tipo_notif = tipo_notif;
    }

    public String getFecha_crea_notif() {
        return fecha_crea_notif;
    }

    public void setFecha_crea_notif(String fecha_crea_notif) {
        this.fecha_crea_notif = fecha_crea_notif;
    }

    public String getFecha_cadu_notif() {
        return fecha_cadu_notif;
    }

    public void setFecha_cadu_notif(String fecha_cadu_notif) {
        this.fecha_cadu_notif = fecha_cadu_notif;
    }

    public String getNombre_us() {
        return nombre_us;
    }

    public void setNombre_us(String nombre_us) {
        this.nombre_us = nombre_us;
    }

    public String getApellido_us() {
        return apellido_us;
    }

    public void setApellido_us(String apellido_us) {
        this.apellido_us = apellido_us;
    }

    public String getUsuario_us() {
        return usuario_us;
    }

    public void setUsuario_us(String usuario_us) {
        this.usuario_us = usuario_us;
    }
}
