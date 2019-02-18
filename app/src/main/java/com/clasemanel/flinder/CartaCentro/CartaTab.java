package com.clasemanel.flinder.CartaCentro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.clasemanel.flinder.R;

import java.util.ArrayList;

public class CartaTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout parent;
    private Context context;
    private RecyclerView rc1;
    private CartaAdaptador adapter;
    private RecyclerView.LayoutManager rlm;
    private ArrayList<CartaData> arrayCarta;

    public CartaTab() {
        // Required empty public constructor
    }

    public static CartaTab newInstance(String param1, String param2) {
        CartaTab fragment = new CartaTab();
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
        View v = inflater.inflate(R.layout.fragment_carta_tab, container, false);
        // parent = (RelativeLayout) v.findViewById(R.id.relative_container);

        rc1 = (RecyclerView) v.findViewById(R.id.recycler_cartas_cartaCentro);
        rlm = new LinearLayoutManager(v.getContext());
        rc1.setLayoutManager(rlm);

        CartaData c1 = new CartaData("pepe","catarroja");
        CartaData c2 = new CartaData("aitor","picassent");
        ArrayList<CartaData> arrrayAux = new ArrayList<>();
        arrrayAux.add(c1);
        arrrayAux.add(c2);
        adapter = new CartaAdaptador(arrrayAux);
        rc1.setAdapter(adapter);

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
}
