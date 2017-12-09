package builders.impls;

import builders.ProductBuilder;
import model.Product;

import java.time.LocalDate;

public class ProductBuilderDirector {
    private ProductBuilder builder;

    public ProductBuilderDirector(ProductBuilder builder) {
        this.builder = builder;
    }


    public Product construct(int id, String name, String country, int weight, LocalDate dateOfCreate, LocalDate dateOfDeath, int idWare) {
        return builder.buildId(id).buildName(name).buildCountry(country).buildWeight(weight).buildDC(dateOfCreate).buildDD(dateOfDeath).buildIdWare(idWare).build();
    }

}
