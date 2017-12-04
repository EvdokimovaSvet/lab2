package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class Warehouse {
    private String nameOfWarehouse;
    private List<Product> storage;

    public Warehouse() {
        this.storage = new ArrayList<>();
    }

    public void setName(String name){this.nameOfWarehouse=name;}

    public List<Product> getProducts() {
        return storage;
    }

    public void setStorage(List<Product> storage) {
        this.storage = storage;
    }

    public Warehouse addProduct(Product product) {
        this.storage.add(product);
        return this;
    }

    public int size() {
        return this.storage.size();
    }

    public void deleteProduct(Product product) {
        this.storage.remove(product);
     }

    public int totalWeight() {
        int sum = 0;
        for (Product product : this.storage) {
            sum += product.getWeight();
        }
        return sum;
    }

    public List<Product> listOfProductFromCountry(String country) {
        List<Product> store = new ArrayList<>();
        for (Product product : this.storage) {
            if (product.getCountry().equals(country)) {
                store.add(product);
            }
        }
        return store;
    }

    public void deleteAllExpiredProduct() {
        Iterator<Product> iter = storage.iterator();
        while (iter.hasNext()) {
            Product p = iter.next();
            if (!p.isValidProduct()){
                iter.remove();
            }
        }
    }

    public void deleteAllExpiredProductByStream() {
        this.setStorage(this.storage.stream().filter(product -> product.isValidProduct()).collect(Collectors.toList()));
    }

    public List<Product> listOfProductFromCountryByStream(String country) {
        return this.storage.stream()
                .filter(product -> product.getCountry().equals(country))
                .collect(Collectors.toList());
    }


    public int totalWeightByStream() {
        return this.storage.stream().mapToInt(product -> product.getWeight()).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Warehouse warehouse = (Warehouse) o;

        if (nameOfWarehouse != null ? !nameOfWarehouse.equals(warehouse.nameOfWarehouse) : warehouse.nameOfWarehouse != null) return false;
        return storage != null ? storage.equals(warehouse.storage) : warehouse.storage == null;
    }

    @Override
    public int hashCode() {
        int result = nameOfWarehouse != null ? nameOfWarehouse.hashCode() : 0;
        result = 31 * result + (storage != null ? storage.hashCode() : 0);
        return result;
    }
}
