package com.example.nosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteClipAdapter extends RecyclerView.Adapter {
    private ArrayList<FavoriteClip> favoriteClipArrayList;
    private View.OnClickListener mOnItemClickListener;
    private Context context;

    public class FavoriteClipViewHolder extends RecyclerView.ViewHolder {

        public TextView textclipName;

        public FavoriteClipViewHolder(@NonNull View itemView) {
            super(itemView);
            textclipName = itemView.findViewById(R.id.textClipName);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public TextView getFavoriteClipTextView() {
            return textclipName;
        }

    }
    public FavoriteClipAdapter(ArrayList<FavoriteClip> arrayList, Context context) {
        favoriteClipArrayList = arrayList;
        context = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_clip_item, parent, false);
        return new FavoriteClipViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FavoriteClipViewHolder cvh = (FavoriteClipViewHolder) holder;
        cvh.getFavoriteClipTextView().setText(favoriteClipArrayList.get(position).getclipName());

    }

    @Override
    public int getItemCount() {return favoriteClipArrayList.size();}
}
