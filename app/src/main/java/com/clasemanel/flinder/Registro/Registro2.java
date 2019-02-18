package com.clasemanel.flinder.Registro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class Registro2 extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";

    private static final int fotos = 1;
    private int PICK_IMAGE_REQUEST = 111;

    private Registro3 registro3;
    private Usuario userAux;

    private Spinner provincias;
    private int mYear, mMonth, mDay;
    private EditText resultado;
    private EditText genero;
    private Uri ur;
    private String ur2;

    private Button tomar_foto;
    private ImageView imageView;

    private Button siguiente2;

    private TextView errorImageView;

    private EditText nombreRegistro;
    private EditText apellidosRegistro;

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
            userAux = getArguments().getParcelable(ARG_PARAM1);
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

        errorImageView=v.findViewById(R.id.imageviewerror);

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
            final CharSequence[] items = { "Hombre", "Mujer", "Prefiero no especificar"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Seleccione su género:");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    genero.setText(items[item]);
                    dialog.dismiss();

                }
            });
            builder.show();
        }

        if (v.getId()==R.id.btn_foto_reg2){

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            //intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);

           /* final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            LayoutInflater inflater = getActivity().getLayoutInflater();

            View layout=inflater.inflate(R.layout.seleccionar_galeria_camara,null);
            builder.setView(layout);

            layout.setBackgroundResource(R.color.colorPrimary);
            builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            final ImageView galeria=layout.findViewById(R.id.galeria);
            galeria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cargarImagen();
                    cambiarmensajeBoton();
                }
            });

            ImageView camara=layout.findViewById(R.id.camara);
            camara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llamarIntentFoto();
                    cambiarmensajeBoton();
                }
            });*/

            // builder.show();

        }

        if (v.getId()==R.id.btn_siguiente_reg2){

            if (comprobar()==true){
                String txtNombre = nombreRegistro.getText().toString().trim();
                userAux.setNombre(txtNombre);

                String txtApellidos = apellidosRegistro.getText().toString().trim();
                userAux.setApellidos(txtApellidos);

                String txtLocalidades = provincias.getSelectedItem().toString();
                userAux.setLocalidad(txtLocalidades);

                String txtFecha = resultado.getText().toString().trim();
                userAux.setFechaDeNacimiento(txtFecha);

                String txtGenero = genero.getText().toString().trim();
                userAux.setGenero(txtGenero);

                registro3 = Registro3.newInstance(userAux, ur2);
                ((NavigationHost) getActivity()).navigateTo(registro3, true);
            }
            else
                Toast.makeText(getContext(),"no va",Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarImagen() {
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"selecciona"),10);

    }

    public void llamarIntentFoto(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, fotos);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == fotos &&resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            imagenAuxiliar = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imagenAuxiliar);

        }*/

        if (resultCode==RESULT_OK){
            Uri path=data.getData();
            imageView.setImageURI(path);
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            ur = data.getData();
            ur2 = data.getDataString();

            try {
                Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), ur);
                Bundle extras = data.getExtras();
                //imagenAuxiliar = (Bitmap) extras.get("data");
                //ur2 = (String) extras.get("data");
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public boolean comprobar(){
        boolean comproba;
        comproba=true;
        String resultadoFecha=resultado.getText().toString().trim();
        if(resultadoFecha.isEmpty()){
            resultado.setError("Debe seleccionar una fecha.");
            comproba=false;
        }
        else
        {
            resultado.setError(null);
        }
        String resultadoGenero=genero.getText().toString().trim();
        if (resultadoGenero.isEmpty()){
            genero.setError("Debe seleccionar un género.");
            comproba=false;
        }
        else {
            genero.setError(null);
        }

        String nombre=nombreRegistro.getText().toString().trim();
        if (nombre.isEmpty()){
            nombreRegistro.setError("Debe poner un nombre.");
            comproba=false;
        }
        String apellido=apellidosRegistro.getText().toString().trim();
        if (apellido.isEmpty()){
            apellidosRegistro.setError("Debe poner los apellidos.");
            comproba=false;
        }
        return comproba;
    }

    public void cambiarmensajeBoton(){
        tomar_foto.setText("Editar");
    }
}
