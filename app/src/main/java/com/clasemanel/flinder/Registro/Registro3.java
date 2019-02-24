package com.clasemanel.flinder.Registro;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clasemanel.flinder.LogIn.LoginFragment;
import com.clasemanel.flinder.Modelo.Imagenes;
import com.clasemanel.flinder.Modelo.Usuario;
import com.clasemanel.flinder.NavigationHost;
import com.clasemanel.flinder.Perfil.EditarPerfil;
import com.clasemanel.flinder.Perfil.Fotos;
import com.clasemanel.flinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.content.ContentValues.TAG;

public class Registro3 extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LoginFragment login;
    EditText resultadoBuscar;
    Usuario personas;
    TextView resultado;
    Button siguiente;
    String url;
    UploadTask uploadTask;
    private DatabaseReference bbdd;
    private Imagenes imag;

    FirebaseStorage storage;
    StorageReference storageRef;
    DatabaseReference databaseReference;

    private Context mainContext;

    private Fotos fragFotos;

    private FirebaseAuth mAuth;

    Uri mImageUri;

    public Registro3() {
    }

    public static Registro3 newInstance(Usuario param1, String ur) {
        Registro3 fragment = new Registro3();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,ur);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personas = getArguments().getParcelable(ARG_PARAM1);
            url = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_registro3, container, false);
        mImageUri = Uri.parse(url);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://flinder-7bcd9.appspot.com");


        databaseReference =  FirebaseDatabase.getInstance().getReference();


        resultadoBuscar=v.findViewById(R.id.et_queBuscas_reg3);
        resultadoBuscar.setOnClickListener(this);
        siguiente=v.findViewById(R.id.btn_aceptar_reg3);
        siguiente.setOnClickListener(this);

        mainContext = v.getContext();

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");


        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.et_queBuscas_reg3){
            final CharSequence[] items = { "Hombres", "Mujeres", "Genero Binario", "Ambos", "Todos"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Seleccione lo que esta buscando:");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {

                    resultadoBuscar.setText(items[item]);
                    dialog.dismiss();

                }
            });
            builder.show();
        }

        if (v.getId()==R.id.btn_aceptar_reg3){
            if (comprobar()){
                conexion();
                login=new LoginFragment();
                ((NavigationHost) getActivity()).navigateTo(login,false);

            }
            else
                Toast.makeText(getContext(),"no va",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean comprobar(){
        boolean comproba;
        comproba=true;
        String Buscar= resultadoBuscar.getText().toString().trim();
        if(Buscar.isEmpty()){

            resultadoBuscar.setError("Debe seleccionar una fecha.");
            comproba=false;
        }
        else
        {
            resultadoBuscar.setError(null);
        }
        return comproba;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }

    private void  conexion(){
        mAuth.createUserWithEmailAndPassword(personas.getEmail(), personas.getPass())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            agregarDatos(personas, uid);
                            uploadFile(imag, uid);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void agregarDatos(Usuario p, String uid){

        bbdd.child(uid).setValue(p);
    }

    private void uploadFile(final Imagenes imag, final String uid) {
        if (mImageUri != null) {
            final String ahora = ""+System.currentTimeMillis();
            String rutaImagen = "prueba2/"+ahora;
            final StorageReference fileReference = storageRef.child(rutaImagen);

            uploadTask = (UploadTask) fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            bbdd.child(uid).child("imagenes").child("img1").child("nombre").setValue(ahora);
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
}

