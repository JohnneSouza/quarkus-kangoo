package dev.kangoo.core.product;

import dev.kangoo.core.product.Product;
import dev.kangoo.core.product.ProductDto;
import dev.kangoo.core.product.ProductRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public Product save(ProductDto productDto){
        Product product = new Product();
        product.setActive(true);
        product.setCreatedDate(LocalDate.now());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setColor(productDto.getColor());
        product.setPriceCost(productDto.getPriceCost());
        product.setStockAmount(productDto.getStockAmount());
        product.setPriceSell(productDto.getPriceSell());
        return productRepository.save(product);
    }

    public Product findById(ObjectId id){
        return productRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }

    public List<Product> findAll(){
        return productRepository.findAll().list();
    }

    public Product update(ObjectId id, ProductDto productDto) {
        Product product = findById(id);
        product.setUpdatedDate(LocalDate.now());
        product.setPriceCost(productDto.getPriceCost());
        product.setPriceSell(productDto.getPriceSell());
        product.setColor(productDto.getColor());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        productRepository.update(product);
        return product;
    }

    public Product changeActiveStatus(ObjectId id){
        Product product = findById(id);
        product.setActive(!product.isActive());
        productRepository.update(product);
        return product;
    }

    public List<Product> findByIdList(List<String> listId) {
        if (listId.isEmpty()){
            throw new NotFoundException("The product list is empty");
        }
        List<ObjectId> productsId = new ArrayList<>();
        listId.forEach(id -> productsId.add(new ObjectId(id)));
        return productRepository.findByIdList(productsId);
    }
}
