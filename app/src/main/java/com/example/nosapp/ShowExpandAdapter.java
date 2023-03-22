package com.example.nosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowExpandAdapter extends RecyclerView.Adapter<ShowExpandAdapter.ShowVH> {

    private ArrayList<Shows> showsList;
    Shows show;
    Boolean expandable;


    public ShowExpandAdapter(ArrayList<Shows> showsList) {
        this.showsList = showsList;
        boolean expandable = false;
    }


    @NonNull
    @Override
    public ShowVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new ShowVH(view);
        }



    @Override
    public void onBindViewHolder(@NonNull ShowVH holder, int position) {
        Shows shows = showsList.get(position);
        holder.showname.setText(Shows.getShowName());
        holder.channel.setText(Shows.getChannel());
        holder.start.setText((Shows.getStartDate()));
        holder.end.setText((Shows.getEndDate()));
        holder.seasons.setText(Integer.toString(Shows.getSeasons()));
        holder.episodes.setText(Integer.toString(Shows.getEpisodes()));
        holder.synopsis.setText(Shows.getSynopsis());

        boolean isExpandable = Shows.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }


    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public class ShowVH extends RecyclerView.ViewHolder {
        public TextView showname, channel, start, end, seasons, episodes, synopsis;
        LinearLayout linearlayout;
        RelativeLayout expandableLayout;

        public ShowVH (@NonNull View itemView) {
            super(itemView);

            showname = itemView.findViewById(R.id.textShowName2);
            channel = itemView.findViewById(R.id.editChannel2);
            start = itemView.findViewById(R.id.editStartDate2);
            end = itemView.findViewWithTag(R.id.editEndDate2);
            seasons = itemView.findViewById(R.id.editSeasons2);
            episodes = itemView.findViewById(R.id.editEpisode2);
            synopsis = itemView.findViewById(R.id.editSynopsis);

            linearlayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    show = showsList.get(getAdapterPosition());
                    show.setExpandable(!Shows.isExpandable());
                    notifyItemChanged(getAdapterPosition());

                }
            });


        }
    }
}

