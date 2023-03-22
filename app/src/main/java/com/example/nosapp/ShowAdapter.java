package com.example.nosapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAdapter extends RecyclerView.Adapter {

    private Context pcontext;
    private ArrayList<Shows> showList;
    private View.OnClickListener mOnItemClickListener;

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageLogo;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            imageLogo = itemView.findViewById(R.id.show_image);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public ImageView getLogoImageview() {
            return imageLogo;
        }
    }

    public ShowAdapter(ArrayList<Shows> arrayList, Context context) {
        pcontext = context;
        showList = arrayList;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(pcontext);
        View view = inflater.inflate(R.layout.shows_item, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ShowViewHolder svh = (ShowViewHolder) holder;
        Shows show = showList.get(position);
        svh.getLogoImageview().setImageResource(show.getShowLogo());




    }

    @Override
    public int getItemCount() {
        return showList.size();
    }
}
