package com.example.nosapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AzureSQL {

    public static ArrayList<Favorites> getFavorites() {
        Connection connect;
        String query = "SELECT logo FROM Favorites";
        ArrayList<Favorites> favsList = new ArrayList<>();

        AzureCon con = new AzureCon();
        connect = con.conclass();

        try (
                PreparedStatement statement = connect.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int logo = resultSet.getInt("logo");


                Favorites favs = new Favorites();
                favsList.add(favs);
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favsList;
    }

    public static ArrayList<Favorites> getClips(String showname) {
        Connection connect;
        String query = "SELECT videoId FROM Favorites where showname =?";
        ArrayList<Favorites> clipList = new ArrayList<>();

        AzureCon con = new AzureCon();
        connect = con.conclass();

        try (
                PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, showname);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("showname");
                int logo = resultSet.getInt("logo");


                Favorites favs = new Favorites();
                clipList.add(favs);
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clipList;
    }



    public static void addFavorite(String videoId,String showname){
        Connection connect;
        String query = "Insert into Favorites(videoID, showID) Values(?,?)";
        AzureCon c = new AzureCon();
        connect = c.conclass();


        try (
                PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, videoId);
            statement.setString(2, showname);
            statement.executeQuery();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public static void deleteFavorite(String videoId) {
        Connection connect;
        String query = "DELETE FROM Favorites WHERE id=?";
        AzureCon c = new AzureCon();
        connect = c.conclass();

        try (
                PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, videoId);
            statement.executeQuery();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Shows> getShowDetail(String showname) {
        Connection connect;
        String query = "SELECT showname, network, startDate, endDate, seasons, episodes, synopsis from Shows WHERE showname=?";
        ArrayList<Shows> details = new ArrayList<>();

        AzureCon c = new AzureCon();
        connect = c.conclass();

        try (
             PreparedStatement statement = connect.prepareStatement(query)) {
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

                Shows show = new Shows(name, network, startDate, endDate, seasons, episodes, synopsis);
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

