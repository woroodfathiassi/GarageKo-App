//package com.example.garageko;
//
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class InvoiceActivity extends AppCompatActivity {
//
//    private RecyclerView recycler;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_invoice);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//
//        recycler = findViewById(R.id.invoice_recycler);
//
//        int[] imageIds = new int[Invoice.invoices.length];
//        String[] dates = new String[Invoice.invoices.length];
//        double[] prices = new double[Invoice.invoices.length];
//        String[] details = new String[Invoice.invoices.length];
//
//        for(int i = 0; i<dates.length;i++){
//            imageIds[i] = Invoice.invoices[i].getImageId();
//            dates[i] = Invoice.invoices[i].getDate();
//            prices[i] = Invoice.invoices[i].getPrice();
//            details[i] = Invoice.invoices[i].getDetail();
//        }
//
//        recycler.setLayoutManager(new LinearLayoutManager(this));
//        InvoiceAdapter adapter = new InvoiceAdapter(imageIds, dates, prices, details);
//        recycler.setAdapter(adapter);
//    }
//}


package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InvoiceActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private DrawerLayout drawerLayout;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice);

        initializeUIComponents();

        // Set padding for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int[] imageIds = new int[Invoice.invoices.length];
        String[] dates = new String[Invoice.invoices.length];
        double[] prices = new double[Invoice.invoices.length];
        String[] details = new String[Invoice.invoices.length];

        for (int i = 0; i < dates.length; i++) {
            imageIds[i] = Invoice.invoices[i].getImageId();
            dates[i] = Invoice.invoices[i].getDate();
            prices[i] = Invoice.invoices[i].getPrice();
            details[i] = Invoice.invoices[i].getDetail();
        }

        recycler.setLayoutManager(new LinearLayoutManager(this));
        InvoiceAdapter adapter = new InvoiceAdapter(this, imageIds, dates, prices, details);
        adapter.setOnItemClickListener(position ->
                Toast.makeText(getApplicationContext(), "Item " + position + " clicked!", Toast.LENGTH_SHORT).show()
        );
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
                Intent intent = new Intent(InvoiceActivity.this, ProfileActivity.class);
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
        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);

        recycler = findViewById(R.id.invoice_recycler);
    }
}
