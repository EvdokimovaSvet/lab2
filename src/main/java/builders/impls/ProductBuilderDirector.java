package builders.impls;

import builders.ProductBuilder;
import model.Product;

public class ProductBuilderDirector {
    private ProductBuilder builder;

    public ProductBuilderDirector(ProductBuilder builder) {
        this.builder = builder;
    }

    public Product construct(String name, String country, int weight, int days) {
        return builder.buildName(name).buildCountry(country).buildWeight(weight).buildDC().buildDD(days).build();
    }

}
