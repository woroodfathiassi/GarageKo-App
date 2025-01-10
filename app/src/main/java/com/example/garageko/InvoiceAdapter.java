package com.example.garageko;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private Context context;
    private int[] imageIds;
    private String[] dates;
    private double[] prices;
    private String[] details;
    private OnItemClickListener listener;
    private int[] requestIds;

    // Constructor
    public InvoiceAdapter(Context context, int[] imageIds, String[] dates, double[] prices, String[] details,int [] requestIds) {
        this.context = context;
        this.imageIds = imageIds;
        this.dates = dates;
        this.prices = prices;
        this.details = details;
        this.requestIds = requestIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_invoice_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drawable dr = ContextCompat.getDrawable(context, imageIds[position]);
        holder.imageView.setImageDrawable(dr);
        holder.dateTextView.setText(dates[position]);
        holder.priceTextView.setText("$ " + prices[position]);
        holder.detailTextView.setText(details[position]);

        holder.cardView.setOnClickListener(v -> {
            // Start InvoiceDetailsActivity with the clicked item's data
            Intent intent = new Intent(context, InvoiceDetailsActivity.class);
            intent.putExtra("requestId", requestIds[position]);
            intent.putExtra("dates", dates[position]);
            intent.putExtra("prices", prices[position]+"");

            // Ensure proper context is used to start the activity
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return dates.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView dateTextView;
        TextView priceTextView;
        TextView detailTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_service);
            imageView = itemView.findViewById(R.id.tool);
            dateTextView = itemView.findViewById(R.id.TVdate);
            priceTextView = itemView.findViewById(R.id.TVprice);
            detailTextView = itemView.findViewById(R.id.TVdetail);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
