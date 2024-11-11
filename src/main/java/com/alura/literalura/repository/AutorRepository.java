package com.alura.literalura.repository;

import com.alura.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Integer> {
    @Query("SELECT a FROM Autor a WHERE " +
            "(a.anioNacimiento <= :year AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento >= :year))")
    List<Autor> findAutoresVivosEnAno(Integer year);
}
