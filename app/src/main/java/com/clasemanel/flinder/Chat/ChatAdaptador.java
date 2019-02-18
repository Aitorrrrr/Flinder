package com.clasemanel.flinder.Chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.clasemanel.flinder.Modelo.ChatPicker;
import com.clasemanel.flinder.R;

import java.util.ArrayList;

public class ChatAdaptador extends RecyclerView.Adapter<ChatAdaptador.ChatPickerViewHolder> {

    private ArrayList<ChatPicker> arrayChats;

    public static class ChatPickerViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public TextView nombre;
        public TextView ultimMensaje;
        public ImageView imagen;

        public ChatPickerViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.lbl_nombre_chatPicker);
            ultimMensaje = (TextView) itemView.findViewById(R.id.lbl_mensaje_chatPicker);
            imagen = (ImageView) itemView.findViewById(R.id.img_imagenUser_chatPicker);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public ChatAdaptador(ArrayList<ChatPicker> array)
    {
        this.arrayChats = array;
        Log.d("MIO",arrayChats.size()+" en el adaptador");
    }

    @NonNull
    @Override
    public ChatPickerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_chat_picker,viewGroup, false);
        Log.d("MIO","Hola inflo un layout");

        return new ChatPickerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatPickerViewHolder chatPickerViewHolder, int i) {
        chatPickerViewHolder.nombre.setText(arrayChats.get(i).getId());
    }

    @Override
    public int getItemCount() {
        Log.d("MIO",arrayChats.size()+" en el getCount");
        return arrayChats.size();
    }
}
