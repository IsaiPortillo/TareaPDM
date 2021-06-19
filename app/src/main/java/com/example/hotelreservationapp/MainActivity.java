package com.example.hotelreservationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.hotelreservationapp.adapter.RecentsAdapter;
import com.example.hotelreservationapp.model.RecentsData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recentRecycle;
    RecentsAdapter recentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<RecentsData> recentsDataList = new ArrayList<>();
        recentsDataList.add(new RecentsData("Underwater Bedroom","BR1","$120", R.drawable.habitacion1));
        recentsDataList.add(new RecentsData("Besto Bedroom","BR2","$300", R.drawable.habitacion2));
        recentsDataList.add(new RecentsData("Underwater Bedroom","BR3","$125", R.drawable.habitacion1));
        recentsDataList.add(new RecentsData("Besto Bedroom 2","BR4","$400", R.drawable.habitacion2));

        setRecentRecycle(recentsDataList);


    }

    private void setRecentRecycle(List<RecentsData> recentsDataList){

        recentRecycle = findViewById(R.id.recent_recycle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        recentRecycle.setLayoutManager(layoutManager);
        recentsAdapter = new RecentsAdapter(this, recentsDataList);
        recentRecycle.setAdapter(recentsAdapter);

    }

}