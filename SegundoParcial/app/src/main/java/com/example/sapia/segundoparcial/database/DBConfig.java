package com.example.sapia.segundoparcial.database;

/**
 * Created by Sapia on 10/11/2017.
 */

public interface DBConfig {
    String DATABASE_NAME = "FINAL";
    int DB_VERSION = 1;
    String TABLA_LOOKS = "Looks";
    String NOMBRE_LOOK = "Nombre";
    String PRENDA_SUPERIOR = "PrendaSuperior";
    String IMAGEN_SUPERIOR = "ImagenSuperior";
    String PRENDA_INFERIOR = "PrendaInferior";
    String IMAGEN_INFERIOR = "ImagenInferior";
    String ID_USUARIO = "IdUsuario";

    String TABLA_USUARIOS = "Usuarios";
    String NOMBRE_USUARIO = "Nombre";
    String CONTRASEÑA_USUARIO = "Contraseña";
}
