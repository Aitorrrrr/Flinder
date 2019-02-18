package com.clasemanel.flinder.Perfil;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.clasemanel.flinder.R;

public class PerfilTab extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageView imagePerfil;
    private Button btnAjustes,btnPerfil;

    private String mParam1;
    private String mParam2;

    public PerfilTab()
    {

    }

    public static PerfilTab newInstance(String param1, String param2) {
        PerfilTab fragment = new PerfilTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil_tab, container, false);

        imagePerfil = (ImageView) v.findViewById(R.id.img_imagenPerfil_perfilTab);
        btnAjustes = (Button) v.findViewById(R.id.btn_ajustes_perfilTab);
        btnPerfil = (Button) v.findViewById(R.id.btn_perfil_perfilTab);

        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();;
    }
}
