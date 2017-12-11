import builders.impls.Builder;
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
        Product ob = new Builder().buildName("Milk").buildCountry("Germany").buildWeight(900).build();
        assertEquals(ob.getWeight(), 900);

    }

    @Test
    public void checkAddingAndSizeOfWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildName("Yogurt").buildCountry("Ukraine").buildWeight(200).build());
        warehouse.addProduct(new Builder().buildName("Cream").buildCountry("Ukraine").buildWeight(250).build());
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
    public void checkListProductFromCountry() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Ukraine").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        assertEquals(warehouse.listOfProductFromCountry("Poland").size(), 3);
    }

    @Test
    public void checkWarehouseExpired() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildDC(LocalDate.now()).buildDD(LocalDate.of(2017,12,30)).build());
        warehouse.addProduct(new Builder().buildDC(LocalDate.now()).buildDD(LocalDate.of(2017,12,30)).build());
        warehouse.addProduct(new Builder().buildDC(LocalDate.now()).buildDD(LocalDate.of(2017,12,30)).build());
        Product product = new Product();
        product.setDateOfDeath(2017, 10, 31);
        warehouse.addProduct(product);
        warehouse.deleteAllExpiredProduct();
        assertEquals(warehouse.getProducts().size(), 3);
    }

    @Test
    public void checkWarehouseExpiredByStream() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildDC(LocalDate.now()).buildDD(LocalDate.of(2017,12,30)).build());
        warehouse.addProduct(new Builder().buildDC(LocalDate.now()).buildDD(LocalDate.of(2017,12,30)).build());
        warehouse.addProduct(new Builder().buildDC(LocalDate.now()).buildDD(LocalDate.of(2017,12,30)).build());
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
    public void checkTotalWeightByStream() {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildWeight(300).build());
        warehouse.addProduct(new Builder().buildWeight(750).build());
        assertEquals(warehouse.totalWeightByStream(), warehouse.totalWeight());

    }

    @Test
    public void checkSavingToJson() throws IOException {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.serealise(warehouse, "warehouses.json");
        Warehouse warehouseFromJsonFile = jsonUtil.deserealise("warehouses.json");
        assertEquals(warehouse, warehouseFromJsonFile);
    }

    @Test
    public void checkSavingToXml() throws IOException {
        Warehouse warehouse = new Warehouse();
        warehouse.setId(1);
        warehouse.setName("Glory");
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        warehouse.addProduct(new Builder().buildCountry("Poland").build());
        XmlUtil xmlUtil = new XmlUtil();
        xmlUtil.serealise(warehouse, "warehouse.xml");
        Warehouse warehouseFromXml = xmlUtil.deserealise("warehouse.xml");
        assertEquals(warehouseFromXml, warehouse);
    }

    @Test
    public void checkSqlConnection() throws SQLException {

    }
}