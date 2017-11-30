package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
public class Warehouse {
    private String name;
    private List<Product> storage;

    public Warehouse() {
        this.storage = new ArrayList<>();
    }

    public List<Product> getStorage() {
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

    public Warehouse deleteProduct(Product product) {
        this.storage.remove(product);
        return this;
    }

    public int totalWeight() {
        int sum = 0;
        for (Product product : this.storage) {
            sum += product.getWeight();
        }
        return sum;
    }

    public int countOfProductFromCountry(String country) {
        int count = 0;
        for (Product product : this.storage) {
            if (product.getCountry().equals(country)) {
                count++;
            }
        }
        return count;
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
        List<Product> list = new ArrayList<>();
        for (Product product : this.storage) {
            if (product.isValidProduct()) {
                list.add(product);
            }
        }
        this.setStorage(list);
    }

    public void deleteAllExpiredProductByStream() {
        this.setStorage(this.storage.stream().filter(product -> product.isValidProduct()).collect(Collectors.toList()));
    }

    public List<Product> listOfProductFromCountryByStream(String country) {
        return this.storage.stream()
                .filter(product -> product.getCountry().equals(country))
                .collect(Collectors.toList());
    }

    public long countOfProductFromCountryByStream(String country) {
        return this.storage.stream()
                .filter(product -> product.getCountry().equals(country)).count();
    }

    public int totalWeightByStream() {
        return this.storage.stream().mapToInt(product -> product.getWeight()).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Warehouse warehouse = (Warehouse) o;

        if (name != null ? !name.equals(warehouse.name) : warehouse.name != null) return false;
        return storage != null ? storage.equals(warehouse.storage) : warehouse.storage == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (storage != null ? storage.hashCode() : 0);
        return result;
    }
}
