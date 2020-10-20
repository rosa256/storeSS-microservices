package com.sample.store.productsservice.services;

import com.sample.store.productsservice.models.Product;
import com.sample.store.productsservice.models.ProductCategory;
import com.sample.store.productsservice.models.ProductImageLinks;
import com.sample.store.productsservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private RestTemplate restTemplate;

    @Autowired
    public ProductService(ProductRepository productRepository, RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    public List<Product> getRecommendedProducts(){
        //TODO: Find Recommended products
        List<Product> productList = productRepository.findAll();
        List<Product> recommendedProducts = new ArrayList<>();

        for(Product p : productList){
            if (p.isRecommended())
                recommendedProducts.add(p);
        }
        return recommendedProducts;
    }

    public List<Product> getProductsByCategory(String category){
        List<Product> productsByCategory = productRepository.findAllByCategory(ProductCategory.valueOf(category));
        productsByCategory = productsByCategory.stream()
                .map(product -> {
                    String url = "http://images-service/links/" + product.getCategory() + "/" + product.getNameUrl();
                    ProductImageLinks productImages = restTemplate.getForObject(
                            url,
                            ProductImageLinks.class);
                    product.setImageUrl(productImages.getImageUrl());
                    product.setHoveredImageUrl(productImages.getHoveredImageUrl());
                    return product;
                }).collect(Collectors.toList());
        return productsByCategory;
    }

    public void addProducts(List<Product> products){
        for (Product p: products)
            productRepository.save(p);
    }
    public List<ProductCategory> getCategories(){
        List<ProductCategory> products = productRepository.findDistinctProd();

      return products;
    };

    public Product getProductDetails(String nameUrl) {
        return productRepository.findByNameUrl(nameUrl);
    }
}
