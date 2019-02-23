package com.clasemanel.flinder.CartaCentro;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clasemanel.flinder.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Spot> spots;
    private StorageReference mStorage;
    private Context auxContext;

    public CardStackAdapter(Context context, List<Spot> spots) {
        this.inflater = LayoutInflater.from(context);
        this.spots = spots;
        this.mStorage = FirebaseStorage.getInstance().getReference().child("prueba2");
        this.auxContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.viewholder_carta_centro, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Spot spot = spots.get(position);
        holder.name.setText(spot.name);
        holder.city.setText(spot.city);

        mStorage.child(spot.getUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(auxContext)
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .into(holder.image);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), spot.name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return spots.size();
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView city;
        ImageView image;
        ViewHolder(View view) {
            super(view);
            this.name = view.findViewById(R.id.item_name);
            this.city = view.findViewById(R.id.item_city);
            this.image = view.findViewById(R.id.item_image);
        }
    }

}
