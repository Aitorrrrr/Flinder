package com.clasemanel.flinder.Chat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clasemanel.flinder.Modelo.ChatPicker;
import com.clasemanel.flinder.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatAdaptador extends RecyclerView.Adapter<ChatAdaptador.ChatPickerViewHolder> {

    private ArrayList<ChatPicker> arrayChats;
    private StorageReference mStorage;
    private Context auxContext;

    private DatabaseReference bbdd;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;

    public static class ChatPickerViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public TextView nombre;
        public ImageView imagen;

        public ChatPickerViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.lbl_nombre_chatPicker);
            imagen = (ImageView) itemView.findViewById(R.id.img_imagenUser_chatPicker);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public ChatAdaptador(ArrayList<ChatPicker> array, Context context)
    {
        this.arrayChats = array;
        Log.d("MIO",arrayChats.size()+" en el adaptador");
        this.mStorage = FirebaseStorage.getInstance().getReference().child("prueba2");
        this.auxContext = context;

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
    }

    @NonNull
    @Override
    public ChatPickerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_chat_picker,viewGroup, false);
        Log.d("MIO","Hola inflo un layout");

        return new ChatPickerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatPickerViewHolder holder, int i) {
        final ChatPicker chatPicker = arrayChats.get(i);

        if (chatPicker.getNombreUser()!=null && chatPicker.getImagen()!=null)
        {
            holder.nombre.setText(chatPicker.getNombreUser());

            Log.d("MIO","onBind imagen "+chatPicker.getImagen());
            Log.d("MIO", "onBind uri"+mStorage.child(chatPicker.getImagen()).toString());
            mStorage.child(chatPicker.getImagen()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Log.d("MIO","onSuccess");

                    Glide.with(holder.imagen)
                            .load(uri)
                            .into(holder.imagen);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("MIO","La imagen no ha cargado hehe");
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(auxContext, ChatActivity.class);
                    Bundle auxBundle = new Bundle();
                    auxBundle.putParcelable("match",chatPicker);
                    i.putExtras(auxBundle);

                    auxContext.startActivity(i);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(auxContext);
                    alertBuilder.setTitle("¿Desea eliminar el match?");

                    alertBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bbdd = FirebaseDatabase.getInstance().getReference("Usuarios").child(chatPicker.getIdUser()).child("matches").child(usuario.getUid());
                            bbdd.removeValue();

                            bbdd = FirebaseDatabase.getInstance().getReference("Usuarios").child(usuario.getUid()).child("matches").child(chatPicker.getIdUser());
                            bbdd.removeValue();

                            dialog.dismiss();
                        }
                    });
                    alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertBuilder.show();

                    return true;
                }
            });
        }
    }

    public ArrayList<ChatPicker> getListaChats()
    {
        return this.arrayChats;
    }

    public void setListaChats(ArrayList<ChatPicker> arrayChats)
    {
        this.arrayChats = arrayChats;
    }

    @Override
    public int getItemCount() {
        Log.d("MIO",arrayChats.size()+" en el getCount");
        return arrayChats.size();
    }
}
