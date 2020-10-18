package com.sample.store.imagesservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class ImageController {

    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/kategoria/{category}/{nameUrl}", "/kategoria/{nameUrl}/{imageName}"},
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImageLink(
            @PathVariable String category,
            @PathVariable String nameUrl,
            @PathVariable(required = false) String imageName) throws IOException {
        category = category.toLowerCase();
        ClassPathResource imgFile;

        if(imageName != null)
            imgFile = new ClassPathResource("static/products/"+ category +"/"+ nameUrl + "/"+ imageName + ".jpg");
        else
            imgFile = new ClassPathResource("static/products/"+ category +"/"+ nameUrl + "/"+ nameUrl + ".jpg");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

    @RequestMapping(value="/kategoria/{category}/{productNameUrl}", method = RequestMethod.GET)
    public List<String> getProductImagesLinks(@PathVariable String category, @PathVariable String productNameUrl) throws IOException {
        List<String> productImagesLinks = new ArrayList<>();
        category = category.toLowerCase();

        String resourcePath = "static/products/" + category + "/" + productNameUrl;
        ClassPathResource imagesFolder = new ClassPathResource(resourcePath);
        productImagesLinks = getImagesLinks(imagesFolder.getFile().listFiles(), category, productNameUrl);
        return productImagesLinks;
    }

    private List<String> getImagesLinks(File[] listOfFiles, String category, String productNameUrl) {
        List<String> imagesNames = new ArrayList<>();
        for(int i = 0; i < listOfFiles.length; i++){
            if(listOfFiles[i].getName().contains("big")){
                imagesNames.add(listOfFiles[i].getName());
            }
        }

        List<String> productImagesLinks = createProductImagesLinks(imagesNames, category, productNameUrl);
        return productImagesLinks;
    }

    private List<String> createProductImagesLinks(List<String> imagesNames, String category, String productNameUrl) {
        List<String> productLinks = new ArrayList<>();
        category = category.toUpperCase();
        for(String imageName: imagesNames){
            String imageLink = "http://localhost:8080/img/kategoria/" + category + "/" + productNameUrl + "/" + imageName.replace(".jpg", "");
            productLinks.add(imageLink);
        }
        return productLinks;
    }

//    @RequestMapping(value = "/kategoria/{category}", method = RequestMethod.GET,
//            produces = MediaType.IMAGE_JPEG_VALUE)
//    public ResponseEntity<List<Product>> getProductsImageLinkByCategory(@PathVariable String category) throws IOException {
//        List<Product> productsByCategory = productService.getProductsByCategory(category);
//        setFullImageLinkForProducts(productsByCategory, category);
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(productsByCategory);
//    }
//
//    private void setFullImageLinkForProducts(List<Product> products, String category) {
//        for(Product product : products) {
//            String fullImageLink = "http://localhost:8080/img/kategoria/" + category + "/" + product.getNameUrl();
//            product.setImageUrl(fullImageLink);
//            product.setHoveredImageUrl(fullImageLink + "-hovered");
//        }
//    }
}
