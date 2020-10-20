package com.sample.store.imagesservice.models;

import lombok.Data;

@Data
public class ProductImageLinks {

    public ProductImageLinks() { }

    private String imageUrl;
    private String hoveredImageUrl;
}
