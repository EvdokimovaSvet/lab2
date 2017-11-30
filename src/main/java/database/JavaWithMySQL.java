package database;

import java.sql.*;
import java.time.LocalDate;


public class JavaWithMySQL {

    static Connection Conn = null;
    static PreparedStatement PrepareStat = null;

    public static void main(String[] argv) {
        try {
            makeJDBCConnection();
            addDataToDB("Potato", "USA", 5, LocalDate.of(2017,5,15),  LocalDate.of(2017,6,15) );
            addDataToDB("Cocoa", "China", 1, LocalDate.of(2017,6,25),  LocalDate.of(2017,7,15));
            addDataToDB("Apple", "Ukraine", 3, LocalDate.of(2017,5,15),  LocalDate.of(2017,10,15));
            getDataFromDB();
            PrepareStat.close();
            Conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void makeJDBCConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            Conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/warehouse", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

    }

    private static void addDataToDB(String nameOfProduct, String countryOfOrigin, int weight, LocalDate dateOfBirth, LocalDate dateOfDeath) {

        try {
            String insertQueryStatement = "INSERT  INTO  Product  VALUES  (?,?,?,?,?)";

            PrepareStat = Conn.prepareStatement(insertQueryStatement);
            PrepareStat.setString(1, nameOfProduct);
            PrepareStat.setString(2, countryOfOrigin);
            PrepareStat.setInt(3, weight);
            PrepareStat.setDate(4, Date.valueOf(dateOfBirth));
            PrepareStat.setDate(5, Date.valueOf(dateOfDeath));
            PrepareStat.executeUpdate();
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getDataFromDB() {

        try {
            String getQueryStatement = "SELECT * FROM product";
            PrepareStat = Conn.prepareStatement(getQueryStatement);
            ResultSet rs = PrepareStat.executeQuery();
            while (rs.next()) {
                String nameOfProduct = rs.getString("nameOfProduct");
                String countryOfOrigin = rs.getString("countryOfOrigin");
                int weight = rs.getInt("weight");
                Date dateOfBirth = rs.getDate("dateOfBirth");
                Date dateOfDeath = rs.getDate("dateOfDeath");
                System.out.format("%s, %s, %s, %s, %s\n", nameOfProduct, countryOfOrigin, weight, dateOfBirth, dateOfDeath);
            }
        } catch (

                SQLException e) {
            e.printStackTrace();
        }

    }
}