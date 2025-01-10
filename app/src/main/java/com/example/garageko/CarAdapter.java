package com.example.garageko;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;
    private String buttonText;
    private OnAddToMaintainClickListener listener;

    // Constructor
    public CarAdapter(List<Car> carList, String buttonText, OnAddToMaintainClickListener listener) {
        this.carList = carList;
        this.buttonText = buttonText;
        this.listener = listener;
    }
    public CarAdapter(List<Car> carList, String buttonText) {
        this.carList = carList;
        this.buttonText = buttonText;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        // Set data to views
        holder.ownerName.setText(car.getOwnerName());
        holder.carName.setText(car.getCarModel());
        holder.carImage.setImageResource(car.getCarImage());
        holder.addToMaintainButton.setText(buttonText);

        // Handle button click
        holder.addToMaintainButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToMaintainClick(car);
            }
        });
    }

//    @Override
//    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
//        Car car = carList.get(position);
//
//        // Set data to views
//        holder.ownerName.setText(car.getOwnerName());
//        holder.carName.setText(car.getCarModel());
//        holder.carImage.setImageResource(car.getCarImage());
//        holder.addToMaintainButton.setText(buttonText);
//
//        // Add navigation to ManageCarBillingActivity
//        holder.addToMaintainButton.setOnClickListener(v -> {
//            Context context = v.getContext();
//            Intent intent = new Intent(context, ManageCarBillingActivity.class);
//
//            // Pass car details to the next activity
//            intent.putExtra("ownerName", car.getOwnerName());
//            intent.putExtra("carModel", car.getCarModel());
//            intent.putExtra("carImage", car.getCarImage());
//            intent.putExtra("request_id", car.request_id);
//
//            context.startActivity(intent);
//        });
//    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView carImage;
        TextView ownerName, carName;
        Button addToMaintainButton;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.carImage);
            ownerName = itemView.findViewById(R.id.ownerName);
            carName = itemView.findViewById(R.id.carName);
            addToMaintainButton = itemView.findViewById(R.id.addToMaintainButton);
        }
    }

    public void updateData(List<Car> newCarList) {
        carList = newCarList;
        notifyDataSetChanged();
    }

    // Listener Interface
    public interface OnAddToMaintainClickListener {
        void onAddToMaintainClick(Car car);
    }
}
