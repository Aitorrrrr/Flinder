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

    private Uri mImageUri1;
    private Uri mImageUri2;
    private Uri mImageUri3;
    private Uri mImageUri4;
    private Uri mImageUri5;
    private Uri mImageUri6;

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
    private String rama;

    private ArrayList<ImageView> listaImgViews;
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


        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://flinder-7bcd9.appspot.com");
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");

        fotoPerfil1 = (ImageView)v.findViewById(R.id.fotoPerfil1);
        fotoPerfil2 = (ImageView)v.findViewById(R.id.fotoPerfil2);
        fotoPerfil2.setOnClickListener(this);
        fotoPerfil3 = (ImageView) v.findViewById(R.id.fotoPerfil3);
        fotoPerfil3.setOnClickListener(this);
        fotoPerfil4 = (ImageView)v.findViewById(R.id.fotoPerfil4);
        fotoPerfil4.setOnClickListener(this);
        fotoPerfil5 = (ImageView)v.findViewById(R.id.fotoPerfil5);
        fotoPerfil5.setOnClickListener(this);
        fotoPerfil6 = (ImageView)v.findViewById(R.id.fotoPerfil6);
        fotoPerfil6.setOnClickListener(this);

        imag = new Imagenes();
        listaImgViews = new ArrayList<>();

        listaImgViews.add(fotoPerfil1);
        listaImgViews.add(fotoPerfil2);
        listaImgViews.add(fotoPerfil3);
        listaImgViews.add(fotoPerfil4);
        listaImgViews.add(fotoPerfil5);
        listaImgViews.add(fotoPerfil6);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getInstance().getCurrentUser();
        nodoImagenes = FirebaseDatabase.getInstance().getReference("Usuarios").child(user.getUid()).child("imagenes");
        Log.d("MIO",nodoImagenes.toString());

        nodoImagenes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot imagen: dataSnapshot.getChildren())
                {
                    String urlAux = imagen.child("nombre").getValue().toString();
                    Log.d("MIO",urlAux);
                    imag.anyadirImagen(urlAux);
                }

                for (int i = 0; i<imag.getSize(); i++)
                {
                    recuperarImagen(listaImgViews.get(i),i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    private void recuperarImagen(final ImageView imgLogo, int posicion) {

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("prueba2").child(imag.recuperarImagen(posicion));
        Log.d("MIO",mStorage.toString());
        mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(getContext())
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(imgLogo);
            }
        });
        mStorage.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
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
            mImageUri2 = data.getData();
            Picasso.with(getContext())
                    .load(mImageUri2)
                    .fit()
                    .centerCrop()
                    .into(desti);
            uploadFile(mImageUri2, rama, imag, user.getUid());
        }

    }

    private void uploadFile(final Uri imagen, final String numeroFoto, final Imagenes imag, final String uid) {
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
        if (v.getId()==R.id.fotoPerfil2)
        {
            ImageViewDestí_id =R.id.fotoPerfil2;
            rama = "img2";
            seleccionarFoto();
        }

        if (v.getId()==R.id.fotoPerfil3)
        {
            ImageViewDestí_id =R.id.fotoPerfil3;
            rama = "img3";
            seleccionarFoto();
        }

        if (v.getId()==R.id.fotoPerfil4)
        {
            ImageViewDestí_id =R.id.fotoPerfil4;
            rama = "img4";
            seleccionarFoto();
        }

        if (v.getId()==R.id.fotoPerfil5)
        {
            ImageViewDestí_id =R.id.fotoPerfil5;
            rama = "img5";
            seleccionarFoto();
        }

        if (v.getId()==R.id.fotoPerfil6)
        {
            ImageViewDestí_id =R.id.fotoPerfil6;
            rama = "img6";
            seleccionarFoto();
        }
    }
}
