//package com.example.garageko;
//
//import android.graphics.drawable.Drawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {
//
//    private int[] imageIds;
//    private String[] dates;
//    private double[] prices;
//    private String[] details;
//
//    // Constructor to initialize data
//    public InvoiceAdapter(int[] imageIds, String[] dates, double[] prices, String[] details) {
//        this.imageIds = imageIds;
//        this.dates = dates;
//        this.prices = prices;
//        this.details = details;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // Inflate the card layout
//        CardView v = (CardView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.card_invoice_list_item, parent, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        CardView cardView = holder.cardView;
//
//        // Set the image
//        ImageView imageView = cardView.findViewById(R.id.tool); // Ensure this ID exists in `card_invoice_list_item.xml`
//        Drawable dr = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
//        imageView.setImageDrawable(dr);
//
//        // Set the date
//        TextView _date = cardView.findViewById(R.id.TVdate);
//        _date.setText(dates[position]);
//
//        // Set the price
//        TextView _price = cardView.findViewById(R.id.TVprice);
//        _price.setText("$ " + prices[position]);
//
//        // Set the detail
//        TextView _detail = cardView.findViewById(R.id.TVdetail);
//        _detail.setText(details[position]);
//
//        // Set click listener (add your logic)
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Add your action here
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return dates.length; // Assuming all arrays are of the same length
//    }
//
//    // ViewHolder class
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private CardView cardView;
//
//        public ViewHolder(CardView cardView) {
//            super(cardView);
//            this.cardView = cardView;
//        }
//    }
//}


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

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private int[] imageIds;
    private String[] dates;
    private double[] prices;
    private String[] details;

    // Constructor to initialize data
    public InvoiceAdapter(int[] imageIds, String[] dates, double[] prices, String[] details) {
        this.imageIds = imageIds;
        this.dates = dates;
        this.prices = prices;
        this.details = details;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the card layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_invoice_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Set the image
        Drawable dr = ContextCompat.getDrawable(holder.cardView.getContext(), imageIds[position]);
        holder.imageView.setImageDrawable(dr);

        // Set the date
        holder.dateTextView.setText(dates[position]);

        // Set the price
        holder.priceTextView.setText("$ " + prices[position]);

        // Set the detail
        holder.detailTextView.setText(details[position]);

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
        return dates.length; // Assuming all arrays are of the same length
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView dateTextView;
        private TextView priceTextView;
        private TextView detailTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view); // Ensure this ID exists in `card_invoice_list_item.xml`
            imageView = itemView.findViewById(R.id.tool);
            dateTextView = itemView.findViewById(R.id.TVdate);
            priceTextView = itemView.findViewById(R.id.TVprice);
            detailTextView = itemView.findViewById(R.id.TVdetail);
        }
    }
}
