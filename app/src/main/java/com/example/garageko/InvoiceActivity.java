package com.example.garageko;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RecyclerView recycler = findViewById(R.id.invoice_recycler);

        int[] imageIds = new int[Invoice.invoices.length];
        String[] dates = new String[Invoice.invoices.length];
        double[] prices = new double[Invoice.invoices.length];
        String[] details = new String[Invoice.invoices.length];

        for(int i = 0; i<dates.length;i++){
            imageIds[i] = Invoice.invoices[i].getImageId();
            dates[i] = Invoice.invoices[i].getDate();
            prices[i] = Invoice.invoices[i].getPrice();
            details[i] = Invoice.invoices[i].getDetail();
        }

        recycler.setLayoutManager(new LinearLayoutManager(this));
        InvoiceAdapter adapter = new InvoiceAdapter(imageIds, dates, prices, details);
        recycler.setAdapter(adapter);
    }
}