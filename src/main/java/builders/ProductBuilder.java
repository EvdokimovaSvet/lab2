package builders;


import model.Product;

import java.time.LocalDate;

public interface ProductBuilder {
    ProductBuilder buildId(int id);

    ProductBuilder buildName(String name);

    ProductBuilder buildCountry(String country);

    ProductBuilder buildWeight(int weight);

    ProductBuilder buildDC(LocalDate date);

    ProductBuilder buildDD(LocalDate date);

    ProductBuilder buildIdWare(int idWare);

    Product build();
}
