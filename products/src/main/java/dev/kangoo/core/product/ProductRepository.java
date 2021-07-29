package dev.kangoo.core.product;

import dev.kangoo.core.product.Product;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductRepository implements PanacheMongoRepository<Product> {

    public Product save(Product product){
        persist(product);
        return product;
    }

    public List<Product> findByIdList(List<ObjectId> productsId){
        return find("{_id:{$in: [?1]}}", productsId).list();
    }

}
