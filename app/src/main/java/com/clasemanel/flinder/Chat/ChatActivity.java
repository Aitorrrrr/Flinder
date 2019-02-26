package com.clasemanel.flinder.Chat;

import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clasemanel.flinder.Modelo.ChatModel;
import com.clasemanel.flinder.Modelo.ChatPicker;
import com.clasemanel.flinder.R;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recycler_Mensajes;
    private ChatMessageAdaptador adaptadorRecycler;
    private RecyclerView.LayoutManager rvLM;

    private EditText et_enviar;
    private Button btn_enviar;
    private ImageView img_match;
    private TextView nombre_match;

    private DatabaseReference bbdd;
    private DatabaseReference bbddMatch;

    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private StorageReference mStorage;

    private String idUsuario;
    private String idMatch;
    private String idRandomMatch;
    private String idChat;

    private ChatPicker match;

    private ArrayList<ChatMessage> mensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mensajes = new ArrayList<ChatMessage>();

        Bundle auxBundle = getIntent().getExtras();
        match = auxBundle.getParcelable("match");
        idMatch = match.getIdUser();
        idRandomMatch = match.getIdMatch();
        if (match.getIdChat()!=null)
        {
            Log.d("MIO","existe id chat"+match.getIdChat());
            idChat = match.getIdChat();
        }
        else
        {
            idChat = null;
        }

        et_enviar = (EditText) findViewById(R.id.et_escribir_chatDentro);
        btn_enviar = (Button) findViewById(R.id.btn_enviar_chatDentro);
        recycler_Mensajes = (RecyclerView) findViewById(R.id.reyclerview_messageList_chatDentro);
        img_match = (ImageView) findViewById(R.id.img_imagenUser_chatDentro);
        nombre_match = (TextView) findViewById(R.id.tv_nombre_chatDentro);

        mStorage = FirebaseStorage.getInstance().getReference().child("prueba2");

        nombre_match.setText(match.getNombreUser());
        mStorage.child(match.getImagen()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(img_match)
                        .load(uri)
                        .into(img_match);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        idUsuario = usuario.getUid();

        if (idChat!=null)
        {
            bbdd = FirebaseDatabase.getInstance().getReference("chats").child(idChat);

            bbdd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("MIO","onDataChange chatId correcto");
                    mensajes.clear();

                    for (DataSnapshot auxDS: dataSnapshot.getChildren())
                    {
                        String id = auxDS.child("creadoPor").getValue().toString();
                        boolean esMatch;
                        if (id.compareTo(idUsuario)==0)
                        {
                            esMatch = false;
                        }
                        else
                        {
                            esMatch = true;
                        }
                        String idMensaje =auxDS.getKey();

                        String mensaje = auxDS.child("mensaje").getValue().toString();

                        ChatMessage msgAux = new ChatMessage(idMensaje, idUsuario, mensaje, esMatch);

                        mensajes.add(0,msgAux);
                    }

                    mensajes = invertirArray(mensajes);

                    adaptadorRecycler.setListaChats(mensajes);
                    adaptadorRecycler.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            bbdd = FirebaseDatabase.getInstance().getReference("chats");
        }

        iniciarRecycler();

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_enviar.getText().toString().isEmpty())
                {
                    if (idChat!=null)
                    {
                        String clave = bbdd.push().getKey();
                        ChatModel mensajeAux = new ChatModel(idUsuario, et_enviar.getText().toString());
                        bbdd.child(clave).setValue(mensajeAux);
                    }
                    else
                    {
                        String claveChat = bbdd.push().getKey();
                        String clave = bbdd.push().getKey();

                        ChatModel mensajeAux = new ChatModel(idUsuario,et_enviar.getText().toString());
                        bbdd.child(claveChat).child(clave).setValue(mensajeAux);
                        bbdd = FirebaseDatabase.getInstance().getReference("chats").child(claveChat);

                        bbddMatch = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuario).child("matches").child(idRandomMatch).child("idChat");
                        bbddMatch.setValue(claveChat);

                        bbddMatch = FirebaseDatabase.getInstance().getReference("Usuarios").child(idMatch).child("matches").child(idRandomMatch).child("idChat");
                        bbddMatch.setValue(claveChat);

                        idChat = claveChat;

                        bbdd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Log.d("MIO","onDataChange chatId correcto");
                                mensajes.clear();

                                for (DataSnapshot auxDS: dataSnapshot.getChildren())
                                {
                                    String id = auxDS.child("creadoPor").getValue().toString();
                                    boolean esMatch;
                                    if (id.compareTo(idUsuario)==0)
                                    {
                                        esMatch = false;
                                    }
                                    else
                                    {
                                        esMatch = true;
                                    }
                                    String idMensaje =auxDS.getKey();

                                    String mensaje = auxDS.child("mensaje").getValue().toString();

                                    ChatMessage msgAux = new ChatMessage(idMensaje, idUsuario, mensaje, esMatch);

                                    mensajes.add(0,msgAux);
                                }

                                adaptadorRecycler.setListaChats(mensajes);
                                adaptadorRecycler.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    et_enviar.setText("");
                }
            }
        });
    }

    private void iniciarRecycler()
    {
        rvLM = new LinearLayoutManager(this);
        recycler_Mensajes.setLayoutManager(rvLM);
        adaptadorRecycler = new ChatMessageAdaptador(mensajes, this);
        recycler_Mensajes.setAdapter(adaptadorRecycler);
    }

    private ArrayList<ChatMessage> invertirArray(ArrayList<ChatMessage> mensajes)
    {
        ArrayList<ChatMessage> auxiliar = new ArrayList<ChatMessage>();

        for (int i=mensajes.size()-1; i>=0; i--)
        {
            auxiliar.add(mensajes.get(i));
        }

        return auxiliar;
    }
}
