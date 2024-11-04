package com.alura.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titulo;
    @Enumerated(EnumType.STRING)
    Idioma idioma;
    int numeroDescargas;

    @OneToOne(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Autor autor;

    public Libro(){}

    public Libro(String titulo, Autor autor, Idioma idioma, int numeroDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }
    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autors().getFirst().nombre(), datosLibro.autors().getFirst().anioNacimiento(),
                                datosLibro.autors().getFirst().anioFallecimiento());
        this.idioma = datosLibro.idiomas().getFirst();
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "------Libro-----\n" +
                "Titulo: " + titulo + '\n' +
                "Autor: " + autor.nombre + '\n' +
                "Idioma: " + idioma +'\n' +
                "Descargas: " + numeroDescargas;

    }
}
