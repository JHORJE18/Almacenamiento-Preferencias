package com.example.jhorje.almacenamientosimple;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Variables
    private LinearLayout lyPrincipal, lyBtns, lyCrear;
    private boolean panelGuardar;

        //Crear datos
        private EditText editNombre, editDNI, editFecha;
        private RadioButton rdBtnChico, rdBtnChica;

        //Mostrar datos
        private TextView txtName, txtDNIe, txtFecha, txtSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        panelGuardar = false;

        //Asignacion a Vista
        lyCrear = (LinearLayout) findViewById(R.id.lyCrear);

        editNombre = (EditText) findViewById(R.id.editNombre);
        editDNI = (EditText) findViewById(R.id.editDNI);
        editFecha = (EditText) findViewById(R.id.editFecha);
        rdBtnChico = (RadioButton) findViewById(R.id.rdBtnChico);
        rdBtnChica = (RadioButton) findViewById(R.id.rdBtnChica);

        txtName = (TextView) findViewById(R.id.txtName);
        txtDNIe = (TextView) findViewById(R.id.txtDNI);
        txtFecha = (TextView) findViewById(R.id.txtNacimiento);
        txtSexo = (TextView) findViewById(R.id.txtSexo);

        lyCrear.setVisibility(View.GONE);
        cargarPreferencias();
    }

    //Guardar preferencias
    public void guardarPreferencias(){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();

        //Cargamos valores de la vista
        String nombre = editNombre.getText().toString();
        String dni = editDNI.getText().toString();
        String fecha = editFecha.getText().toString();
        boolean chico = rdBtnChico.isChecked();
        boolean chica = rdBtnChica.isChecked();

        //Guardamos valores
        editor.putString("Nombre",nombre);
        editor.putString("DNI",dni);
        editor.putString("Fecha",fecha);
        editor.putBoolean("Chico",chico);
        editor.putBoolean("Chica",chica);
        editor.commit();

        Toast.makeText(getBaseContext(),"Se han Guardado las preferencias",Toast.LENGTH_LONG).show();
        cargarPreferencias();
    }

    //Cargar preferencias
    public void cargarPreferencias(){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);

        //Asignamos valores a pantalla
        txtName.setText("Hola " + misPreferencias.getString("Nombre","Anonimo"));
        txtDNIe.setText("DNIe: " + misPreferencias.getString("DNI","xxxxxxxX"));
        txtFecha.setText("Nacimiento: " + misPreferencias.getString("Fecha","¿?/¿?/¿?¿?"));
        if (misPreferencias.getBoolean("Chico",false)){
            txtSexo.setText("Eres masculino");
            txtSexo.setTextColor(getColor(R.color.colorChico));
        } else {
            txtSexo.setText("Eres femenina");
            txtSexo.setTextColor(getColor(R.color.colorChica));
        }
        Toast.makeText(getBaseContext(),"Se han Cargado las preferencias",Toast.LENGTH_LONG).show();
    }

    //Eliminar preferencias
    public void eliminarPreferencias(){
        SharedPreferences misPreferencias = getSharedPreferences("PreferenciasUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();

        editor.clear().commit();
        Toast.makeText(getBaseContext(),"Preferencias eliminadas",Toast.LENGTH_LONG).show();

        cargarPreferencias();
    }

    //Clics botones
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnCrear:
                if (panelGuardar){
                    lyCrear.setVisibility(View.GONE);
                    panelGuardar = false;
                } else {
                    lyCrear.setVisibility(View.VISIBLE);
                    panelGuardar = true;
                }
                break;
            case R.id.btnCargar:
                cargarPreferencias();
                break;
            case R.id.btnGuardarDatos:
                guardarPreferencias();
                lyCrear.setVisibility(View.GONE);
                panelGuardar = true;
                break;
            case R.id.btnEliminar:
                eliminarPreferencias();
                break;
        }
    }
}
