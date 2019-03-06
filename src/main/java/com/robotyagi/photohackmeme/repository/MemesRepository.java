package com.robotyagi.photohackmeme.repository;

import com.robotyagi.photohackmeme.model.Memes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemesRepository extends JpaRepository<Memes, Long> {

    @Override
    List<Memes> findAll();
}
