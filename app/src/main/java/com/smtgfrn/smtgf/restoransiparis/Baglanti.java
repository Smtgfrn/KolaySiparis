package com.smtgfrn.smtgf.restoransiparis;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Baglanti {

    public Connection Baglan(String user, String password) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://192.168.43.103/KolaySiparis:1433";
            connection = DriverManager.getConnection(ConnectionURL, user, password);

        } catch (SQLException se) {
            Log.e("error here 1:", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2:", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3:", e.getMessage());
        }
        return connection;
    }
}
