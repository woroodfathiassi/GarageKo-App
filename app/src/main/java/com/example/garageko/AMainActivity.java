package com.example.garageko;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AMainActivity extends AppCompatActivity {
    private Button loginBtn;
    private Connection connection;
    private String connectionResult="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.a_activity_main);
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(e->{
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            //executorService.execute(()-> {
                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connection = connectionHelper.makeConnection();
                    String query = "select * from users";
                    PreparedStatement st = connection.prepareStatement(query);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        Toast.makeText(this, rs.getString(1), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Problem", Toast.LENGTH_SHORT).show();
                }
            //});
        });
    }
}