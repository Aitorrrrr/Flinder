package com.clasemanel.flinder.Perfil;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.clasemanel.flinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditarPerfil extends Fragment implements View.OnClickListener{

    Button aceptar;
    EditText editarNombre;
    EditText editarApellidos;
    Spinner editarLocalidad;
    DatabaseReference bbdd;
    private Fotos fotos;
    FirebaseAuth auth;

    public EditarPerfil() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        editarNombre = (EditText)v.findViewById(R.id.et_nombre_editarPerfil);
        editarApellidos = (EditText)v.findViewById(R.id.et_apellidos_editarPerfil);
        editarLocalidad = (Spinner)v.findViewById(R.id.spinner_provincias_editarPerfil);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        fotos = new Fotos();
        transaction.add(R.id.frameFotos, fotos);
        transaction.commit();

        //Spinner de localidad
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.provincias));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editarLocalidad.setAdapter(adapter);
        editarLocalidad.setSelection(0); // Posicion inicial del sppiner
        editarLocalidad.setBackgroundColor(Color.parseColor("#90CAF9"));
        aceptar = (Button)v.findViewById(R.id.btn_guardar_editarPerfil);

        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        aceptar.setOnClickListener(this);
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
        if (v.getId()==R.id.btn_guardar_editarPerfil)
        {
            final String nombre = String.valueOf(editarNombre.getText());
            final String apellidos = String.valueOf(editarApellidos.getText());
            final String localidad= editarLocalidad.getSelectedItem().toString();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String key ="";
            if (user!=null)
            {
                key = user.getUid();
            }
            Query q = bbdd.orderByKey().equalTo(key);
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren())
                    {
                        String key = datasnapshot.getKey();
                        if (!nombre.equals(""))
                        {
                            bbdd.child(key).child("nombre").setValue(nombre);
                        }
                        if (!apellidos.equals(""))
                        {
                            bbdd.child(key).child("apellidos").setValue(apellidos);
                        }
                        if (!localidad.equals("Selecciona la provincia"))
                        {
                            bbdd.child(key).child("localidad").setValue(localidad);
                        }
                    }
                    Toast.makeText(getActivity(), "Guardado", Toast.LENGTH_LONG).show();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
