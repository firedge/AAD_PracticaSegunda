package com.fdgproject.firedge.aad_practicasegunda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Firedge on 14/11/2014.
 */
public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "jutbol.sqlite";
    public static final int DATABASE_VERSION = 2;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql="create table "+Contrato.TablaJugador.TABLA+
                " ("+ Contrato.TablaJugador._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaJugador.NOMBRE+" text, "+
                Contrato.TablaJugador.TELEFONO+" text, "+
                Contrato.TablaJugador.FNAC+" text)";
        //create table jugador (_id integer primary key autoincrement, nombre text, telefono text, fnac text)
        Log.v("SQL", sql);
        db.execSQL(sql);

        sql="create table "+Contrato.TablaPartido.TABLA+
                " ("+ Contrato.TablaPartido._ID+
                " integer primary key autoincrement, "+
                Contrato.TablaPartido.IDJUGADOR+" integer, "+
                Contrato.TablaPartido.CONTRINCANTE+" text default inicial, "+
                Contrato.TablaPartido.VALORACION+" integer, "+
                "unique("+Contrato.TablaPartido.IDJUGADOR+", "+Contrato.TablaPartido.CONTRINCANTE+"))";
        //create table partido (_id integer primary key autoincrement, idjugador integer, contrincante text default inicial, valoracion integer, unique(idjugador, contrincante))
        Log.v("SQL", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        *  Aquí se transforman las tablas de la versión old a la new.
        *  1º Se crean tablas auxiliares, normalmente tienen que ser identicas a las anteriores.
        *  2º Copio los datos de las tablas actuales a las tablas auxiliares.
        *  3º Se borran las tablas originales.
        *  4º Se crean las nuevas tablas ya modificadas como queramos, se llama al onCreate
        *  5º Copiar los datos de las tablas auxiliares en las nuevas tablas.
        *  6º Borrar las tablas auxiliares.
        */

        String sql;

        //Crear tablas auxiliares
        sql="create table auxiliar"+Contrato.TablaJugador.TABLA+
                " ("+ Contrato.TablaJugador._ID+
                " integer primary key, "+
                Contrato.TablaJugador.NOMBRE+" text, "+
                Contrato.TablaJugador.TELEFONO+" text, "+
                Contrato.TablaJugador.VALORACION+" integer, "+
                Contrato.TablaJugador.FNAC+" text)";
        Log.v("SQL", sql);
        db.execSQL(sql);
        //create table auxiliarjugador (_id integer primary key, nombre text, telefono text, valoracion integer, fnac text)

        //Copiar datos
        sql = "insert into auxiliar"+Contrato.TablaJugador.TABLA+" select * from "+Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        //insert into auxiliarjugador select * from jugador

        //Eliminar la antigua tabla
        sql="drop table if exists "+ Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        //drop table if exists jugador

        //Crear nuevas tablas
        onCreate(db);
        //create table jugador (_id integer primary key autoincrement, nombre text, telefono text, fnac text)
        //create table partido (_id integer primary key autoincrement, idjugador integer, contrincante text default inicial, valoracion integer, unique(idjugador, contrincante))

        //Copiar datos a nuevas tablas
        sql = "insert into "+Contrato.TablaJugador.TABLA+" select "+
                Contrato.TablaJugador._ID+", "+
                Contrato.TablaJugador.NOMBRE+", "+
                Contrato.TablaJugador.TELEFONO+", "+
                Contrato.TablaJugador.FNAC+
                " from auxiliar"+Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        //Insertar los datos del partido -----VOY POR AQUI----
        sql = "insert into "+Contrato.TablaPartido.TABLA+" " +
                "("+Contrato.TablaPartido.IDJUGADOR+
                ", "+Contrato.TablaPartido.VALORACION+") select "+
                Contrato.TablaJugador._ID+", "+
                Contrato.TablaJugador.VALORACION+
                " from auxiliar"+Contrato.TablaJugador.TABLA;
        Log.v("SentenciaSQL: ", sql);
        db.execSQL(sql);
        //insert into jugador select _id, nombre, telefono, fnac from auxiliarjugador
        //insert into partido (idjugador, valoracion) select _id, valoracion from auxiliarjugador

        //Borrar tabla auxiliar
        sql="drop table if exists auxiliar"+ Contrato.TablaJugador.TABLA;
        db.execSQL(sql);
        //drop table if exists auxiliarjugador
    }

}
