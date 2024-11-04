package com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro (
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autors,
        @JsonAlias("languages") List<Idioma> idiomas,
        @JsonAlias("download_count") int numeroDescargas
) { }
