package com.clasemanel.flinder.Modelo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
public class Imagenes {
    private String imagenD1;
    private DatabaseReference nodoImagenes;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private StorageReference mReference;

    private Context context;

    public Imagenes(Context context)
    {
        this.context = context;
    }

    public void recuperarFoto(final ImageView imagen1, final String numeroImagen)
    {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getInstance().getCurrentUser();
        nodoImagenes = FirebaseDatabase.getInstance().getReference("Usuarios").child(user.getUid()).child("imagenes");
        nodoImagenes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    imagenD1 = dataSnapshot.child(numeroImagen).child("nombre").getValue().toString();
                    mReference = FirebaseStorage.getInstance().getReference().child("prueba2").child(imagenD1);
                    mReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(context)
                                    .load(uri)
                                    .fit()
                                    .centerCrop()
                                    .into(imagen1);
                        }
                    });
                }catch (NullPointerException e) {
                    Log.d("MIO", "Error: "+e);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}