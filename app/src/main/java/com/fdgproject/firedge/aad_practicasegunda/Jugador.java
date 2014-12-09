package com.fdgproject.firedge.aad_practicasegunda;

import java.io.Serializable;

/**
 * Created by Firedge on 17/11/2014.
 */

//java beans, pojo --> Plain Old Java Object
public class Jugador implements Serializable, Comparable<Jugador>{
    private long id;
    private String nombre;
    private String telefono;
    private int valoracion;
    private String fnac;

    //1º Constructor predeterminado vacio
    //2º Constructor completo
    //3º Setter and Getter todos
    //4º equals, hashCode --> Clave principal de la tabla
    //5º compareTo
    //6º toString

    public Jugador(){
        this(0,"","",0,"");
    }

    public Jugador(long id, String nombre, String telefono, int valoracion, String fnac) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.valoracion = valoracion;
        this.fnac = fnac;
    }

    public Jugador(String nombre, String telefono, String valoracion, String fnac) {
        this.id = 0;
        this.nombre = nombre;
        this.telefono = telefono;
        int valor;
        try{
            valor = Integer.parseInt(valoracion);
        } catch(NumberFormatException ex){
            valor = 0;
        }
        this.valoracion = valor;
        this.fnac = fnac;
    }

    public Jugador(String nombre, String telefono, String fnac) {
        this.id = 0;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fnac = fnac;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", valoracion=" + valoracion +
                ", fnac='" + fnac + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Jugador jugador = (Jugador) o;

        if (id != jugador.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Jugador jugador) {
        //jugadores -> this, jugador
        if(this.valoracion!=jugador.valoracion){
            return this.valoracion-jugador.valoracion;
        } else if(this.nombre.compareTo(jugador.nombre)!=0){
            return this.nombre.compareTo(jugador.nombre);
        } else {
            return this.fnac.compareTo(jugador.fnac);
        }
    }
}
