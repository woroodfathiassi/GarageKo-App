package com.example.garageko;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

public class WMainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Button menuItem1, menuItem2, menuItem3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wmain);

        // Initialize DrawerLayout and Toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ActionBarDrawerToggle to open/close the drawer when the hamburger icon is clicked
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.setDrawerIndicatorEnabled(false); // Disable the default hamburger icon
        toolbar.setNavigationIcon(R.drawable.custom_icon_with_size);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialize the menu items inside the drawer
        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        menuItem3 = findViewById(R.id.menu_item_3);

        // Set click listeners to close the drawer on item click
        menuItem1.setOnClickListener(v -> {
            Toast.makeText(WMainActivity.this, "Menu Item 1 clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);  // Close the drawer
        });

        menuItem2.setOnClickListener(v -> {
            Toast.makeText(WMainActivity.this, "Menu Item 2 clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);  // Close the drawer
        });

        menuItem3.setOnClickListener(v -> {
            Toast.makeText(WMainActivity.this, "Menu Item 3 clicked", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);  // Close the drawer
        });
    }
}
