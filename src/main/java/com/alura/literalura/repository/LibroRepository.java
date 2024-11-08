package com.alura.literalura.repository;

import com.alura.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

}
