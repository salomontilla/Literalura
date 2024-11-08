package com.alura.literalura.services;

import com.alura.literalura.models.Libro;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    LibroRepository repository;


}
