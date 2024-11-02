package com.alura.literalura.principal;

import com.alura.literalura.models.DatosLibro;
import com.alura.literalura.models.DatosRespuesta;
import com.alura.literalura.models.Libro;
import com.alura.literalura.services.ConsumoAPI;
import com.alura.literalura.services.Conversor;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("----Literalura----");
        System.out.println("1 - Buscar libro");
        System.out.println("2 - Ver tu historial de libros");
        System.out.println("3 - Ver tu historial de autores");
        System.out.println("4 - Mostrar autores vivos en un determinado año");
        System.out.println("5 - Ver libros por idiomas");
        System.out.println("------------------");
        seleccion();
    }

    private void seleccion() {
        System.out.println("Introduce el número de la opción que deseas: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                bucarLibro();
                mostrarMenu();
                break;
            case 2:
                // mostrarHistorialLibros();
                break;
            case 3:
                // mostrarHistorialAutores();
                break;
            case 4:
                // mostrarAutoresVivos();
                break;
            case 5:
                // mostrarLibrosPorIdioma();
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
    }
    String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private Conversor conversor = new Conversor();

    private DatosLibro obtenerDatosLibro(String busqueda) {
        String json = consumoApi.obtenerDatos(URL_BASE + busqueda.replace(" ", "+"));
        DatosRespuesta respuesta = conversor.convertirDatos(json, DatosRespuesta.class);

        return respuesta.libros().stream()
                .findFirst()
                .map(l -> new DatosLibro(l.titulo(), l.autors(), l.idiomas()))
                .orElse(null);
    }


    private void bucarLibro() {
        System.out.println("Introduce el título del libro que deseas buscar: ");
        String titulo = sc.nextLine();
        DatosLibro datosLibro = obtenerDatosLibro(titulo);
        Libro libro = new Libro(datosLibro);
        System.out.println(libro.toString());

    }
}