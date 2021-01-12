package com.easy.ecomm.testcontainer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusTest;
import lombok.SneakyThrows;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Collections;
import java.util.Map;

@QuarkusTest
@QuarkusTestResource(MongoContainer.class)
public class MongoContainer implements QuarkusTestResourceLifecycleManager {

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @SneakyThrows
    @Override
    public Map<String, String> start() {
        mongoDBContainer.start();
        mongoDBContainer.execInContainer("db.easy-sales-quarkus.save(){\n" +
                "    \"_id\" : \"dac7bd2c-2cd0-453a-ac8c-6c299f57dec8\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"category\" : \"books\"\n" +
                "});");
        return Collections.singletonMap("quarkus.mongodb.connection-string", mongoDBContainer.getReplicaSetUrl());
    }

    @Override
    public void stop() {
        mongoDBContainer.close();
    }

}
