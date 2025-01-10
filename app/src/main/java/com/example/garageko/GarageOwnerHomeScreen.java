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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GarageOwnerHomeScreen extends AppCompatActivity implements CarAdapter.OnAddToMaintainClickListener {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_owner_home_screen);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carList = new ArrayList<>();
        carAdapter = new CarAdapter(carList, "Add to Maintain", this);
        recyclerView.setAdapter(carAdapter);

        // Fetch data from the server
        fetchDataFromServer();
        setupBottomBar();
    }

    @Override
    public void onAddToMaintainClick(Car car) {
        updateCarStatus(car.getCarId());
    }

    private void fetchDataFromServer() {
        String url = "http://10.0.2.2/api/getDeliveredCars.php"; // Use 10.0.2.2 for localhost in the emulator
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        carList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject carObject = response.getJSONObject(i);
                            int carId = carObject.getInt("car_id");
                            String name = carObject.getString("name");
                            String brand = carObject.getString("brand");

                            // Assuming Car constructor: Car(int carId, String name, String brand, int imageId)
                            carList.add(new Car(carId, name, brand, R.drawable.hyundai_getz)); // Placeholder image
                        }
                        carAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e("GarageOwnerHomeScreen", "JSON parsing error: " + e.getMessage());
                        Toast.makeText(GarageOwnerHomeScreen.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("GarageOwnerHomeScreen", "Volley error: " + error.getMessage());
                    Toast.makeText(GarageOwnerHomeScreen.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(jsonArrayRequest);
    }

    private void updateCarStatus(int carId) {
        String url = "http://10.0.2.2/api/updateCarStatusToMaintain.php"; // Endpoint for updating the car status
        RequestQueue queue = Volley.newRequestQueue(this);

        // Prepare the parameters
        Map<String, String> params = new HashMap<>();
        params.put("car_id", String.valueOf(carId));
        Log.d("UpdateCarStatus", "URL: " + url);
        Log.d("UpdateCarStatus", "Params: " + params);

        // Send the request
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    // Log the response
                    Log.d("UpdateCarStatus", "Response: " + response.toString());

                    try {
                        if (response.getBoolean("success")) {
                            Toast.makeText(this, "Car status updated successfully.", Toast.LENGTH_SHORT).show();
                            addToMaintenance(carId);
                            fetchDataFromServer();  // This will refresh the data

                        } else {
                            String error = response.has("error") ? response.getString("error") : "Unknown error";
                            Log.e("UpdateCarStatus", "Error from server: " + error);
                            Toast.makeText(this, "Failed to update car status: " + error, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("UpdateCarStatus", "JSON parsing error: " + e.getMessage());
                        Toast.makeText(this, "Error parsing server response.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // Log the error
                    Log.e("UpdateCarStatus", "Volley error: " + error.toString());
                    Toast.makeText(this, "Error communicating with the server.", Toast.LENGTH_SHORT).show();
                });

        queue.add(postRequest);
    }

    private void addToMaintenance(int carId) {
        String url = "http://10.0.2.2/api/addToMaintain.php"; // Endpoint for adding to the maintenance table
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<>();
        params.put("car_id", String.valueOf(carId));

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            Toast.makeText(this, "Car added to maintenance.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Failed to add car to maintenance.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("GarageOwnerHomeScreen", "Response parsing error: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("GarageOwnerHomeScreen", "Error: " + error.getMessage());
                    Toast.makeText(this, "Error communicating with server in maintain.", Toast.LENGTH_SHORT).show();
                });

        queue.add(postRequest);
    }

    private void setupBottomBar() {
        findViewById(R.id.homeIcon).setOnClickListener(v -> recreate());

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

        // Adding action for ratesIcon
        findViewById(R.id.ratesIcon).setOnClickListener(v -> {
            showCustomMenu(); // Show your menu as a dialog
        });

        findViewById(R.id.manageCustomersButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, GarageOwnerCarsManageScreen.class);
            startActivity(intent);
        });
    }

    // Method to show the custom menu
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

}
