package database;

import java.sql.*;
import java.time.LocalDate;


public class ConnectJavaWithMySQL {

    private static Connection Conn = null;
    private static PreparedStatement PrepareStat = null;

    public static Connection makeConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/warehouses", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Conn;

    }

    private static void createProductTable() throws SQLException {
        String prepCreate="CREATE TABLE product\n" +
                "(id_prod INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                " nameOfProduct CHAR(25) NOT NULL,\n" +
                " cointryOfOrigin CHAR(25) NOT NULL,\n" +
                " weight INTEGER NOT NULL,\n" +
                " dateOfBirth DATE NOT  NULL,\n" +
                " dateOfDeath DATE NOT NULL,\n" +
                " idWare INTEGER REFERENCES warehouse(idWare));";
        PreparedStatement preparedStatement =  Conn.prepareStatement(prepCreate);
        preparedStatement.executeUpdate();

    }

    private static void createWarehouseTable() throws SQLException {
        String prepCreate="CREATE TABLE warehouse\n" +
                "(idWare INTEGER PRIMARY KEY  AUTO_INCREMENT NOT NULL,\n" +
                " nameOfWarehouse CHAR(25) NOT NULL );";
        PreparedStatement preparedStatement =  Conn.prepareStatement(prepCreate);
        preparedStatement.executeUpdate();
    }

    public static void setDatabase() throws SQLException, ClassNotFoundException {
        Conn = makeConnection();
        createWarehouseTable();
        createProductTable();
        Conn.close();
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

    private static void dropTable() throws SQLException, ClassNotFoundException {
        Connection conn = makeConnection();
        Statement st = conn.createStatement();
        st.executeUpdate("DROP TABLE IF EXISTS 'warehouse';");
        st.executeUpdate("DROP TABLE IF EXISTS 'product';");
        conn.close();
    }
}