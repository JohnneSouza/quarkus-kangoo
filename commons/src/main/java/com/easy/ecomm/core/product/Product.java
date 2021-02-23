package com.easy.ecomm.core.product;

import io.quarkus.mongodb.panache.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Getter
@Setter
@MongoEntity(collection = "Products")
public class Product {

    @BsonId
    private ObjectId id;
    private String description;
    private String category;
    private String color;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private double priceSell;
    private double priceCost;
    private int stockAmount;
    private boolean active;

}
