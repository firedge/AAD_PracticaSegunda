package com.fdgproject.firedge.aad_practicasegunda;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private GestorJugador gj;
    private EditText etNombre, etTelefono, etValoracion, etFecha, etIdjugador, etContrincante;
    private Adaptador adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gj = new GestorJugador(this);
        etNombre = (EditText)findViewById(R.id.et_nombre);
        etTelefono = (EditText)findViewById(R.id.et_telefono);
        etValoracion = (EditText)findViewById(R.id.et_valoracion);
        etFecha = (EditText)findViewById(R.id.et_fnac);
        etIdjugador = (EditText)findViewById(R.id.et_idjugador);
        etContrincante = (EditText)findViewById(R.id.et_contrincante);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Cursor
        gj.open();
        Cursor c = gj.getCursor();

        //Lista
        adp = new Adaptador(this, c);
        final ListView lv = (ListView)findViewById(R.id.listaPrincipal);
        lv.setAdapter(adp);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor)lv.getItemAtPosition(i);
                Jugador j = gj.getRow(cursor);
                gj.delete(j);
                adp.changeCursor(gj.getCursor());

                return false;
            }
        });
        //registerForContextMenu(lv);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gj.close();
    }

    public void alta(View v){
        String nombre, fecha, telefono;
        nombre = etNombre.getText().toString();
        fecha = etFecha.getText().toString();
        telefono = etTelefono.getText().toString();
        Jugador j = new Jugador(nombre, telefono, fecha);
        long id = gj.insert(j);
        Toast.makeText(this, getResources().getString(R.string.tst_jug) + id, Toast.LENGTH_SHORT).show();
        Cursor c = gj.getCursor();
        adp.changeCursor(c);
    }

    public void altaPartido(View v){
        long idjugador = 0;
        String contrincante;
        int valoracion = 0;
        contrincante = etContrincante.getText().toString();
        try{
            idjugador = Long.parseLong(etIdjugador.getText().toString());
            valoracion = Integer.parseInt(etValoracion.getText().toString());
        }catch (Exception ex){}
        long id = gj.insertPartido(idjugador, contrincante, valoracion);
        Toast.makeText(this, getResources().getString(R.string.tst_par) + id, Toast.LENGTH_SHORT).show();
        Cursor c = gj.getCursor();
        adp.changeCursor(c);
    }

}
