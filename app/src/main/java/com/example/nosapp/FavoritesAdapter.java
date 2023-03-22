package com.example.nosapp;

import android.content.Context;
import android.os.Bundle;
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
    private final RecyclerViewInterface recyclerViewInterface;

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageLogo;

        public FavoritesViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageLogo = itemView.findViewById(R.id.favs_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        public ImageView getLogoImageview() {
            return imageLogo;
        }
    }

    public FavoritesAdapter(ArrayList<Favorites> arrayList, Context context,
                            RecyclerViewInterface recyclerViewInterface) {
        favoritesList = arrayList;
        pcontext = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(pcontext);
        View view = inflater.inflate(R.layout.favorites_item, parent, false);
        return new FavoritesAdapter.FavoritesViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FavoritesAdapter.FavoritesViewHolder fvh = (FavoritesAdapter.FavoritesViewHolder) holder;
        Favorites favs = favoritesList.get(position);
        String logo = favs.getLogo();
        System.out.println("Logo value: " + logo);
        if (logo != null) {
            int resId = pcontext.getResources().getIdentifier(logo, "drawable", pcontext.getPackageName());
            if (resId != 0) {
                fvh.getLogoImageview().setImageResource(resId);
            } else {
                // handle case where the image file is not found
                fvh.getLogoImageview().setImageResource(R.drawable.favlogo);
            }
        } else {
            // handle null case, for example set a default image
            fvh.getLogoImageview().setImageResource(R.drawable.favlogo);
        }
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public interface RecyclerViewInterface {

        void onResume(Bundle savedInstanceState);

        void onItemClick(int position);
    }
}
