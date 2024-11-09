package com.alura.literalura.principal;

import com.alura.literalura.models.DatosLibro;
import com.alura.literalura.models.DatosRespuesta;
import com.alura.literalura.models.Idioma;
import com.alura.literalura.models.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.services.ConsumoAPI;
import com.alura.literalura.services.Conversor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner sc = new Scanner(System.in);
    LibroRepository repository;

    public Principal(LibroRepository repository) {
        this.repository = repository;
    }
    public void mostrarMenu() {
        System.out.println("----Literalura----");
        System.out.println("1 - Buscar libro");
        System.out.println("2 - Ver tu historial de libros");
        System.out.println("3 - Mostrar libros por idioma");
        System.out.println("4 - Mostrar autores vivos en un determinado año");
        System.out.println("5 - Ver libros por idiomas");
        System.out.println("------------------");
        seleccion();
    }

    private void seleccion() {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.println("Introduce el número de la opción que deseas: ");
            String entrada = sc.nextLine();

            // Verificar si la entrada es un número
            if (esNumero(entrada)) {
                opcion = Integer.parseInt(entrada);
                entradaValida = true;
            } else {
                System.out.println("Entrada inválida. Por favor, introduce solo números.");
            }
        }

        switch (opcion) {
            case 1:
                bucarLibro();
                mostrarMenu();
                break;
            case 2:
                mostrarHistorialLibros();
                mostrarMenu();
                break;
            case 3:
                mostrarEstadisticasPorIdioma();
                mostrarMenu();
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

    // Método auxiliar para verificar si una cadena es numérica
    private boolean esNumero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
                .map(l -> new DatosLibro(l.titulo(), l.autors(), l.idiomas(), l.numeroDescargas()))
                .orElse(null);
    }


    private void bucarLibro() {
        System.out.println("Introduce el título del libro que deseas buscar: ");
        String titulo = sc.nextLine();
        DatosLibro datosLibro = obtenerDatosLibro(titulo);
        Libro libro = new Libro(datosLibro);
        System.out.println(libro.toString());
        repository.save(libro);
    }

    List<Libro> libros;
    private void mostrarHistorialLibros(){
        System.out.println("-----Historial-----");
        libros = repository.findAll();
        libros.forEach(System.out::println);
    }

    public void mostrarEstadisticasPorIdioma() {
        Map<Idioma, Long> estadisticas = repository.findAll().stream()
                .collect(Collectors.groupingBy(Libro::getIdioma, Collectors.counting()));

        estadisticas.forEach((idioma, count) ->
                System.out.println("Idioma: " + idioma + ", Cantidad de libros: " + count));
    }
}
