package com.example.sapia.segundoparcial.database;

/**
 * Created by Sapia on 10/11/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

import com.example.sapia.segundoparcial.model.Look;
import com.example.sapia.segundoparcial.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import static com.example.sapia.segundoparcial.database.DBConfig.CONTRASEÑA_USUARIO;
import static com.example.sapia.segundoparcial.database.DBConfig.DATABASE_NAME;
import static com.example.sapia.segundoparcial.database.DBConfig.DB_VERSION;
import static com.example.sapia.segundoparcial.database.DBConfig.ID_USUARIO;
import static com.example.sapia.segundoparcial.database.DBConfig.IMAGEN_INFERIOR;
import static com.example.sapia.segundoparcial.database.DBConfig.IMAGEN_SUPERIOR;
import static com.example.sapia.segundoparcial.database.DBConfig.NOMBRE_USUARIO;
import static com.example.sapia.segundoparcial.database.DBConfig.PRENDA_SUPERIOR;
import static com.example.sapia.segundoparcial.database.DBConfig.PRENDA_INFERIOR;
import static com.example.sapia.segundoparcial.database.DBConfig.NOMBRE_LOOK;
import static com.example.sapia.segundoparcial.database.DBConfig.TABLA_LOOKS;
import static com.example.sapia.segundoparcial.database.DBConfig.TABLA_USUARIOS;

/**
 * Created by educacionit on 23/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLA_LOOKS + " (id integer primary key, "
                + NOMBRE_LOOK + " text, " + PRENDA_SUPERIOR + " text, " + IMAGEN_SUPERIOR + " integer, "
                + PRENDA_INFERIOR + " text , " + IMAGEN_INFERIOR + " integer, " + ID_USUARIO + " integer )");

        db.execSQL("create table if not exists " + TABLA_USUARIOS + " (id integer primary key, "
                + NOMBRE_USUARIO + " text, " + CONTRASEÑA_USUARIO + " text )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean insertarLook(Look look) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBRE_LOOK, look.getNombre());
            contentValues.put(PRENDA_SUPERIOR, look.getPrendaSuperior());
            contentValues.put(IMAGEN_SUPERIOR, look.getImagenSuperior());
            contentValues.put(PRENDA_INFERIOR, look.getPrendaInferior());
            contentValues.put(IMAGEN_INFERIOR, look.getImagenInferior());
            contentValues.put(ID_USUARIO, look.getIdUsuario());
            db.insert(TABLA_LOOKS, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBRE_USUARIO, usuario.getNombre());
            contentValues.put(CONTRASEÑA_USUARIO, usuario.getContraseña());
            db.insert(TABLA_USUARIOS, null, contentValues);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarLook(Look look) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
            contentValues.put(NOMBRE_LOOK, look.getNombre());
            contentValues.put(PRENDA_SUPERIOR, look.getPrendaSuperior());
            contentValues.put(IMAGEN_SUPERIOR, look.getImagenSuperior());
            contentValues.put(PRENDA_INFERIOR, look.getPrendaInferior());
            contentValues.put(IMAGEN_INFERIOR, look.getImagenInferior());
            contentValues.put(ID_USUARIO, look.getIdUsuario());
            db.update(TABLA_LOOKS, contentValues, "id = ?", new String[]{String.valueOf(look.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean borrarLook(Look look) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLA_LOOKS, "id = ?", new String[]{String.valueOf(look.getId())});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Look> obtenerLooks(int IdUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Look> looks = new ArrayList<>();
        Cursor cur = db.query(TABLA_LOOKS, null, " idusuario = ? ", new String[]{String.valueOf(IdUsuario)}, null, null, null);
        cur.moveToFirst();
        try {
            while (!cur.isAfterLast()) {
                Look look = new Look();
                look.setId(cur.getInt(cur.getColumnIndex("id")));
                look.setNombre(cur.getString(cur.getColumnIndex(NOMBRE_LOOK)));
                look.setPrendaSuperior(cur.getString(cur.getColumnIndex(PRENDA_SUPERIOR)));
                look.setImagenSuperior(cur.getInt(cur.getColumnIndex(IMAGEN_SUPERIOR)));
                look.setPrendaInferior(cur.getString(cur.getColumnIndex(PRENDA_INFERIOR)));
                look.setImagenInferior(cur.getInt(cur.getColumnIndex(IMAGEN_INFERIOR)));
                look.setIdUsuario(cur.getInt(cur.getColumnIndex(ID_USUARIO)));

                looks.add(look);
                cur.moveToNext();
            }
            return looks;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Look obtenerLook(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLA_LOOKS, null, " id = ? ", new String[]{String.valueOf(id)}, null, null, null);
        try {
            if (cur.moveToFirst()) {
                Look look = new Look();
                look.setId(cur.getInt(cur.getColumnIndex("id")));
                look.setNombre(cur.getString(cur.getColumnIndex(NOMBRE_LOOK)));
                look.setPrendaSuperior(cur.getString(cur.getColumnIndex(PRENDA_SUPERIOR)));
                look.setImagenSuperior(cur.getInt(cur.getColumnIndex(IMAGEN_SUPERIOR)));
                look.setPrendaInferior(cur.getString(cur.getColumnIndex(PRENDA_INFERIOR)));
                look.setImagenInferior(cur.getInt(cur.getColumnIndex(IMAGEN_INFERIOR)));
                look.setIdUsuario(cur.getInt(cur.getColumnIndex(ID_USUARIO)));

                return look;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }

    public int ComprobarNombre(String nombre) {
        int valor = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLA_USUARIOS, null, " nombre = ? ", new String[]{nombre}, null, null, null);

        try {
            if (cur.moveToFirst()) {

                Boolean NombreDB;

                NombreDB = cur.getString(cur.getColumnIndex(NOMBRE_USUARIO)).equals(nombre);


                if (NombreDB == true) {
                    valor = 0; //Usuario correcto.
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            valor = -1; //ERROR.
        } finally {
            db.close();
        }
        return valor;
    }

    public int ValidarUsuario(String nombre, String contraseña) {
        int valor = -2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLA_USUARIOS, null, " nombre = ? ", new String[]{nombre}, null, null, null);
        try {
            if (cur.moveToFirst()) {

                Boolean NombreDB, ContraseñaDB;

                NombreDB = cur.getString(cur.getColumnIndex(NOMBRE_USUARIO)).equals(nombre);
                ContraseñaDB = cur.getString(cur.getColumnIndex(CONTRASEÑA_USUARIO)).equals(contraseña);


                if (NombreDB == true) {
                    valor = 0; //Usuario correcto.
                    if (ContraseñaDB == true)
                        valor =1; //Usuario y contraseña correcto.
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            valor = -1; //ERROR.
        } finally {
            db.close();
        }
        return valor;
    }


    public int obtenerIDUsuario(String nombre) {

        int IdUsuario = -1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLA_USUARIOS, null, " nombre = ? ", new String[]{nombre}, null, null, null);

        try {
            if (cur.moveToFirst()) {
                IdUsuario = cur.getInt(cur.getColumnIndex("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            IdUsuario = -1; //ERROR.
        } finally {
            db.close();
        }
        return IdUsuario;
    }


}