package com.example.nosapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class AzureCon {
    Connection con;
    @SuppressLint("NewApi")
    public Connection conclass()
    {

        final String SERVER = "nostalgiaapp.database.windows.net";
        final String DATABASE = "nostalgiaapp";
        final String USERNAME = "nostalgiaapp";
        final String PASSWORD = "BigPapa123";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        // Connection string for the Azure SQL Database
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String CONNECTION_STRING = "jdbc:jtds:sqlserver://" + SERVER + ":1433;" +
                            "database=" + DATABASE + ";" +
                            "user=" + USERNAME + ";" +
                            "password=" + PASSWORD + ";" + "databaseName=nosapp;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=60;ssl=request;";
            con = DriverManager.getConnection(CONNECTION_STRING);
        }
        catch(Exception e){
            Log.e("Error is",e.getMessage());
        }
        return con;

    }
}
