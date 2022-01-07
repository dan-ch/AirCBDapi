package com.example.api.repository;

import com.example.api.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    List<Opinion> findByOffer_Id(Long id);
}
