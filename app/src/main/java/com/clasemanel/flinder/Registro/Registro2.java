package com.clasemanel.flinder.Registro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clasemanel.flinder.Modelo.Usuario;
import com.clasemanel.flinder.NavigationHost;
import com.clasemanel.flinder.R;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class Registro2 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    static final int fotos = 1;
    int PICK_IMAGE_REQUEST = 111;
    private Registro3 registro3;
    private Usuario personas;
    Spinner provincias;
    private int mYear, mMonth, mDay;
    EditText resultado;
    EditText genero;
    Uri ur;
    UploadTask uploadTask;
    String ur2;

    Button tomar_foto;
    ImageView imageView;

    Button siguiente2;

    TextView errorImageView;

    EditText nombreRegistro;
    EditText apellidosRegistro;

    private Uri mImageUri;

    public Registro2() {

    }

    public static Registro2 newInstance(Usuario param1) {
        Registro2 fragment = new Registro2();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personas = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_registro2, container, false);
        provincias=v.findViewById(R.id.spinner_provincias_reg2);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.provincias));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provincias.setAdapter(adapter);

        resultado=v.findViewById(R.id.et_fechaSelector_reg2);
        resultado.setOnClickListener(this);

        genero=v.findViewById(R.id.et_generoSel_reg2);
        genero.setOnClickListener(this);

        tomar_foto=v.findViewById(R.id.btn_foto_reg2);
        tomar_foto.setOnClickListener(this);

        imageView=v.findViewById(R.id.imageView);

        siguiente2=v.findViewById(R.id.btn_siguiente_reg2);
        siguiente2.setOnClickListener(this);

        nombreRegistro=v.findViewById(R.id.et_nombre_reg2);
        apellidosRegistro=v.findViewById(R.id.et_apellidos_reg2);
        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.et_fechaSelector_reg2){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            resultado.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v.getId()==R.id.et_generoSel_reg2){
            final CharSequence[] items = { getString(R.string.hombre), getString(R.string.mujer), getString(R.string.noEspecificar)};

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.genero));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    genero.setText(items[item]);
                    dialog.dismiss();

                }
            });
            builder.show();
        }

        if (v.getId()==R.id.btn_foto_reg2){
            seleccionarFoto();
        }

        if (v.getId()==R.id.btn_siguiente_reg2){

            if (comprobar()==true){
                String txtNombre = nombreRegistro.getText().toString().trim();
                personas.setNombre(txtNombre);

                String txtApellidos = apellidosRegistro.getText().toString().trim();
                personas.setApellidos(txtApellidos);

                String txtLocalidades = provincias.getSelectedItem().toString();
                personas.setLocalidad(txtLocalidades);

                String txtFecha = resultado.getText().toString().trim();
                personas.setFechaDeNacimiento(txtFecha);

                String txtGenero = genero.getText().toString().trim();
                personas.setGenero(txtGenero);


                String imagen = mImageUri.toString();
                registro3 = Registro3.newInstance(personas, imagen);
                ((NavigationHost) getActivity()).navigateTo(registro3, true);
            }
            else
                Toast.makeText(getContext(),getString(R.string.algoNoVa),Toast.LENGTH_SHORT).show();
        }
    }

    public void seleccionarFoto()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(getContext()).load(mImageUri).into(imageView);
        }

    }


    public boolean comprobar(){
        boolean comproba;
        comproba=true;
        String resultadoFecha=resultado.getText().toString().trim();
        if(resultadoFecha.isEmpty()){
            resultado.setError(getString(R.string.seleccionaUnaFecha));
            comproba=false;
        }
        else
        {
            resultado.setError(null);
        }
        String resultadoGenero=genero.getText().toString().trim();
        if (resultadoGenero.isEmpty()){
            genero.setError(getString(R.string.seleccionaGenero));
            comproba=false;
        }
        else {
            genero.setError(null);
        }

        String nombre=nombreRegistro.getText().toString().trim();
        if (nombre.isEmpty()){
            nombreRegistro.setError(getString(R.string.ponerNombre));
            comproba=false;
        }
        String apellido=apellidosRegistro.getText().toString().trim();
        if (apellido.isEmpty()){
            apellidosRegistro.setError(getString(R.string.ponerApellidos));
            comproba=false;
        }

        if (mImageUri == null)
        {
            Toast.makeText(getContext(), getString(R.string.seleccionarFoto), Toast.LENGTH_LONG).show();
            comproba=false;
        }
        return comproba;
    }
}
