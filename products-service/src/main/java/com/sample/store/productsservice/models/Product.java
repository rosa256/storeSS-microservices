package com.sample.store.productsservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
public class Product {
    //For Marshaling and UnMarshaling
    public Product() { }
    @Id
    private Integer id;
    private String name;
    private String nameUrl;
    private String imageUrl;
    private String hoveredImageUrl; // image showing on mouse hover. *Not required*
    private ProductCategory category;
    private String description;
    private BigDecimal price;
    private boolean isRecommended;

}
