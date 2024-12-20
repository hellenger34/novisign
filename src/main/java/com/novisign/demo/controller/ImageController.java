package com.novisign.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.service.ImageService;

import lombok.RequiredArgsConstructor;

/**
 * Dev notes: according to rest name convention, commented annotations should be preferably used, but I used the original ones on order to follow documentation
 */
@RestController
@RequestMapping("/")
//@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    //    @PostMapping("/")
    @PostMapping(value = "/addImage", produces = "application/json")
    public ResponseEntity<Image> addImage(@RequestBody final Image image) {
        Image createdImage = imageService.addImage(image);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdImage);
    }

    //    @DeleteMapping("/{id}")
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable final Long id) {
        boolean isDeleted = imageService.deleteImage(id);

        if (isDeleted) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .build();
        }
        /*
         * Dev notes: statuses depends on the requirements
         */
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();
    }

    //    @GetMapping("/search")
    @GetMapping("/images/search")
    public ResponseEntity<List<Image>> searchImages(@RequestParam final String keyword, @RequestParam final Integer duration) {
        List<Image> images = imageService.searchImages(keyword, duration);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(images);
    }
}
