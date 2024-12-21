package com.example.garageko;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InvoiceDetailsActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private Button btnLeaveFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_invoice_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycler = findViewById(R.id.service_recycler);
        btnLeaveFeedback = findViewById(R.id.leaveFeedback);

        btnLeaveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvoiceDetailsActivity.this, CFeedbackActivity.class);
                startActivity(intent);
            }
        });

        int[] imageIds = new int[Service.invoices.length];
        String[] services = new String[Service.invoices.length];

        for(int i = 0; i<services.length;i++){
            imageIds[i] = Invoice.invoices[i].getImageId();
            services[i] = Invoice.invoices[i].getDate();
        }

        recycler.setLayoutManager(new LinearLayoutManager(this));
        ServiceAdapter adapter = new ServiceAdapter(imageIds, services);
        recycler.setAdapter(adapter);
    }
}