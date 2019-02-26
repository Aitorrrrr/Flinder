package com.clasemanel.flinder.Perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.clasemanel.flinder.MainActivity;
import com.clasemanel.flinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.processphoenix.ProcessPhoenix;

public class PreferenciasActivity extends AppCompatActivity implements View.OnClickListener {

    private Button cerrarSesion;
    private Switch notificaciones;
    private EditText muestrame;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        cerrarSesion=findViewById(R.id.btn_cerrarSesion_preferencias);
        cerrarSesion.setOnClickListener(this);

        notificaciones=findViewById(R.id.switch_notificaciones_preferencias);

        notificaciones.setOnClickListener(this);

        muestrame=findViewById(R.id.et_muestrame_preferencias);
        muestrame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_cerrarSesion_preferencias){
            FirebaseAuth.getInstance().signOut();
            logOut();
        }

        if (v.getId()==R.id.switch_notificaciones_preferencias){
            notificaciones();
        }

        if (v.getId()==R.id.et_muestrame_preferencias){
            mostrar();
        }
    }



    private void logOut(){
        /*login =new LoginFragment();
        ((NavigationHost) getActivity()).navigateTo(login,false);
        Toast.makeText(getContext(),getString(R.string.desconectado), Toast.LENGTH_SHORT).show();*/
        Intent i = new Intent(this, MainActivity.class);
        ProcessPhoenix.triggerRebirth(this,i);
    }

    private void notificaciones(){

        if(notificaciones.isChecked())
        {
            Toast.makeText(this,getText(R.string.notificacionesActivadas), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,getText(R.string.notificacionesDesactivadas), Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrar() {

        final CharSequence[] items = { getString(R.string.hombre), getString(R.string.mujer), getString(R.string.todo)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.queBusco));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                muestrame.setText(items[item]);
                dialog.dismiss();

            }
        }).show();
    }
}
