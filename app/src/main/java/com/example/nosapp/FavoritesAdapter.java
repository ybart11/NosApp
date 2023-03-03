package com.example.nosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter {

    private Context pcontext;
    private ArrayList<Favorites> favoritesList;
    private View.OnClickListener mOnItemClickListener;

    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageLogo;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLogo = itemView.findViewById(R.id.show_image);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public ImageView getLogoImageview() {
            return imageLogo;
        }
    }

    public FavoritesAdapter(ArrayList<Favorites> arrayList, Context context) {
        pcontext = context;
        favoritesList = arrayList;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(pcontext);
        View view = inflater.inflate(R.layout.show_item, parent, false);
        return new FavoritesAdapter.FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FavoritesAdapter.FavoritesViewHolder fvh = (FavoritesViewHolder) holder;
        Favorites favorites = favoritesList.get(position);
        fvh.getLogoImageview().setImageResource(favorites.getLogo());

    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }
}
