package com.fdgproject.firedge.aad_practicasegunda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firedge on 17/11/2014.
 */
public class GestorJugador {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorJugador(Context c) {
        abd= new Ayudante(c);
    }
    public void open() {
        bd= abd.getWritableDatabase();
    }
    public void openRead() {
        bd= abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    //INSERTAR
    public long insert(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO,objeto.getTelefono());
        valores.put(Contrato.TablaJugador.FNAC,objeto.getFnac());
        long id = bd.insert(Contrato.TablaJugador.TABLA,null, valores);
        //El ID es el codigo autonumerico (Primary Key)
        return id;
    }

    public long insertPartido(long idjugador, String contrincante, int valoracion) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.IDJUGADOR, idjugador);
        valores.put(Contrato.TablaPartido.CONTRINCANTE,contrincante);
        valores.put(Contrato.TablaPartido.VALORACION,valoracion);
        long id = bd.insert(Contrato.TablaPartido.TABLA,null, valores);
        //El ID es el codigo autonumerico (Primary Key)
        return id;
    }

    //BORRAR
    public int delete(Jugador objeto) {
        String condicion= Contrato.TablaJugador._ID+ " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.delete(Contrato.TablaJugador.TABLA, condicion,argumentos);
        return cuenta;
    }

    //EDITAR
    public int update(Jugador objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaJugador.NOMBRE, objeto.getNombre());
        valores.put(Contrato.TablaJugador.TELEFONO, objeto.getTelefono());
        valores.put(Contrato.TablaJugador.VALORACION,objeto.getValoracion());
        valores.put(Contrato.TablaJugador.FNAC,objeto.getFnac());
        String condicion= Contrato.TablaJugador._ID+ " = ?";
        String[] argumentos = { objeto.getId() + "" };
        int cuenta = bd.update(Contrato.TablaJugador.TABLA, valores,condicion, argumentos);
        return cuenta;
    }

    public Jugador getRow(Cursor c) {
        Jugador objeto= new Jugador();
        Long id = c.getLong(0);
        objeto.setId(id);
        objeto.setNombre(c.getString(1));
        objeto.setTelefono(c.getString(2));
        objeto.setFnac(c.getString(3));
        try {
            objeto.setValoracion(c.getInt(4));
        } catch (Exception ex){
            objeto.setValoracion(0);
        }
        return objeto;
    }

    public Cursor getCursor(){
        String join = "select j.*, AVG(p.valoracion) from jugador j left outer join partido p on j._id = p.idjugador group by j._id";
        return bd.rawQuery(join, null);
    }
}
