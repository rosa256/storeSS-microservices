package com.sample.store.imagesservice.services;

import com.sample.store.imagesservice.models.ProductImageLinks;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImageService {

    public List<String> readFolderFromJar(String relativePath) throws Exception {
        URI resource = getClass().getClassLoader().getResource(relativePath).toURI();
        try (FileSystem jarFileSystem = FileSystems.newFileSystem(resource, Collections.emptyMap())) {
            String[] jarPath = resource.toString().split("!", 2);
            String jarReference = jarPath[1].replace("!", "");
            return readContent(jarFileSystem.getPath(jarReference));
        }
    }

    private List<String> readContent(Path path) {
        List<String> productDetailImages = new ArrayList<>();
        try {
            Stream<Path> list = Files.list(path);
            List<Path> paths = list.parallel().collect(Collectors.toList());
            paths.stream().parallel().forEach(filePath -> {
                if (Files.isDirectory(filePath)) {
                    readContent(filePath);
                }
                else {
                    try {
                        String fileName = filePath.toString().split("classes")[1];
                        if(fileName.contains("big")) {
                            productDetailImages.add(fileName);
                            System.out.println("File: " + fileName);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Unable to read file " + e.getMessage());
                    }
                }
            });
        }
        catch (Exception e) {
            System.out.println("Unable to read file " + e.getMessage());
        }

        return productDetailImages;
    }

    public List<String> getImagesLinks(List<String> listOfPaths, String category, String productNameUrl) {
        List<String> imagesNames = new ArrayList<>();
        for(int i = 0; i < listOfPaths.size(); i++){
            if(listOfPaths.get(i).contains("big")){

                //Take last segment of path. example: aa/bb/cc.jpg = cc.jpg
                String[] segments = listOfPaths.get(i).split("/");
                String imageFileName = segments[segments.length-1];

                imagesNames.add(imageFileName);
            }
        }

        List<String> productImagesLinks = createProductImagesLinks(imagesNames, category, productNameUrl);
        return productImagesLinks;
    }

    private List<String> createProductImagesLinks(List<String> imagesNames, String category, String productNameUrl) {
        List<String> productLinks = new ArrayList<>();
        category = category.toUpperCase();
        for(String imageName: imagesNames){
            String imageLink = "http://localhost:8000/api/images/kategoria/" + category + "/" + productNameUrl + "/" + imageName.replace(".jpg", "");
            System.out.println(imageLink);
            productLinks.add(imageLink);
        }
        return productLinks;
    }


    public ProductImageLinks createProductImageLinks(String category, String productNameUrl) {
        ProductImageLinks productImages = new ProductImageLinks();

        String fullImageLink = "http://localhost:8000/api/images/kategoria/" + category + "/" + productNameUrl;
        productImages.setImageUrl(fullImageLink);
        productImages.setHoveredImageUrl(fullImageLink + "-hovered");
        return productImages;
    }

}
