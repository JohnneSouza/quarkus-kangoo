package com.easy.ecomm.testcontainers;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PostgreSqlTestContainer implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:latest");

    @Override
    public Map<String, String> start() {
        POSTGRES.start();
        Map<String, String> properties = new HashMap<>();

        properties.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
        properties.put("quarkus.datasource.username", POSTGRES.getUsername());
        properties.put("quarkus.datasource.password", POSTGRES.getPassword());
        properties.put("quarkus.hibernate-orm.database.generation", "create");

        return properties;
    }

    @Override
    public void stop() {
        if (POSTGRES.isRunning()) {
            POSTGRES.stop();
        }
    }
}
