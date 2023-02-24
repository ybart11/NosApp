package com.example.nosapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShowAdapter showAdapter;
    private List<Show> showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvShows);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        showList = new ArrayList<>();

        showAdapter = new ShowAdapter(this, showList);
        recyclerView.setAdapter(showAdapter);

        loadShows();
    }

    private void loadShows() {

        showList.add(new Show("Show 1", R.drawable.jimmy_neutron_logo));
        showList.add(new Show("Show 2", R.drawable.kim_possible_logo));
        showList.add(new Show("Show 3", R.drawable.the_fairly_oddparents_logo));

        showAdapter.notifyDataSetChanged();
    }
}
