package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class TrackMyCarActivity extends AppCompatActivity {

    RecyclerView repairStatusRV;
    ArrayList<RepairStatusModel> repairList;
    RepairStatusAdapter adapter;

    private DrawerLayout drawerLayout;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;

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


        initializeUIComponents();


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

        // Menu icon click listener to open/close the drawer
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });

        // Set click listeners
        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrackMyCarActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

        menuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MenuClick", "Mcenu Item 2 licked");
                System.out.println("Logout!"); // Prints to logcat
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });

    }

    private void initializeUIComponents() {
        repairStatusRV = findViewById(R.id.idRVRepairStatus);

        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);

    }
}