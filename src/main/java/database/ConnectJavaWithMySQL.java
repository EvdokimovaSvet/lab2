package database;

import sun.dc.pr.PRError;

import java.sql.*;
import java.time.LocalDate;


public class ConnectJavaWithMySQL {

    private static Connection Conn = null;
    private static PreparedStatement PrepareStat = null;

    public static void makeJDBCConnection() {
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
        }

    }

    public static void addDataToDBProduct(String nameOfProduct, String countryOfOrigin, int weight, LocalDate dateOfBirth, LocalDate dateOfDeath, int idWare) {

        try {
            String insertQueryStatement = "INSERT  INTO  Product (nameOfProduct,countryOfOrigin,weight,dateOfBirth,dateOfDeath,id_ware) VALUES  (?,?,?,?,?,?)";

            PrepareStat = Conn.prepareStatement(insertQueryStatement);
            PrepareStat.setString(1, nameOfProduct);
            PrepareStat.setString(2, countryOfOrigin);
            PrepareStat.setInt(3, weight);
            PrepareStat.setDate(4, Date.valueOf(dateOfBirth));
            PrepareStat.setDate(5, Date.valueOf(dateOfDeath));
            PrepareStat.setInt(6,idWare);
            PrepareStat.executeUpdate();
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDataToDBWarehouse(int idWare,String nameOfWarehouse) {

        try {
            String insertQueryStatement = "INSERT  INTO  Product (id, name) VALUES  (?,?)";

            PrepareStat = Conn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, idWare);
            PrepareStat.setString(2, nameOfWarehouse);
            PrepareStat.executeUpdate();
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getDataFromDB(int id) {

        try {
            String getQueryStatement = "SELECT * FROM product WHERE product.id="+id+";";
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