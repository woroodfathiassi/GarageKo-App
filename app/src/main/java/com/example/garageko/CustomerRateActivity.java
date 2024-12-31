package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CustomerRateActivity extends AppCompatActivity {

    private TextView customerName, notes;
    private ImageView customerImage;
    private LinearLayout ratingStars;
    private Button previousButton, nextButton;

    private List<Customer> customerList;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rate);

        // Initialize views
        customerName = findViewById(R.id.customerName);
        notes = findViewById(R.id.notes);
        customerImage = findViewById(R.id.customerImage);
        ratingStars = findViewById(R.id.ratingStars);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        // Load customer data
        loadCustomers();
        setupBottomBar();

        // Show first customer
        displayCustomer(currentIndex);

        // Handle navigation buttons
        previousButton.setOnClickListener(v -> {
            if (currentIndex > 0) {
                currentIndex--;
                displayCustomer(currentIndex);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (currentIndex < customerList.size() - 1) {
                currentIndex++;
                displayCustomer(currentIndex);
            }
        });
        ImageButton menuButton = findViewById(R.id.menuButton);
//        menuButton.setOnClickListener(v -> {
//            PopupMenu popupMenu = new PopupMenu(CustomerRateActivity.this, v);
//            popupMenu.getMenuInflater().inflate(R.menu.ic_menu, popupMenu.getMenu());
//            popupMenu.show();
//            });

        }

    private void loadCustomers() {
        customerList = new ArrayList<>();
        customerList.add(new Customer("Ali Ahmed", 5, "Excellent service!", R.drawable.home));
        customerList.add(new Customer("Sara Omar", 4, "Good, but could improve.", R.drawable.home));
        customerList.add(new Customer("Omar Khalid", 3, "Average experience.", R.drawable.home));
        customerList.add(new Customer("Mona Yusuf", 2, "Not satisfied.", R.drawable.home));
        customerList.add(new Customer("Yusuf Nader", 1, "Terrible service.", R.drawable.home));
    }

    private void displayCustomer(int index) {
        Customer customer = customerList.get(index);

        // Update name, notes, and image
        customerName.setText(customer.getName());
        notes.setText(customer.getNotes());
        customerImage.setImageResource(customer.getImageResId());

        // Update rating stars
        ratingStars.removeAllViews();
        for (int i = 0; i < customer.getRating(); i++) {
            ImageView star = new ImageView(this);
            star.setLayoutParams(new LinearLayout.LayoutParams(30, 30));
            star.setImageResource(R.drawable.home);
            ratingStars.addView(star);
        }
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
                Intent intent = new Intent(this, GarageOwnerBillingManageScreen.class);
                startActivity(intent);
            });

            findViewById(R.id.ratesIcon).setOnClickListener(v -> {
                Toast.makeText(this, "You are already on Rates screen", Toast.LENGTH_SHORT).show();

            });
        } catch (Exception e) {
            Log.e("GarageOwnerCarsManageScreen", "Error setting up bottom bar", e);
        }
    }

}