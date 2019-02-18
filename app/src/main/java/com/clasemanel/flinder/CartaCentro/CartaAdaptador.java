package com.clasemanel.flinder.CartaCentro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.clasemanel.flinder.R;

import java.util.ArrayList;

public class CartaAdaptador extends RecyclerView.Adapter<CartaAdaptador.CartasCentroViewHolder> {

    private ArrayList<CartaData> arrayCartas;
    private int windowwidth;
    private int screenCenter;
    private int x_cord, y_cord, x, y;
    private int Likes = 0;

    private View containerView;

    public static class CartasCentroViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        public TextView nombre;
        public TextView localidad;
        public ImageView imagen;
        public ImageView info;
        public MaterialCardView layout;

        public Context context;
        public View v;

        public CartasCentroViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.tvNombreCardCentro);
            localidad = (TextView) itemView.findViewById(R.id.tvLocalidadCardCentro);
            imagen = (ImageView) itemView.findViewById(R.id.imageCardCentro);
            info = (ImageView) itemView.findViewById(R.id.btnInfoCardCentro);
            layout = (MaterialCardView) itemView.findViewById(R.id.layoutCardCentro);

            v = itemView;
            context = itemView.getContext();
        }

        @Override
        public void onClick(View v) {

        }
    }

    public CartaAdaptador (ArrayList<CartaData> carta){

        this.arrayCartas = carta;

    }
    @NonNull
    @Override
    public CartasCentroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        containerView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_carta_centro,viewGroup, false);

        return new CartasCentroViewHolder(containerView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull CartasCentroViewHolder holder, int i) {
        final int posicion = i;

        holder.nombre.setText(arrayCartas.get(i).getName());
        holder.localidad.setText(arrayCartas.get(i).getLocalidad());

        holder.layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x_cord = (int) event.getRawX();
                y_cord = (int) event.getRawY();

                //containerView.setX(0);
                //containerView.setY(0);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = (int) event.getX();
                        y = (int) event.getY();

                        Log.v("On touch", x + " " + y);
                        break;
                    case MotionEvent.ACTION_MOVE:

                        x_cord = (int) event.getRawX();
                        // smoother animation.
                        y_cord = (int) event.getRawY();

                        containerView.setX(x_cord - x);
                        containerView.setY(y_cord - y);

                        if (x_cord >= screenCenter) {
                            containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                            if (x_cord > (screenCenter + (screenCenter / 2))) {
                                //tvLike.setAlpha(1);
                                if (x_cord > (windowwidth - (screenCenter / 4))) {
                                    Likes = 2;
                                } else {
                                    Likes = 0;
                                }
                            } else {
                                Likes = 0;
                                //tvLike.setAlpha(0);
                            }
                            //tvUnLike.setAlpha(0);
                        } else {
                            // rotate image while moving
                            containerView.setRotation((float) ((x_cord - screenCenter) * (Math.PI / 32)));
                            if (x_cord < (screenCenter / 2)) {
                                //tvUnLike.setAlpha(1);
                                if (x_cord < screenCenter / 4) {
                                    Likes = 1;
                                } else {
                                    Likes = 0;
                                }
                            } else {
                                Likes = 0;
                                //tvUnLike.setAlpha(0);
                            }
                            //tvLike.setAlpha(0);
                        }
                        break;
                    case MotionEvent.ACTION_UP:

                        x_cord = (int) event.getRawX();
                        y_cord = (int) event.getRawY();

                        Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                        //tvUnLike.setAlpha(0);
                        //tvLike.setAlpha(0);

                        if (Likes == 0) {
                            Log.e("Event_Status :-> ", "Nothing");
                            containerView.setX(0);
                            containerView.setY(0);
                            containerView.setRotation(0);
                        } else if (Likes == 1) {
                            Log.e("Event_Status :-> ", "UNLIKE");
                            //parentView.removeView(containerView);
                            arrayCartas.remove(posicion);
                            notifyItemRemoved(posicion);
                            notifyItemRangeChanged(posicion, getItemCount());
                        } else if (Likes == 2) {
                            Log.e("Event_Status :-> ", "Liked");
                            //parentView.removeView(containerView);
                            arrayCartas.remove(posicion);
                            notifyItemRemoved(posicion);
                            notifyItemRangeChanged(posicion, getItemCount());
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayCartas.size();
    }
}
