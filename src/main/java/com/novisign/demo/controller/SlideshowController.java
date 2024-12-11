package com.novisign.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;

/**
 * Dev notes: according to rest name convention, commented annotations should be preferably used, but I used the original ones on order to follow documentation
 */
@RestController
@RequestMapping("/")
//@RequestMapping("/slideshow")
public class SlideshowController {

    //    @PostMapping("/")
    @PostMapping("/addSlideshow")
    public ResponseEntity<Slideshow> createSlideshow(@RequestBody List<Image> images) {
        return null;
    }

//    @DeleteMapping("/{id}")
    @DeleteMapping("deleteSlideshow/{id}")
    public ResponseEntity<Void> deleteSlideshow(@PathVariable Long id) {
        return null;
    }

//    @GetMapping("/{id}/order")
    @GetMapping("/slideshow/{id}/slideshowOrder")
    public ResponseEntity<List<Image>> slideshowOrder(@PathVariable Long id) {
        return null;
    }
}
