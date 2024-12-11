package com.novisign.demo.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.novisign.demo.model.entity.ImageDb;

public interface JpaImageRepository extends JpaRepository<ImageDb, Long> {

    Optional<ImageDb> findByUrl(String url);

    List<ImageDb> findByUrlContainingOrDurationSeconds(String url, Integer durationSeconds);
}
