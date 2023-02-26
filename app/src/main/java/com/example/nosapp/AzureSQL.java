package com.example.nosapp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AzureSQL {

    public static List<Show> getShowDetail(String showname) {
        Connection connect;
        String query = "SELECT showname, network, startDate, endDate, seasons, episodes, synopsis from ShowDetails WHERE showname=?";
        List<Show> showList = new ArrayList<>();

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

                Show show = new Show(name, network, startDate, endDate, seasons, episodes, synopsis);
                showList.add(show);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return showList;
    }

    public static List<Show> getShows() {
        Connection connection;
        String query = "SELECT  FROM dbo.ShowClips";
        List<Show> showList = new ArrayList<>();

        AzureCon con = new AzureCon();
        connection = con.conclass();

        try (
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
    }






}

