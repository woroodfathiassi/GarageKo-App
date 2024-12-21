package com.example.garageko;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private int[] imageIds;
    private String[] services;

    // Constructor to initialize data
    public ServiceAdapter(int[] imageIds, String[] services) {
        this.imageIds = imageIds;
        this.services = services;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the card layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_service_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Set the image
        Drawable dr = ContextCompat.getDrawable(holder.cardView.getContext(), imageIds[position]);
        holder.imageView.setImageDrawable(dr);

        // Set the date
        holder.serviceTextView.setText(services[position]);

        // Set click listener (add your logic)
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your action here
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.length; // Assuming all arrays are of the same length
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView serviceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_service);
            imageView = itemView.findViewById(R.id.tool);
            serviceTextView = itemView.findViewById(R.id.TVservice);
        }
    }
}
