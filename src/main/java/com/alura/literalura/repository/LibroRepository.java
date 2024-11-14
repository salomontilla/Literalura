package com.alura.literalura.repository;

import com.alura.literalura.models.Idioma;
import com.alura.literalura.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByIdioma(Idioma idioma);
    List<Libro> findTop5ByOrderByNumeroDescargasDesc();
}
