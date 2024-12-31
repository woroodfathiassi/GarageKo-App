package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class CFeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText feedbackEditText;
    private Button submitButton;

    private DrawerLayout drawerLayout;
    private TextView menuItem1;
    private TextView menuItem2;
    private ImageView menuIcon;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cfeedback);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeUIComponents();

        submitButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            String feedback = feedbackEditText.getText().toString();

            if (feedback.isEmpty()) {
                Toast.makeText(CFeedbackActivity.this, "Please provide your feedback", Toast.LENGTH_SHORT).show();
            } else {
                // Print the rating and feedback in the Logcat
                Log.d("FeedbackActivity", "Rating: " + rating + ", Feedback: " + feedback);

                // Logic to save feedback (e.g., send to server or save locally)
                Toast.makeText(CFeedbackActivity.this, "Thank you for your feedback!", Toast.LENGTH_SHORT).show();

                // Clear inputs
                ratingBar.setRating(0);
                feedbackEditText.setText("");
            }
        });


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

        menuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CFeedbackActivity.this, ProfileActivity.class);
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
        ratingBar = findViewById(R.id.ratingBar);
        feedbackEditText = findViewById(R.id.et_feedback);
        submitButton = findViewById(R.id.btn_submit_feedback);

        menuItem1 = findViewById(R.id.menu_item_1);
        menuItem2 = findViewById(R.id.menu_item_2);
        drawerLayout = findViewById(R.id.drawer_layout);
        menuIcon = findViewById(R.id.imageView);

        backButton = findViewById(R.id.imageButtonBack);
    }
}