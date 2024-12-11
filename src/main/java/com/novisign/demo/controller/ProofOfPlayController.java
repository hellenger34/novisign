package com.novisign.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novisign.demo.model.dto.Slideshow;

@RestController
@RequestMapping("/")
public class ProofOfPlayController {

    @GetMapping("/slideshow/{slideshowId}/proof-of-play/{imageId}")
    public ResponseEntity<Slideshow> proofOfPlay(@PathVariable Long slideshowId, @PathVariable Long imageId) {
        return null;
    }

}
