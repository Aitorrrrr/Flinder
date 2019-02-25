package com.clasemanel.flinder.Registro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clasemanel.flinder.Modelo.Usuario;
import com.clasemanel.flinder.NavigationHost;
import com.clasemanel.flinder.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro1 extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String PASSWORD_PATTERN = ("^" +
            "(?=.*[0-9])" +         //Por lo menos un número
            "(?=.*[a-z])" +         //Por lo menos una letra Minuscula
            "(?=.*[A-Z])" +         //Por lo menos una letra Majúscula
            "(?=.*[a-zA-Z])" +      //Alguna letra
            //"(?=.*[@#$%^&+=])" +    //Algún caracter especial
            "(?=\\S+$)" +           //Sin espacio en blanco
            ".{4,20}" +               //Tiene que ser entre 4 y 20 caracteres
            "$");

    private EditText nombreUsuario;
    private EditText contrasenya;
    private EditText repetirContrasenya;
    private EditText email;
    private Button siguiente;

    private Registro2 registro2;

    public Registro1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registro1, container, false);

        nombreUsuario = v.findViewById(R.id.et_nombreUser_reg1);
        contrasenya = v.findViewById(R.id.et_password_reg1);
        repetirContrasenya = v.findViewById(R.id.et_repetirPassword_reg1);
        email = v.findViewById(R.id.et_email_reg1);
        siguiente = v.findViewById(R.id.btn_siguiente_reg1);
        siguiente.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_siguiente_reg1)
        {
            validarTodo();
        }
    }



    public boolean validarEmail()
    {
        String emailIntroducido = email.getText().toString().trim();
        if (emailIntroducido.isEmpty())
        {
            email.setError(getString(R.string.espacioEnBlanco));
            return false;
        }
        else {
            Pattern p = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}@(floridauniversitaria\\.es|florida\\-uni\\.es)");
            Matcher m = p.matcher(emailIntroducido);
            boolean b = m.matches();
            if(b) {
                email.setError(null);
                return true;
            }else {
                email.setError(getString(R.string.correoInvalido));
                return false;
            }
        }
    }

    public boolean validarContrasenya()
    {

        String contrasenya1 = contrasenya.getText().toString();
        String contrasenya2 = repetirContrasenya.getText().toString();
        boolean patternCorrecto = validarElPatternDeLaContrasenya(contrasenya1);

        if (contrasenya1.isEmpty())
        {
            contrasenya.setError(getString(R.string.espacioEnBlanco));
            return false;
        }
        else if (!contrasenya1.equals(contrasenya2))
        {
            contrasenya.setError(getString(R.string.noCoincide));
            return false;
        }
        else if (patternCorrecto==false){
            contrasenya.setError(getString(R.string.patternInvalido));
            return false;
        }
        else {
            contrasenya.setError(null);
            return true;
        }
    }

    public boolean validarElPatternDeLaContrasenya(String contrasenya)
    {
        Pattern p = Pattern.compile(PASSWORD_PATTERN);
        Matcher m = p.matcher(contrasenya);
        boolean b = m.matches();
        return b;
    }

    public boolean validarNombreUsuario()
    {
        String nombre = nombreUsuario.getText().toString();
        if (nombre.isEmpty())
        {
            nombreUsuario.setError(getString(R.string.espacioEnBlanco));
            return false;
        }
        else
        {
            nombreUsuario.setError(null);
            return true;
        }
    }

    public void validarTodo()
    {
        boolean okNombre = validarNombreUsuario();
        boolean okEmail = validarEmail();
        boolean okContrasenya = validarContrasenya();


        if (okNombre && okContrasenya && okEmail)
        {
            Usuario userAux = new Usuario();
            String nom=nombreUsuario.getText().toString();
            userAux.setNombreUsuario(nom);
            String txtemail=email.getText().toString();
            userAux.setEmail(txtemail);
            String pass=contrasenya.getText().toString();
            userAux.setPass(pass);

            registro2 = Registro2.newInstance(userAux);
            ((NavigationHost) getActivity()).navigateTo(registro2, true);
        }
        else
        {
            Toast.makeText(getContext(), getString(R.string.comprobacion), Toast.LENGTH_LONG).show();
        }

    }
}
