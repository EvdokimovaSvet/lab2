package builders;


import model.Product;

public interface ProductBuilder {
    ProductBuilder buildName(String name);

    ProductBuilder buildCountry(String country);

    ProductBuilder buildWeight(int weight);

    ProductBuilder buildDC();

    ProductBuilder buildDD(int days);

    Product build();
}
