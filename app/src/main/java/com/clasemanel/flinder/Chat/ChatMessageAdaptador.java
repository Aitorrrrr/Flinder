package com.clasemanel.flinder.Chat;

import android.content.Context;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatMessageAdaptador extends RecyclerView.Adapter<ChatMessageAdaptador.ChatMessageViewHolder> {

    private ArrayList<ChatMessage> arrayMensajes;
    private Context auxContext;

    public static class ChatMessageViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public TextView msgPropio;
        public TextView msgAjeno;
        public TextView nombreAjeno;

        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);

            msgPropio=(TextView)itemView.findViewById(R.id.tv_mensaje_vhChat);
            msgAjeno = (TextView) itemView.findViewById(R.id.tv_mensaje_vhRecibido);
            nombreAjeno = (TextView) itemView.findViewById(R.id.tv_hora_vhRecibido);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public ChatMessageAdaptador(ArrayList<ChatMessage> array, Context context)
    {
        this.arrayMensajes = array;
        this.auxContext = context;
    }

    public ArrayList<ChatMessage> getListaChats()
    {
        return this.arrayMensajes;
    }

    public void setListaChats(ArrayList<ChatMessage> arrayChats)
    {
        this.arrayMensajes = arrayChats;
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_chat_message,viewGroup, false);

        return new ChatMessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int i) {
        if (arrayMensajes.get(i).isEsMatch())
        {
            holder.msgPropio.setVisibility(View.INVISIBLE);
            holder.msgAjeno.setText(arrayMensajes.get(i).getMsg());
        }
        else
        {
            holder.msgAjeno.setVisibility(View.INVISIBLE);
            holder.msgPropio.setText(arrayMensajes.get(i).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return arrayMensajes.size();
    }
}
