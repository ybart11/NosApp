package com.example.nosapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AzureSQL {
    /*private static final String SERVER = "nostalgiaapp.database.windows.net";
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
    public void addClips(String videoID, String showname) throws SQLException {
        String query = "INSERT INTO dbo.ShowClips (videoID, showname) VALUES (?, ?)";
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
    public static void updateClips(String videoID, String showname) throws SQLException {
        String query = "UPDATE dbo.ShowClips SET videoID=?, showname=? WHERE videoID=?";
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
    public static void deleteClips(String videoID) throws SQLException {
        String query = "DELETE FROM dbo.ShowClips WHERE videoID=?";
        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, videoID);
            statement.executeUpdate();
        }
        catch(SQLException e){
            Context Context = null;
            Toast.makeText(null, "Error retrieving show details", Toast.LENGTH_SHORT).show();
        }
    }

    // Get a list of all records in the database
    public static ResultSet getAllClips(String showname) throws SQLException {
        String query = "SELECT * FROM dbo.ShowClips WHERE showname=?";
        Connection connection = DriverManager.getConnection(CONNECTION_STRING);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, showname);
        return statement.executeQuery();
    }*/

    public static List<Show> getShowDetail(String showname) {
        Connection connection;
        String query = "SELECT showname, network, startDate, endDate, seasons, episodes, synopsis from ShowDetails WHERE showname=?";
        List<Show> showList = new ArrayList<>();

        AzureCon con = new AzureCon();
        connection = con.conclass();

        try (
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, showname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("showname");
                String network = resultSet.getString("network");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                int seasons = resultSet.getInt("seasons");
                int episodes = resultSet.getInt("episodes");
                String synopsis = resultSet.getString("synopsis");

                Show show = new Show(name, network, startDate, endDate, seasons, episodes, synopsis);
                showList.add(show);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showList;
    }

    /*public static List<Show> getShows() {
        String query = "SELECT  FROM dbo.ShowClips";
        List<Show> showList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("showname");
                int logo = resultSet.getInt("logo");


                Show show = new Show(name, logo);
                showList.add(show);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showList;
    }*/






}

