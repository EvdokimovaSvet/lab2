package database;

import builders.impls.Builder;
import builders.impls.ProductBuilderDirector;
import model.Product;
import model.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
                " countryOfOrigin CHAR(25) NOT NULL,\n" +
                " weight INTEGER NOT NULL,\n" +
                " dateOfCreate DATE NOT  NULL,\n" +
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

    public static void addProduct(Product product, Warehouse warehouse) {

        try {
            Conn = makeConnection();
            Conn.createStatement();

            String insertQueryStatement = "INSERT  INTO  Product (id_prod, nameOfProduct,countryOfOrigin,weight,dateOfCreate,dateOfDeath,idWare) VALUES  (?,?,?,?,?,?,?)";

            PrepareStat = Conn.prepareStatement(insertQueryStatement);
            PrepareStat.setInt(1, product.getIdOfProduct());
            PrepareStat.setString(2, product.getNameOfProduct());
            PrepareStat.setString(3, product.getCountry());
            PrepareStat.setInt(4, product.getWeight());
            PrepareStat.setDate(5, Date.valueOf(product.getDateOfCreate()));
            PrepareStat.setDate(6, Date.valueOf(product.getDateOfDeath()));
            PrepareStat.setInt(7,warehouse.getId());
            PrepareStat.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public static Warehouse addWarehouseWithoutProducts(Warehouse warehouse) throws ClassNotFoundException, SQLException{
        Conn = makeConnection();
        Conn.createStatement();
        String preparedUpdate1="INSERT INTO warehouse (idWare, nameOfWarehouse) values(?,?);";
        PreparedStatement pr1= Conn.prepareStatement(preparedUpdate1);
        pr1.setInt(1, warehouse.getId());
        pr1.setString(2, warehouse.getName());
        pr1.executeUpdate();

        String preparedQuery1="SELECT idware FROM warehouse WHERE idware=? ;";
        PreparedStatement pr2= Conn.prepareStatement(preparedQuery1);
        pr2.setInt(1, warehouse.getId());
        ResultSet resultSet=pr2.executeQuery();
        if(resultSet.next())
            warehouse.setId(resultSet.getInt("idware"));
        return warehouse;
    }

    public static void addWarehouse(Warehouse warehouse) throws ClassNotFoundException, SQLException {
        warehouse=addWarehouseWithoutProducts(warehouse);
        for(Product product : warehouse.getProducts()) {
            addProduct(product, warehouse);
        }
    }


    public static Product getProductById(Integer id) throws SQLException, ClassNotFoundException {
        Conn = makeConnection();
        Conn.createStatement();
        String preparedQuerry1="SELECT c.id_prod as id_prod,"
                + "c.nameOfProduct as nameOfProduct,"
                + "c.countryOfOrigin as countryOfOrigin,"
                + "c.weight as weight,"
                + "c.dateOfCreate as dateOfCreate,"
                + "c.dateOfDeath as dateOfDeath,"
                + "c.idware as idware FROM product c"
                +" WHERE  c.id_prod=?;";

        PreparedStatement preparedStmt1=Conn.prepareStatement(preparedQuerry1);
        preparedStmt1.setInt(1,id);
        ResultSet resultSet = preparedStmt1.executeQuery();

        Product product = null;
        if(resultSet.next()) {
            product = new ProductBuilderDirector(new Builder()).construct(resultSet.getInt(1),
                    resultSet.getString(2),resultSet.getString(3),
                    resultSet.getInt(4), resultSet.getDate(5).toLocalDate(),
                    resultSet.getDate(6).toLocalDate(),resultSet.getInt(7));
           }
        Conn.close();
        return product;
    }

    public static List<String> getProductByWarehouse(Warehouse warehouse) throws SQLException, ClassNotFoundException{
        List<String> namesProducts = new ArrayList<>();
        Conn = makeConnection();
        String preparedQuerry1="SELECT nameOfProduct FROM Product WHERE idware = ?;";
        PreparedStatement preparedStmt =  Conn.prepareStatement(preparedQuerry1);
        preparedStmt.setInt(1, warehouse.getId());
        ResultSet resultSet=preparedStmt.executeQuery();
        while(resultSet.next())
        {
            namesProducts.add(resultSet.getString(2));
        }
        Conn.close();
        return namesProducts;
    }

    public static void deleteProduct(int id) throws SQLException, ClassNotFoundException {
        Conn=makeConnection();
        String prepQuery1="SELECT * FROM PRODUCT WHERE id_prod = ?;";
        String prepDelete1="DELETE FROM PRODUCT WHERE id_prod= ? ;";
        PreparedStatement preparedStatement= Conn.prepareStatement(prepQuery1);
        preparedStatement.setInt(1, id);
        ResultSet resultSet= preparedStatement.executeQuery();

        if(resultSet.next())
        {Product product = new Builder().buildId(id).build();
            product.setIdProduct(resultSet.getInt("id_prod"));

            PreparedStatement preparedStatement1= Conn.prepareStatement(prepDelete1);
            preparedStatement1.setInt(1, product.getIdOfProduct());
            int rs = preparedStatement1.executeUpdate();
            Conn.close();
            if(rs == 0) throw new IllegalArgumentException("Product not found");
        }
    }

    public static void deleteWarehouse(int nameOfWarehouse) throws SQLException, ClassNotFoundException {
        Conn = makeConnection();
        String prepQuery1="SELECT idWare FROM WAREHOUSE WHERE nameOfWarehouse = ? ;";
        String prepDelete1="DELETE FROM WAREHOUSE WHERE nameOfWarehouse = ?;";
        String prepQuery2="SELECT id_prod FORM PRODUCT WHERE idWare = ?;";
        PreparedStatement preparedStatement = Conn.prepareStatement(prepQuery1);
        preparedStatement.setInt(1, nameOfWarehouse);
        ResultSet resultSet= preparedStatement.executeQuery();
        if(resultSet.next())
        {int idWare = resultSet.getInt("idWare");

            PreparedStatement preparedStatement1 = Conn.prepareStatement(prepDelete1);
            preparedStatement1.setInt(1, nameOfWarehouse);
            int rs = preparedStatement1.executeUpdate();

            PreparedStatement preparedStatement2 = Conn.prepareStatement(prepQuery2);
            preparedStatement2.setInt(1, idWare);
            ResultSet r2 = preparedStatement2.executeQuery();

            while(r2.next()) {
                deleteProduct(r2.getInt("id_prod"));
            }
            Conn.close();
            if(rs == 0) throw new IllegalArgumentException("Warehouse not found");
        }
    }

    public static void dropTables() throws SQLException, ClassNotFoundException {
        Conn = makeConnection();
        Statement statement = Conn.createStatement();
        statement.executeUpdate("DROP TABLE IF EXISTS warehouse;");
        statement.executeUpdate("DROP TABLE IF EXISTS product;");
        Conn.close();
    }
}