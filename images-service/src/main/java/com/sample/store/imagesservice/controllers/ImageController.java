package com.sample.store.imagesservice.controllers;

import com.sample.store.imagesservice.models.ProductImageLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class ImageController {

    private RestTemplate restTemplate;

    @Autowired
    public ImageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/kategoria/{category}/{nameUrl}", "/kategoria/{category}/{nameUrl}/{nameUrlHovered}"},
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(
            @PathVariable String category,
            @PathVariable String nameUrl,
            @PathVariable(required = false) String nameUrlHovered) throws IOException {

        category = category.toLowerCase();
        ClassPathResource imgFile;
        if(nameUrlHovered != null)
            imgFile = new ClassPathResource("static/products/"+ category +"/"+ nameUrl + "/"+ nameUrlHovered + ".jpg");
        else
            imgFile = new ClassPathResource("static/products/"+ category +"/"+ nameUrl + "/"+ nameUrl + ".jpg");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

    //TODO:Propably have to fix when Integration with react app.
//    @RequestMapping(value="/kategoria/{category}/details/{productNameUrl}", method = RequestMethod.GET)
//    public List<String> getProductImagesLinks(@PathVariable String category, @PathVariable String productNameUrl) throws IOException {
//        List<String> productImagesLinks = new ArrayList<>();
//        category = category.toLowerCase();
//
//        String resourcePath = "static/products/" + category + "/" + productNameUrl;
//        ClassPathResource imagesFolder = new ClassPathResource(resourcePath);
//        productImagesLinks = getImagesLinks(imagesFolder.getFile().listFiles(), category, productNameUrl);
//        return productImagesLinks;
//    }

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

    @RequestMapping(value = "/links/{category}/{productNameUrl}", method = RequestMethod.GET)
    public ProductImageLinks getProductsImageLinkByCategory(@PathVariable String category, @PathVariable String productNameUrl) throws IOException {
        System.out.println("IMAGE SERVICE START");
        //TODO: Checking if service contains given "category" and "productNameUrl" in resources.
        ProductImageLinks productImageLinks = createProductImageLinks(category, productNameUrl);
        System.out.println("IMAGE SERVICE END");
        return productImageLinks;
    }

    private ProductImageLinks createProductImageLinks(String category, String productNameUrl) {
        ProductImageLinks productImages = new ProductImageLinks();

        String fullImageLink = "http://localhost:8000/api/images/kategoria/" + category + "/" + productNameUrl;
        productImages.setImageUrl(fullImageLink);
        productImages.setHoveredImageUrl(fullImageLink + "-hovered");
        return productImages;
    }
}
