package com.sample.store.productsservice.controllers;

import com.sample.store.productsservice.models.Product;
import com.sample.store.productsservice.models.ProductCategory;
import com.sample.store.productsservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productService.getProducts();
    }

    @GetMapping("/recommended")
    public List<Product> getRecommendedProducts(){
        return productService.getRecommendedProducts();
    }

    @GetMapping("/kategoria/{category}")
    //TODO:Checking if category exist
    public List<Product> getProductsByCategory(@PathVariable String category){
        return productService.getProductsByCategory(category);
    }


    @PostConstruct
    public void addProducts(){
        List<Product> products = new ArrayList<>();
        products.add(new Product(1,"Bluza 1","bluza-1","","",ProductCategory.BLUZA, "picture1",new BigDecimal(10), false));
        products.add(new Product(2,"Bluza Czarna 2","bluza-2-czarna","","", ProductCategory.BLUZA, "picture4",new BigDecimal(10), false));

        products.add(new Product(4,"T-Shirt Biały","tshirt-1-bialy","","", ProductCategory.TSHIRT, "picture2",new BigDecimal(10), false));
        products.add(new Product(5,"T-Shirt Czarny 1","tshirt-1-czarny","","", ProductCategory.TSHIRT, "picture5",new BigDecimal(10), false));
        products.add(new Product(6,"T-Shirt Czarny 2","tshirt-2-czarny","","",ProductCategory.TSHIRT, "picture8",new BigDecimal(10), true));

        //Recommended
        products.add(new Product(7,"Brelok 1","brelok-1","","", ProductCategory.GADZET, "picture6",new BigDecimal(10), false));
        products.add(new Product(8,"Kubek 1","kubek-1","","", ProductCategory.GADZET, "picture3",new BigDecimal(10), false));

        productService.addProducts(products);
    }

    @GetMapping("/categories")
    public List<ProductCategory> getCategories(){
        List<ProductCategory> categoriesList;
        categoriesList = productService.getCategories();

        return categoriesList;
    }

    @RequestMapping(value="/kategoria/{category}/details/{productNameUrl}", method = RequestMethod.GET)
    public ResponseEntity<Product> getProductDetails(@PathVariable String category, @PathVariable String productNameUrl){
        Product product = productService.getProductDetails(productNameUrl);
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product);
    }

}
