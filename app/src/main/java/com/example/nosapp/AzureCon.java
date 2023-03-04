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

        final String s = "lilpapa.database.windows.net";
        final String dB = "nosapp";
        final String uN = "BigPapa";
        final String pW = "lilpapa@123";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            String cS = "jdbc:jtds:sqlserver://" + s + ":1433;" +
                            "database=" + dB + ";" +
                            "user=" + uN + ";" +
                            "password=" + pW + ";" + "databaseName=nosapp;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=45;ssl=request;";
            con = DriverManager.getConnection(cS);
        }
        catch(Exception e){
            Log.e("Error is",e.getMessage());
        }
        return con;

    }
}
