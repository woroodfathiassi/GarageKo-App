package com.example.garageko;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class TrackMyCarActivity extends AppCompatActivity {

    RecyclerView repairStatusRV;
    ArrayList<RepairStatusModel> repairList;
    RepairStatusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track_my_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        repairStatusRV = findViewById(R.id.idRVRepairStatus);

        // Sample data
        repairList = new ArrayList<>();
        repairList.add(new RepairStatusModel("Worood Fathi Assi and my name is worood assis Car Received", true, false, false));
        repairList.add(new RepairStatusModel("Brake System Maintenance", false, true, false));
        repairList.add(new RepairStatusModel("Cooling System Maintenance", false, true, true));
        repairList.add(new RepairStatusModel("Suspension Repairs", true, false, false));
        repairList.add(new RepairStatusModel("Air Filter Replacement", false, false, true));
        repairList.add(new RepairStatusModel("Transmission Repair", false, true, false));
        repairList.add(new RepairStatusModel("Oil Change", true, false, false));

        // Set up Adapter
        adapter = new RepairStatusAdapter(this, repairList);

        // Set up StaggeredGridLayoutManager
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        repairStatusRV.setLayoutManager(layoutManager);
        repairStatusRV.setAdapter(adapter);
    }
}