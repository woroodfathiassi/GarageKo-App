package com.example.garageko;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GarageOwnerBillingManageScreen extends AppCompatActivity implements CarAdapter.OnAddToMaintainClickListener {

    @SuppressLint("MissingInflatedId")
    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_owner_billing_manage_screen);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carList = new ArrayList<>();
        carAdapter = new CarAdapter(carList, "Manage", this);
        recyclerView.setAdapter(carAdapter);


        // Fetch data from the server
        fetchDataFromServer();
        setupBottomBar();
    }

    private void fetchDataFromServer() {
        String url = "http://10.0.2.2/api/getCarsForBillings.php"; // Use 10.0.2.2 for localhost in the emulator
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            carList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject carObject = response.getJSONObject(i);
                                String name = carObject.getString("name");
                                String brand = carObject.getString("brand");
                                String imageUrl = carObject.getString("image"); // Ensure your image data is handled correctly
                                int customerId = carObject.getInt("customer_id");

                                // Assuming a Car constructor: Car(String name, String brand, int imageId)
                                carList.add(new Car(name, brand, R.drawable.hyundai_getz)); // Use placeholders or download images
                            }
                            carAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("GarageOwnerRequests", "JSON parsing error: " + e.getMessage());
                            Toast.makeText(GarageOwnerBillingManageScreen.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GarageOwnerRequests", "Volley error: " + error.getMessage());
                        Toast.makeText(GarageOwnerBillingManageScreen.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }

    @Override
    public void onAddToMaintainClick(Car car) {

    }
    private void setupBottomBar() {

        findViewById(R.id.homeIcon).setOnClickListener(v -> {
            Intent intent = new Intent(this, GarageOwnerHomeScreen.class);
            startActivity(intent);
        });

        findViewById(R.id.manageCarsIcon).setOnClickListener(v -> {
            Intent intent = new Intent(this, GarageOwnerCarsManageScreen.class);
            startActivity(intent);
        });

        findViewById(R.id.manageRequestsIcon).setOnClickListener(v -> {
            Intent intent = new Intent(this, GarageOwnerRequestsManageScreen.class);
            startActivity(intent);
        });

        findViewById(R.id.billingIcon).setOnClickListener(v -> {
            Intent intent = new Intent(this, GarageOwnerBillingManageScreen.class);
            startActivity(intent);
        });

        findViewById(R.id.ratesIcon).setOnClickListener(v -> {
            showCustomMenu(); // Show your menu as a dialog
        });

        findViewById(R.id.manageCustomersButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, GarageOwnerRequestsManageScreen.class);
            startActivity(intent);
        });
    }
    private void showCustomMenu() {
        // Inflate the menu layout
        View menuView = getLayoutInflater().inflate(R.layout.navigation_menu_g, null);

        // Create a dialog to show the menu
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(menuView);

        // Handle menu item clicks
        menuView.findViewById(R.id.menu_item_2).setOnClickListener(v -> {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        // Optional: Customize dialog window size
        dialog.getWindow().setLayout(800, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void fillRequests(){
        String url = getString(R.string.abd_url)+"carRequest.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                JSONArray data = response.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject car = data.getJSONObject(i);
                                    String name = car.getString("name");
                                    String brand = car.getString("brand");
                                    int request_id = car.getInt("request_id");
                                    carList.add(new Car(name, brand, R.drawable.bmw_x5, request_id));
                                }
                                carAdapter = new CarAdapter(carList,"Manage");
                                recyclerView.setAdapter(carAdapter);
                            } else {
                                String message = response.getString("message");
                                Toast.makeText(GarageOwnerBillingManageScreen.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GarageOwnerBillingManageScreen.this, "JSON Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GarageOwnerBillingManageScreen.this, "Volley error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
