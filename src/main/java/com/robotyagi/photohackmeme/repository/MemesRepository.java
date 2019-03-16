package com.robotyagi.photohackmeme.repository;

import com.robotyagi.photohackmeme.model.Memes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemesRepository extends JpaRepository<Memes, Long> {

    @Override
    List<Memes> findAll();
}
