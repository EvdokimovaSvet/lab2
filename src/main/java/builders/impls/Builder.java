package builders.impls;

import builders.ProductBuilder;
import model.Product;

import java.time.LocalDate;

public class Builder implements ProductBuilder {

    private Product product;

    public Builder() {
        this.product = new Product();
    }

    public ProductBuilder buildId(int id) {
        product.setIdProduct(id);
        return this;
    }

    public ProductBuilder buildName(String name) {
        product.setName(name);
        return this;
    }

    public ProductBuilder buildCountry(String country) {
        product.setCountry(country);
        return this;
    }

    public ProductBuilder buildWeight(int weight) {
        product.setWeight(weight);
        return this;
    }

    public ProductBuilder buildDC(LocalDate date) {
        product.setDateOfCreate(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
        return this;
    }

    public ProductBuilder buildDD(LocalDate date) {
        product.setDateOfDeath(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
        return this;
    }

    public ProductBuilder buildIdWare(int id) {
        product.setIdWare(id);
        return this;
    }


    public Product build() {
        return product;
    }
}

