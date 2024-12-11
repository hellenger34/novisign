package com.novisign.demo.controller;

import java.util.List;

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

/**
 * Dev notes: according to rest name convention, commented annotations should be preferably used, but I used the original ones on order to follow documentation
 */
@RestController
@RequestMapping("/")
//@RequestMapping("/image")
public class ImageController {

    //    @PostMapping("/")
    @PostMapping("/addImage")
    public ResponseEntity<Image> addImage(@RequestBody final Image image) {
        return null;
    }

    //    @DeleteMapping("/{id}")
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<Void> deleteImageById(@PathVariable final Long id) {
        return null;
    }

    //    @GetMapping("/search")
    @GetMapping("/images/search")
    public ResponseEntity<List<Image>> searchImages(@RequestParam final String keyword, @RequestParam final Integer duration) {
        return null;
    }

}
