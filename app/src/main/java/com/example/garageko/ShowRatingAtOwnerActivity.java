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
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowRatingAtOwnerActivity extends AppCompatActivity {

    private boolean IsRated = false;

    private RatingBar ratingBar;
    private TextView feedbackEditText;
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
        setContentView(R.layout.activity_show_rating_at_owner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // the intent should has a request id and costumer name
        Intent intent = getIntent();
        int requestId = intent.getIntExtra("requestId", 0);
        Log.d("woroodassi123", requestId+"");

        initializeUIComponents();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close this activity and return to the previous one
                finish();
            }
        });

        getFeedback(1);
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

    private void getFeedback(int requestId){
        String url = getString(R.string.GET_CFEEDBACK);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject userJson = new JSONObject(response);
                        if (userJson != null) {
                            int rating = userJson.getInt("rating");
                            String notes = userJson.optString("notes");
                            ratingBar.setRating(rating);
                            feedbackEditText.setText(notes);
                            IsRated = true;
                        } else {
                            ratingBar.setRating(0);
                            feedbackEditText.setText("Feedback not provided yet.");
                            IsRated = false;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ShowRatingAtOwnerActivity.this, "Error parsing JSON", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    Toast.makeText(ShowRatingAtOwnerActivity.this, "Volley Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("request_id", String.valueOf(requestId));
                return params;
            }
        };
        queue.add(stringRequest);
    }
}