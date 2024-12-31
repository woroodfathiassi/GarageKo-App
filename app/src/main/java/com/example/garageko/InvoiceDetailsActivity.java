package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InvoiceDetailsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private Button btnLeaveFeedback;
    private TextView Idate;
    private TextView Iprice;

    private DrawerLayout drawerLayout;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;
    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice_details);

        initializeUIComponents();

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        double price = intent.getDoubleExtra("price", 0.0);

        // Set the invoice details to EditTexts
        if (date != null) {
            Idate.setText(date);
        }
        Iprice.setText(String.valueOf(price));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        btnLeaveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvoiceDetailsActivity.this, CFeedbackActivity.class);
                startActivity(intent);
            }
        });

        int[] imageIds = new int[Service.invoices.length];
        String[] services = new String[Service.invoices.length];

        for(int i = 0; i<services.length;i++){
            //change the data-------------------------------------->>
            imageIds[i] = Invoice.invoices[i].getImageId();
            services[i] = Invoice.invoices[i].getDate();
        }

        recycler.setLayoutManager(new LinearLayoutManager(this));
        ServiceAdapter adapter = new ServiceAdapter(imageIds, services);
        recycler.setAdapter(adapter);

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
                Intent intent = new Intent(InvoiceDetailsActivity.this, ProfileActivity.class);
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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close this activity and return to the previous one
                finish();
            }
        });
    }

    private void initializeUIComponents() {
        recycler = findViewById(R.id.service_recycler);
        btnLeaveFeedback = findViewById(R.id.leaveFeedback);

        Idate = findViewById(R.id.Idate);
        Iprice = findViewById(R.id.Iprice);

        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);

        backButton = findViewById(R.id.imageButtonBack);
    }
}