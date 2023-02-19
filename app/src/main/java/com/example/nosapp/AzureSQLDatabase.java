package com.example.nosapp;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AzureSQLDatabase {


    private static final String SERVER = "nostalgiaapp.database.windows.net";
    private static final String DATABASE = "nostalgiaapp";
    private static final String USERNAME = "nostalgiaapp";
    private static final String PASSWORD = "BigPapa123";

    // Connection string for the Azure SQL Database
    private static final String CONNECTION_STRING =
            "jdbc:sqlserver://" + SERVER + ":1433;" +
                    "database=" + DATABASE + ";" +
                    "user=" + USERNAME + "@" + SERVER + ";" +
                    "password=" + PASSWORD + ";" +
                    "encrypt=true;trustServerCertificate=false;" +
                    "hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    // Insert a new record into the database
    public void addClip(String videoID, String showname) throws SQLException {
        String query = "INSERT INTO ShowClips (videoID, showname) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, videoID);
            statement.setString(2, showname);
            statement.executeUpdate();
        }
        catch(SQLException e){
            Context Context = null;
            Toast.makeText(null, "Error retrieving show details", Toast.LENGTH_SHORT).show();
        }

    }

    // Update an existing record in the database
    public static void updateClip(String videoID, String showname) throws SQLException {
        String query = "UPDATE users SET videoID=?, showname=? WHERE videoID=?";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, videoID);
            statement.setString(2, showname);
            statement.executeUpdate();
        }
        catch(SQLException e){
            Context Context = null;
            Toast.makeText(null, "Error retrieving show details", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete an existing record from the database
    public static void deleteRecord(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id=?";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Get a list of all records in the database
    public static ResultSet getAllRecords() throws SQLException {
        String query = "SELECT * FROM users";
        Connection connection = DriverManager.getConnection(CONNECTION_STRING);
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }
}

