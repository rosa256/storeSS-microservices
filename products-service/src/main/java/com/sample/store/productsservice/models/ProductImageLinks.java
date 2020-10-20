package com.sample.store.productsservice.models;

import lombok.Data;

@Data
public class ProductImageLinks {

    public ProductImageLinks() { }

    private String imageUrl;
    private String hoveredImageUrl;
}
