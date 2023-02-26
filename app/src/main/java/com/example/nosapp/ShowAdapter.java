package com.example.nosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private Context context;
    private List<Show> showList;

    public ShowAdapter(Context context, List<Show> showList) {
        this.context = context;
        this.showList = showList;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.show_item, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        Show show = showList.get(position);
        holder.showImage.setImageResource(show.getLogo());
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    class ShowViewHolder extends RecyclerView.ViewHolder {


        ImageView showImage;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);

            showImage = itemView.findViewById(R.id.show_image);
        }
    }
}
