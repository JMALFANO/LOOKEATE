package com.example.sapia.segundoparcial.model;

/**
 * Created by Sapia on 10/11/2017.
 */
public class Look {

    private int id;
    private String nombre;
    private String prendasuperior;
    private int imagensuperior;
    private String prendainferior;
    private int imageninferior;
    private int idusuario;

    public Look() {
    }

    public Look(int id, String nombre, String prendasuperior, int imagensuperior, String prendainferior, int imageninferior, int idusuario) {
        this.id = id;
        this.nombre = nombre;
        this.prendasuperior = prendasuperior;
        this.imagensuperior=imagensuperior;
        this.prendainferior = prendainferior;
        this.imageninferior = imageninferior;
        this.idusuario = idusuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrendaSuperior() {
        return prendasuperior;
    }

    public int getImagenSuperior() {
        return imagensuperior;
    }

    public void setPrendaSuperior(String prendasuperior) { this.prendasuperior = prendasuperior; }

    public void setImagenSuperior(int imagensuperior) { this.imagensuperior = imagensuperior; }

    public String getPrendaInferior() {
        return prendainferior;
    }

    public int getImagenInferior() {   return imageninferior;    }

    public void setPrendaInferior(String prendainferior) { this.prendainferior =prendainferior; }

    public void setImagenInferior(int imageninferior) { this.imageninferior =imageninferior; }

    public int getIdUsuario() {
        return idusuario;
    }

    public void setIdUsuario(int idusuario) { this.idusuario =idusuario; }

}
