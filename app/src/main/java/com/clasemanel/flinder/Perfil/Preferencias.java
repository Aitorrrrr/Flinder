package com.clasemanel.flinder.Perfil;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.clasemanel.flinder.LogIn.LoginFragment;
import com.clasemanel.flinder.NavigationHost;
import com.clasemanel.flinder.R;
import com.google.firebase.auth.FirebaseAuth;


public class Preferencias extends Fragment implements View.OnClickListener {

    Button cerrarSesion;
    Switch notificaciones;
    private EditText muestrame;
    FirebaseAuth mAuth;

    private LoginFragment login;

    public Preferencias() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_preferencias, container, false);

        cerrarSesion=v.findViewById(R.id.btn_cerrarSesion_preferencias);
        cerrarSesion.setOnClickListener(this);

        notificaciones=v.findViewById(R.id.switch_notificaciones_preferencias);

        notificaciones.setOnClickListener(this);

        muestrame=v.findViewById(R.id.et_muestrame_preferencias);
        muestrame.setOnClickListener(this);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        login =new LoginFragment();
        ((NavigationHost) getActivity()).navigateTo(login,false);
        Toast.makeText(getContext(),getString(R.string.desconectado), Toast.LENGTH_SHORT).show();
    }

    private void notificaciones(){

        if(notificaciones.isChecked())
        {
            Toast.makeText(getContext(),getText(R.string.notificacionesActivadas), Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(),getText(R.string.notificacionesDesactivadas), Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrar() {

        final CharSequence[] items = { getString(R.string.hombre), getString(R.string.mujer), getString(R.string.todo)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.queBusco));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                muestrame.setText(items[item]);
                dialog.dismiss();

            }
        }).show();
    }


}