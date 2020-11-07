package com.sample.store.imagesservice.controllers;

import com.sample.store.imagesservice.models.ProductImageLinks;
import com.sample.store.imagesservice.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.io.*;

import java.util.List;

@RestController
public class ImageController {

    private RestTemplate restTemplate;
    private ImageService imageService;

    @Autowired
    public ImageController(RestTemplate restTemplate, ImageService imageService) {
        this.restTemplate = restTemplate;
        this.imageService = imageService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = {"/kategoria/{category}/{nameUrl}", "/kategoria/{category}/{nameUrl}/{nameUrlExtra}"},
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(
            @PathVariable String category,
            @PathVariable String nameUrl,
            @PathVariable(required = false) String nameUrlExtra) throws IOException {

        category = category.toLowerCase();
        ClassPathResource imgFile;
        if(nameUrlExtra != null && nameUrlExtra.contains("hovered"))
            imgFile = new ClassPathResource("static/products/"+ category +"/"+ nameUrl + "/"+ nameUrlExtra + ".jpg");
        else if(nameUrlExtra !=null && nameUrlExtra.contains("big"))
            imgFile = new ClassPathResource("static/products/"+ category +"/"+ nameUrl + "/"+ nameUrlExtra + ".jpg");
        else
            imgFile = new ClassPathResource("static/products/"+ category +"/"+ nameUrl + "/"+ nameUrl + ".jpg");

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value="/kategoria/{category}/details/{productNameUrl}")
    public List<String> getProductImagesLinks(@PathVariable String category, @PathVariable String productNameUrl) throws Exception {

        category = category.toLowerCase();
        String resourcePath = "/static/products/" + category + "/" + productNameUrl;
        List<String> productImagesPath = imageService.readFolderFromJar(resourcePath);
        return imageService.getImagesLinks(productImagesPath, category, productNameUrl);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/links/{category}/{productNameUrl}")
    public ProductImageLinks getProductsImageLinkByCategory(@PathVariable String category, @PathVariable String productNameUrl) throws IOException {
        //TODO: Checking if service contains given "category" and "productNameUrl" in resources.
        ProductImageLinks productImageLinks = imageService.createProductImageLinks(category, productNameUrl);
        return productImageLinks;
    }
}
