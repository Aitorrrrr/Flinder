package com.clasemanel.flinder.Perfil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.clasemanel.flinder.Modelo.Imagenes;
import com.clasemanel.flinder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class Fotos extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private int PICK_IMAGE_REQUEST = 111;

    //Imagenes

    private Uri mImageUri;

    String ruta;
    String img1;
    String img2;
    String img3;
    String img4;
    String img5;
    String img6;

    //Referencias a la base de datos

    private DatabaseReference bbdd;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private UploadTask uploadTask;
    private DatabaseReference databaseReference;
    private DatabaseReference nodoImagenes;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    // Fotos del perfil

    private ImageView fotoPerfil1;
    private ImageView fotoPerfil2;
    private ImageView fotoPerfil3;
    private ImageView fotoPerfil4;
    private ImageView fotoPerfil5;
    private ImageView fotoPerfil6;

    private int ImageViewDestí_id;
    private Imagenes imag;

    public Fotos() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fotos, container, false);

        imag = new Imagenes(getActivity());
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://flinder-7bcd9.appspot.com");
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getInstance().getCurrentUser();

        fotoPerfil1 = (ImageView)v.findViewById(R.id.iv_FotoPerfil1_Fotos);
        fotoPerfil1.setOnClickListener(this);
        fotoPerfil2 = (ImageView)v.findViewById(R.id.iv_FotoPerfil2_Fotos);
        fotoPerfil2.setOnClickListener(this);
        fotoPerfil3 = (ImageView)v.findViewById(R.id.iv_FotoPerfil3_Fotos);
        fotoPerfil3.setOnClickListener(this);
        fotoPerfil4 = (ImageView)v.findViewById(R.id.iv_FotoPerfil4_Fotos);
        fotoPerfil4.setOnClickListener(this);
        fotoPerfil5 = (ImageView)v.findViewById(R.id.iv_FotoPerfil5_Fotos);
        fotoPerfil5.setOnClickListener(this);
        fotoPerfil6 = (ImageView)v.findViewById(R.id.iv_FotoPerfil6_Fotos);
        fotoPerfil6.setOnClickListener(this);

        img1 = "img1";
        img2 = "img2";
        img3 = "img3";
        img4 = "img4";
        img5 = "img5";
        img6 = "img6";


        imag.recuperarFoto(fotoPerfil1, img1);
        imag.recuperarFoto(fotoPerfil2, img2);
        imag.recuperarFoto(fotoPerfil3, img3);
        imag.recuperarFoto(fotoPerfil4, img4);
        imag.recuperarFoto(fotoPerfil5, img5);
        imag.recuperarFoto(fotoPerfil6, img6);


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

    public void seleccionarFoto()
    {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            ImageView desti = (ImageView) getActivity().findViewById(ImageViewDestí_id);
            mImageUri = data.getData();
            Picasso.with(getContext())
                    .load(mImageUri)
                    .fit()
                    .centerCrop()
                    .into(desti);
            uploadFile(mImageUri, ruta, user.getUid());
        }

    }

    private void uploadFile(final Uri imagen, final String numeroFoto, final String uid) {
        if (imagen != null) {
            final String ahora = ""+System.currentTimeMillis();
            String rutaImagen = "prueba2/"+ahora;
            final StorageReference fileReference = storageRef.child(rutaImagen);

            uploadTask = (UploadTask) fileReference.putFile(imagen)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            bbdd.child(uid).child("imagenes").child(numeroFoto).child("nombre").setValue(ahora);
                            Toast.makeText(getContext(), "Upload successful", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.iv_FotoPerfil1_Fotos)
        {
            ImageViewDestí_id =R.id.iv_FotoPerfil1_Fotos;
            ruta = img1;
            seleccionarFoto();
        }

        if (v.getId()==R.id.iv_FotoPerfil2_Fotos)
        {
            ImageViewDestí_id =R.id.iv_FotoPerfil2_Fotos;
            ruta = img2;
            seleccionarFoto();
        }

        if (v.getId()==R.id.iv_FotoPerfil3_Fotos)
        {
            ImageViewDestí_id =R.id.iv_FotoPerfil3_Fotos;
            ruta = img3;
            seleccionarFoto();
        }

        if (v.getId()==R.id.iv_FotoPerfil4_Fotos)
        {
            ImageViewDestí_id =R.id.iv_FotoPerfil4_Fotos;
            ruta = img4;
            seleccionarFoto();
        }

        if (v.getId()==R.id.iv_FotoPerfil5_Fotos)
        {
            ImageViewDestí_id =R.id.iv_FotoPerfil5_Fotos;
            ruta = img5;
            seleccionarFoto();
        }

        if (v.getId()==R.id.iv_FotoPerfil6_Fotos)
        {
            ImageViewDestí_id =R.id.iv_FotoPerfil6_Fotos;
            ruta = img6;
            seleccionarFoto();
        }
    }
}
