package com.example.chulkify;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class Logear_usuario {
    private String Ci;
    private String nombres;
    private String apellidos;
    private String nombre_usuario;
    private String correo;
    private String password;
    private Double fondos;
    private  int grupo, estado_grupo, id_cuenta, tipo;
    public Logear_usuario() {
    }
    public Logear_usuario(int id, String nombres, String apellidos, String nombre_usuario, String correo, String password, Double fondos, int grupo, int estado_grupo, int id_cuenta, int tipo) {
        this.Ci = Ci;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombre_usuario = nombre_usuario;
        this.correo = correo;
        this.password = password;
        this.fondos = fondos;
        this.grupo = grupo;
        this.estado_grupo = estado_grupo;
        this.id_cuenta = id_cuenta;
        this.tipo = tipo;
    }
    //Getter

    public String getCi() {
        return Ci;
    }
    public String getNombres() {
        return nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getNombre_usuario() {
        return nombre_usuario;
    }
    public String getCorreo() { return correo;  }
    public String getPassword() {
        return password;
    }


    public Double getFondos() {
        return fondos;
    }
    public int getGrupo() {
        return grupo;
    }
    public int getEstado_grupo() {
        return estado_grupo;
    }
    public int getId_cuenta() {
        return id_cuenta;
    }
    public int getTipo() {
        return id_cuenta;
    }

    //SETTER
    public void setCi(String Ci) {
        this.Ci = Ci;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setFondos(Double fondos) {
        this.fondos = fondos;
    }
    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }
    public void setEstado_grupo(int estado_grupo) { this.estado_grupo = estado_grupo;  }
    public void setId_cuenta(int id_cuenta) {
        this.id_cuenta = id_cuenta;
    }
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }






    public int describeContent(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.Ci);
        dest.writeString(this.nombres);
        dest.writeString(this.apellidos);
        dest.writeString(this.nombre_usuario);
        dest.writeString(this.correo);
        dest.writeString(this.password);
        dest.writeDouble(this.fondos);
        dest.writeInt(this.grupo);
        dest.writeInt(this.estado_grupo);
        dest.writeInt(this.id_cuenta);
        dest.writeInt(this.tipo);

    }

    protected Logear_usuario(Parcel in){
        this.Ci=in.readString();
        this.nombres=in.readString();
        this.apellidos=in.readString();
        this.nombre_usuario=in.readString();
        this.correo=in.readString();
        this.password=in.readString();

        this.fondos=in.readDouble();
        this.grupo=in.readInt();
        this.estado_grupo=in.readInt();
        this.id_cuenta=in.readInt();
        this.tipo=in.readInt();
    }

    public static final Parcelable.Creator<Logear_usuario> CREATOR = new Parcelable.ClassLoaderCreator<Logear_usuario>() {

        @Override
        public Logear_usuario createFromParcel(Parcel source, ClassLoader loader) {
            return null;
        }

        @Override
        public Logear_usuario createFromParcel(Parcel source) {
            return new Logear_usuario(source);
        }

        @Override
        public Logear_usuario[] newArray(int size) {
            return new Logear_usuario[size];
        }
    };

    public void setNombre(String usuario_us) {
    }

    public void setContraseña(String contrasena_us) {
    }
}
