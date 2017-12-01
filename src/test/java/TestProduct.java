import builders.impls.Builder;
import builders.impls.ProductBuilderDirector;
import database.ConnectJavaWithMySQL;
import model.Product;
import model.Warehouse;
import org.testng.annotations.Test;
import util.JsonUtil;
import util.XmlUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static database.ConnectJavaWithMySQL.makeJDBCConnection;
import static org.testng.Assert.assertEquals;


public class TestProduct {
    @Test
    public void expiredTest() {
        Product ob = new Product();
        ob.setDateOfDeath(2017, 10, 31);
        assertEquals(ob.isValidProduct(), false);

    }

    @Test
    public void testBuilder() {
        Product ob = new ProductBuilderDirector(new Builder()).construct("Milk", "Germany", 900, 15);
        assertEquals(ob.getWeight(), 900);

    }

    @Test
    public void checkAddingAndSizeOfWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new ProductBuilderDirector(new Builder()).construct("Yogurt", "Ukraine", 200, 20));
        warehouse.addProduct(new ProductBuilderDirector(new Builder()).construct("Cream", "Ukraine", 250, 20));
        assertEquals(warehouse.size(), 2);

    }

    @Test
    public void checkAllWeight() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildWeight(300).build());
        warehouse.addProduct(new Builder().buildWeight(750).build());
        assertEquals(warehouse.totalWeight(), 1050);

    }

    @Test
    public void checkCountProductFromCountry() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Ukraine").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        assertEquals(warehouse.countOfProductFromCountry("Poland"), 3);

    }

    @Test
    public void checkListProductFromCountry() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Ukraine").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        assertEquals(warehouse.listOfProductFromCountry("Poland").size(), warehouse.countOfProductFromCountry("Poland"));
    }

    @Test
    public void checkWarehouseExpired() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildDC().buildDD(30).build());
        warehouse.addProduct(new Builder().buildDC().buildDD(30).build());
        warehouse.addProduct(new Builder().buildDC().buildDD(30).build());
        Product product = new Product();
        product.setDateOfDeath(2017, 10, 31);
        warehouse.addProduct(product);
        warehouse.deleteAllExpiredProduct();
        assertEquals(warehouse.size(), 3);
    }

    @Test
    public void checkWarehouseExpiredByStream() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildDC().buildDD(30).build());
        warehouse.addProduct(new Builder().buildDC().buildDD(30).build());
        warehouse.addProduct(new Builder().buildDC().buildDD(30).build());
        Product product = new Product();
        product.setDateOfDeath(2017, 10, 31);
        warehouse.addProduct(product);
        warehouse.deleteAllExpiredProductByStream();
        assertEquals(warehouse.size(), 3);

    }

    @Test
    public void checkListProductFromCountryByStream() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Ukraine").build());
        assertEquals(warehouse.listOfProductFromCountryByStream("Poland").size(), warehouse.listOfProductFromCountry("Poland").size());

    }

    @Test
    public void checkCountProductFromCountryByStream() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Ukraine").build());
        assertEquals(warehouse.countOfProductFromCountryByStream("Poland"), warehouse.countOfProductFromCountry("Poland"));

    }

    @Test
    public void checkTotalWeightByStream() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildWeight(300).build());
        warehouse.addProduct(new Builder().buildWeight(750).build());
        assertEquals(warehouse.totalWeightByStream(), warehouse.totalWeight());

    }

    @Test
    public void checkSavingToJson() throws IOException {
        List<Warehouse> warehouses = new ArrayList<>();
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        Warehouse warehouse1 = new Warehouse();
        warehouse1.addProduct(new Builder().buildCountry("Poland").build());
        warehouse1.addProduct(new Builder().buildCountry("Ukraine").build());
        warehouses.add(warehouse);
        warehouses.add(warehouse1);
        /*assertEquals(warehouse.totalWeightByStream(),warehouse.totalWeight());*/
        JsonUtil.saveJson(warehouses, "warehouses.json");
        List<Warehouse> warehouseFromJsonFile = JsonUtil.getWarehouseFromJsonFile("warehouses.json");
        assertEquals(warehouses.size(), warehouseFromJsonFile.size());
    }

    @Test
    public void checkSavingToXml() throws IOException {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        XmlUtil.writeToXmlFile(warehouse, "warehouse.xml");
        Warehouse warehouseFromXml = XmlUtil.readFromXmlFile("warehouse.xml");
        assertEquals(warehouseFromXml, warehouse);
    }

    @Test
    public void checkSqlConnection() throws SQLException {
        makeJDBCConnection();
        ConnectJavaWithMySQL.addDataToDB("Potato", "USA", 2, LocalDate.of(2017, 5, 15),LocalDate.of(2017, 6, 15));
        ConnectJavaWithMySQL.getDataFromDB();
        
    }
}