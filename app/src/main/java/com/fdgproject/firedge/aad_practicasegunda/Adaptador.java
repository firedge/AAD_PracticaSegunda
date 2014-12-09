package com.fdgproject.firedge.aad_practicasegunda;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Firedge on 21/11/2014.
 */
public class Adaptador extends CursorAdapter{

    public Adaptador (Context co, Cursor cu) {
        super(co, cu, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater i = LayoutInflater.from(viewGroup.getContext());
        View v = i.inflate(R.layout.list_element, viewGroup, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        GestorJugador gj = new GestorJugador(context);
        Jugador j = gj.getRow(cursor);
        TextView tvNombre;
        tvNombre=(TextView) view.findViewById(R.id.tv_nombre);
        tvNombre.setText(j.getNombre());
        TextView tvTelefono;
        tvTelefono=(TextView) view.findViewById(R.id.tv_telefono);
        tvTelefono.setText(j.getTelefono());
        TextView tvValoracion;
        tvValoracion=(TextView) view.findViewById(R.id.tv_valoracion);
        tvValoracion.setText(j.getValoracion()+"");
        TextView tvFecha;
        tvFecha=(TextView) view.findViewById(R.id.tv_fecha);
        tvFecha.setText(j.getFnac());
    }
}
