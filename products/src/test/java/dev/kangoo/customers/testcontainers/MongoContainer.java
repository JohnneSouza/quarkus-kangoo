package dev.kangoo.customers.testcontainers;

import dev.kangoo.customers.core.product.ProductRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MongoDBContainer;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;

@QuarkusTest
@QuarkusTestResource(MongoContainer.class)
public class MongoContainer implements QuarkusTestResourceLifecycleManager {

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Inject
    ProductRepository productRepository;

    @SneakyThrows
    @Override
    public Map<String, String> start() {
        mongoDBContainer.start();
        return Collections.singletonMap("quarkus.mongodb.connection-string", mongoDBContainer.getReplicaSetUrl());
    }

    @Override
    public void stop() {
        mongoDBContainer.close();
    }


    @BeforeEach
    public void setup(){
        productRepository.deleteAll();
    }

}
