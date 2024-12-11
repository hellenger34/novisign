package com.novisign.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novisign.demo.service.ProofOfPlayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProofOfPlayController {

    private final ProofOfPlayService proofOfPlayService;

    @GetMapping("/slideshow/{slideshowId}/proof-of-play/{imageId}")
    public ResponseEntity<Void> proofOfPlay(@PathVariable Long slideshowId, @PathVariable Long imageId) {
        proofOfPlayService.proofOfPlay(slideshowId, imageId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }
}
