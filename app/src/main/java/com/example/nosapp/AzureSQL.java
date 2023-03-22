package com.example.nosapp;

import android.util.Log;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AzureSQL {

    public static ArrayList<Favorites> getFavorites() {
        Connection connect;
        AzureCon c = new AzureCon();
        connect = c.conclass();
        String query = "SELECT DISTINCT s.logo FROM Favorites f JOIN Shows s ON f.showname = s.showname;";

        ArrayList<Favorites> favsList = new ArrayList<>();

        try (
             PreparedStatement statement = connect.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String logo = resultSet.getString("logo");
                Log.d("Favorites", "Logo: " + logo);
                Favorites favs = new Favorites(logo);
                favsList.add(favs);
            }
            connect.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favsList;
    }


//    public static ArrayList<Favorites> getClips(String showname) {
//        Connection connect;
//        String query = "SELECT videoID FROM Favorites where showname =?";
//        ArrayList<Favorites> clipList = new ArrayList<>();
//
//        AzureCon con = new AzureCon();
//        connect = con.conclass();
//
//        try (
//                PreparedStatement statement = connect.prepareStatement(query)) {
//            statement.setString(1, showname);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                String videoid = resultSet.getString("videoID");
//                Favorites favs = new Favorites(videoid);
//                clipList.add(favs);
//            }
//            connect.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return clipList;
//    }

    // Get clips using logo name
    public static ArrayList<String> getClips(String logo) {
        Connection connect;
        String query = "SELECT videoID FROM Favorites f JOIN Shows s ON f.showname = s.showname WHERE s.logo = ?";
        ArrayList<String> clipList = new ArrayList<>();

        AzureCon con = new AzureCon();
        connect = con.conclass();

        try (
                PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, logo);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String videoid = resultSet.getString("videoID");
                clipList.add(videoid);
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clipList;
    }



    public static void addFavorite(String videoID,String showname){
        Connection connect;
        String query = "Insert into Favorites(videoID, showname) Values(?,?)";
        AzureCon c = new AzureCon();
        connect = c.conclass();


        try (
                PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, videoID);
            statement.setString(2, showname);
            statement.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public static void deleteFavorite(String videoId) {
        Connection connect;
        String query = "DELETE FROM Favorites WHERE videoID=?";
        AzureCon c = new AzureCon();
        connect = c.conclass();

        try (
                PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, videoId);
            statement.executeUpdate();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Shows> getShowDetail(String showname) {
        Connection connect;
        String query = "SELECT showname, channel, startDate, endDate, seasons, episodes, synopsis from Shows WHERE showname=?";
        ArrayList<Shows> details = new ArrayList<>();

        AzureCon c = new AzureCon();
        connect = c.conclass();

        try (
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, showname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("showname");
                String channel = resultSet.getString("channel");
              //  Date startDate = resultSet.getDate("startDate");
              //  Date endDate = resultSet.getDate("endDate");
                String startDate = new SimpleDateFormat("MM-dd-yyyy").format(resultSet.getDate("startDate"));
                String endDate = resultSet.getDate("endDate") != null ? new SimpleDateFormat("MM-dd-yyyy").format(resultSet.getDate("endDate")) : "Present";

                int seasons = resultSet.getInt("seasons");
                int episodes = resultSet.getInt("episodes");
                String synopsis = resultSet.getString("synopsis");

                Shows show = new Shows(name, channel, startDate, endDate, seasons, episodes, synopsis);
                details.add(show);

            }
            connect.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return details;
    }

    public static ArrayList<Shows> getShows() {
        Connection connect;
        String query = "SELECT showname, logo FROM Shows";
        ArrayList<Shows> showList = new ArrayList<>();

        AzureCon con = new AzureCon();
        connect = con.conclass();

        try (
             PreparedStatement statement = connect.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("showID");
                String name = resultSet.getString("showname");
                String logo = resultSet.getString("logo");


                Shows show = new Shows(id, name, logo);
                showList.add(show);
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showList;
    }






}

