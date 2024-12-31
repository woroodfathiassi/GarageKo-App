package com.example.garageko;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection con;
    String uname, pass, ip, port, database;
    public Connection makeConnection() {
        ip = "127.0.0.1";
        database="garage";
        uname="root";
        pass="root1234";
        port="3306";
        Connection connection=null;
        String ConnectionURL = null;
        try
        {
            Class.forName ("com.mysql.jdbc.Driver");
            ConnectionURL = "jdbc:mysql://" + ip + ":" + port + "/" + database;
            connection=DriverManager.getConnection(ConnectionURL, uname, pass);
        }
        catch (Exception ex) {
            Log.e("Error ", ex.getMessage()) ;
        }
        return connection;
    }
}
