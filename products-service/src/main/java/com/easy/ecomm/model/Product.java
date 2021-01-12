package com.easy.ecomm.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Product {

    private String id;
    private String description;
    private String category;
    private String color;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private double price;
    private int stockAmount;

}
