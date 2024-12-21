package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GarageOwnerBillingManageScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_owner_billing_manage_screen);
        try {

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate data
        carList = new ArrayList<>();
        carList.add(new Car("Ahmad Fadi", "Hyundai Getz", R.drawable.hyundai_getz));
        carList.add(new Car("Khalid Fadi", "BMW X5", R.drawable.bmw_x5));
        carList.add(new Car("Shadi Radi", "Kia Sportage", R.drawable.kia_sportage));
        carList.add(new Car("Murad Muslih", "MG 4", R.drawable.mg_4));
        carList.add(new Car("Yasmeen Masri", "Hyundai ix 35", R.drawable.hyundai_ix35));

        // Set adapter
        carAdapter = new CarAdapter(carList,"Manage");
        recyclerView.setAdapter(carAdapter);

        // Setup bottom bar
        setupBottomBar();
        } catch (Exception e) {
            Log.e("GarageOwnerHomeScreen", "Error initializing RecyclerView", e);
        }
        ImageButton menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(GarageOwnerBillingManageScreen.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.ic_menu, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(item -> {
//                switch (item.getItemId()) {
//                    case R.id.menu_home:
//                        Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.menu_manage_cars:
//                        Toast.makeText(this, "Manage Cars clicked", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.menu_manage_requests:
//                        Toast.makeText(this, "Manage Requests clicked", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.menu_billings_payment:
//                        Toast.makeText(this, "Billings Payment clicked", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.menu_rates:
//                        Toast.makeText(this, "Rates clicked", Toast.LENGTH_SHORT).show();
//                        return true;
//                    default:
//                        return false;
//                }
//
//            });
            popupMenu.show();
        });

    }

    private void setupBottomBar() {
        try {
            findViewById(R.id.homeIcon).setOnClickListener(v -> {
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, GarageOwnerHomeScreen.class);
                startActivity(intent);
            });

            findViewById(R.id.manageCarsIcon).setOnClickListener(v -> {
                        Intent intent = new Intent(this, GarageOwnerCarsManageScreen.class);
                        startActivity(intent);
                    }
            );

            findViewById(R.id.manageRequestsIcon).setOnClickListener(v -> {
                Intent intent = new Intent(this, GarageOwnerRequestsManageScreen.class);
                startActivity(intent);
            });

            findViewById(R.id.billingIcon).setOnClickListener(v -> {
                Toast.makeText(this, "You are already on Billings screen", Toast.LENGTH_SHORT).show();

            });

            findViewById(R.id.ratesIcon).setOnClickListener(v -> {
                Intent intent = new Intent(this, CustomerRateActivity.class);
                startActivity(intent);
            });
        } catch (Exception e) {
            Log.e("GarageOwnerCarsManageScreen", "Error setting up bottom bar", e);
        }
    }
}