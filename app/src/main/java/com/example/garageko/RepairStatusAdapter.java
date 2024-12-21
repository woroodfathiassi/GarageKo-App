package com.example.garageko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RepairStatusAdapter extends RecyclerView.Adapter<RepairStatusAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RepairStatusModel> repairList;

    public RepairStatusAdapter(Context context, ArrayList<RepairStatusModel> repairList) {
        this.context = context;
        this.repairList = repairList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_service_tracking_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RepairStatusModel repairStatus = repairList.get(position);

        // Set title
        holder.titleTextView.setText(repairStatus.getTitle());

        // Manage visibility and colors based on status
        if (repairStatus.isCompleted()) {
            holder.completedTextView.setVisibility(View.VISIBLE);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.green_light));
        } else {
            holder.completedTextView.setVisibility(View.GONE);
        }

        if (repairStatus.isAwaitingParts()) {
            holder.awaitingPartsTextView.setVisibility(View.VISIBLE);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.orange_light));
        } else {
            holder.awaitingPartsTextView.setVisibility(View.GONE);
        }

        if (repairStatus.isInProgress()) {
            holder.inProgressTextView.setVisibility(View.VISIBLE);
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.blue_light));
        } else {
            holder.inProgressTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return repairList.size();
    }

    // ViewHolder Class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, completedTextView, awaitingPartsTextView, inProgressTextView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            completedTextView = itemView.findViewById(R.id.completedTextView);
            awaitingPartsTextView = itemView.findViewById(R.id.awaitingPartsTextView);
            inProgressTextView = itemView.findViewById(R.id.inProgressTextView);
            cardView = (CardView) itemView;
        }
    }
}


//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//
//import java.util.ArrayList;
//
//public class RepairStatusAdapter extends ArrayAdapter<RepairStatusModel> {
//
//    public RepairStatusAdapter(Context context, ArrayList<RepairStatusModel> repairList) {
//        super(context, 0, repairList);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_service_tracking_list_item, parent, false);
//        }
//
//        // Fetch the current repair item
//        RepairStatusModel repairStatus = getItem(position);
//
//        // Find views
//        TextView titleTV = convertView.findViewById(R.id.titleTextView);
//        TextView completedTV = convertView.findViewById(R.id.completedTextView);
//        TextView awaitingPartsTV = convertView.findViewById(R.id.awaitingPartsTextView);
//        TextView inProgressTV = convertView.findViewById(R.id.inProgressTextView);
//        CardView cardView = (CardView) convertView;
//
//        // Set data
//        titleTV.setText(repairStatus.getTitle());
//
//        // Handle visibility and card border based on the status
//        if (repairStatus.isCompleted()) {
//            completedTV.setVisibility(View.VISIBLE);
//            cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.green_light));
//        } else {
//            completedTV.setVisibility(View.GONE);
//        }
//
//        if (repairStatus.isAwaitingParts()) {
//            awaitingPartsTV.setVisibility(View.VISIBLE);
//            cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.orange_light));
//        } else {
//            awaitingPartsTV.setVisibility(View.GONE);
//        }
//
//        if (repairStatus.isInProgress()) {
//            inProgressTV.setVisibility(View.VISIBLE);
//            cardView.setCardBackgroundColor(getContext().getResources().getColor(R.color.blue_light));
//        } else {
//            inProgressTV.setVisibility(View.GONE);
//        }
//
//        return convertView;
//    }
//}


