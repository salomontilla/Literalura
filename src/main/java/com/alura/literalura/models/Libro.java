package com.alura.literalura.models;

import java.util.List;

public class Libro {

    String titulo;
    Autor autor;
    Idioma idioma;

    public Libro(){}

    public Libro(String titulo, Autor autor, Idioma idioma) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
    }
    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autors().getFirst().nombre(), datosLibro.autors().getFirst().anioNacimiento(),
                                datosLibro.autors().getFirst().anioFallecimiento());
        this.idioma = datosLibro.idiomas().getFirst();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return "------Libro-----\n" +
                "Titulo: '" + titulo + '\n' +
                "Autor: " + autor.nombre + '\n' +
                "Idioma: " + idioma;
    }
}
