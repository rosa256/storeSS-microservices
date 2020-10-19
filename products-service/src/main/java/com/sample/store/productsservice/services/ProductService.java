package com.sample.store.productsservice.services;

import com.sample.store.productsservice.models.Product;
import com.sample.store.productsservice.models.ProductCategory;
import com.sample.store.productsservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
