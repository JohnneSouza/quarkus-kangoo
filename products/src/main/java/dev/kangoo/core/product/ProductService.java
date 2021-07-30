package dev.kangoo.core.product;

import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;

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

    ModelMapper modelMapper = new ModelMapper();

    public Product save(ProductDto productDto){
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setActive(true);
        product.setCreatedDate(LocalDate.now());

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
        modelMapper.map(productDto, product);
        product.setUpdatedDate(LocalDate.now());
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
