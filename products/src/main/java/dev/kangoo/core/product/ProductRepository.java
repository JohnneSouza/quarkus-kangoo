package dev.kangoo.core.product;

import dev.kangoo.core.support.PageRequest;
import dev.kangoo.core.support.PageResponse;
import dev.kangoo.core.support.PaginationUtils;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
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

    public PageResponse<Product> findAll(PageRequest pageRequest){
        PanacheQuery<Product> page = findAll()
                .page(Page.of(pageRequest.getPageIndex(), pageRequest.getPageSize()));

        return PaginationUtils.generatePageResponse(page);
    }
}
