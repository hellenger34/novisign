package com.novisign.demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novisign.demo.model.entity.SlideshowDb;

public interface JpaSlideshowRepository extends JpaRepository<SlideshowDb, Long> {

}
