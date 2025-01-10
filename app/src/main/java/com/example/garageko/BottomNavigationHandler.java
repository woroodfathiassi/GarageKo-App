package com.example.garageko;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationHandler {

    /**
     * Sets up the BottomNavigationView for the given activity.
     *
     * @param activity             The activity where the BottomNavigationView is used.
     * @param bottomNavigationView The BottomNavigationView instance.
     */
    public static void setupBottomNavigation(Activity activity, BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            handleMenuItemClick(item.getItemId(), activity);
            return true;
        });
    }

    /**
     * Handles menu item click events.
     *
     * @param itemId  The ID of the selected menu item.
     * @param activity The current activity (useful for navigation actions).
     */
    private static void handleMenuItemClick(int itemId, Activity activity) {
        if (itemId == R.id.nav_home && !(activity instanceof InvoiceActivity)) {
            activity.startActivity(new Intent(activity, InvoiceActivity.class));
        } else if (itemId == R.id.nav_profile && !(activity instanceof TrackMyCarActivity)) {
            activity.startActivity(new Intent(activity, TrackMyCarActivity.class));
        } else if (itemId == R.id.nav_settings && !(activity instanceof InvoiceActivity)) {
            activity.startActivity(new Intent(activity, InvoiceActivity.class));
        } else {
            Log.d("BottomNavigationHandler", "Selected menu item does not require navigation");
        }
    }

}
