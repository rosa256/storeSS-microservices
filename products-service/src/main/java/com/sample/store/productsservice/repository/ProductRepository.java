package com.sample.store.productsservice.repository;

import com.sample.store.productsservice.models.Product;
import com.sample.store.productsservice.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select distinct p.category from Product p")
    List<ProductCategory> findDistinctProd();

    List<Product> findAllByCategory(ProductCategory category);

    Product findByNameUrl(String nameUrl);
}
