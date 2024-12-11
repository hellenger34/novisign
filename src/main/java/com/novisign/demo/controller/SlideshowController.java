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
import org.springframework.web.bind.annotation.RestController;

import com.novisign.demo.model.dto.Image;
import com.novisign.demo.model.dto.Slideshow;
import com.novisign.demo.service.SlideshowService;

import lombok.RequiredArgsConstructor;

/**
 * Dev notes: according to rest name convention, commented annotations should be preferably used, but I used the original ones on order to follow documentation
 */
@RestController
@RequestMapping("/")
//@RequestMapping("/slideshow")
@RequiredArgsConstructor
public class SlideshowController {

    private final SlideshowService slideshowService;

    //    @PostMapping("/")
    @PostMapping("/addSlideshow")
    public ResponseEntity<Slideshow> createSlideshow(@RequestBody List<Image> images) {
        Slideshow createdSlideshow = slideshowService.createSlideshow(images);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(createdSlideshow);
    }

    //    @DeleteMapping("/{id}")
    @DeleteMapping("deleteSlideshow/{id}")
    public ResponseEntity<Void> deleteSlideshow(@PathVariable Long id) {
        boolean isDeleted = slideshowService.deleteSlideshow(id);

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

    //    @GetMapping("/{id}/order")
    @GetMapping("/slideshow/{id}/slideshowOrder")
    public ResponseEntity<List<Image>> slideshowOrder(@PathVariable Long id) throws Exception {
        List<Image> sortedImages = slideshowService.getSlideshowOrder(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(sortedImages);
    }
}
