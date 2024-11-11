package com.alura.literalura.principal;

import com.alura.literalura.models.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.services.ConsumoAPI;
import com.alura.literalura.services.Conversor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    Scanner sc = new Scanner(System.in);
    LibroRepository repository;
    AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }
    public void mostrarMenu() {
        int opcion = -1;

        while (opcion != 0) {
            var menu = """
                    ----Literatura----
                    1 - Buscar libro
                    2 - Ver tu historial de libros
                    3 - Mostrar libros por idioma
                    4 - Mostrar autores registrados
                    5 - Mostrar autores vivos en un determinado año
                    0 - Salir
                    """;
            System.out.println(menu);

            try{
            System.out.println("Introduce el número de la opción que deseas: ");
            opcion = sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Vuelve a intentarlo!");
            }
            sc.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarHistorialLibros();
                    break;
                case 3:
                    mostrarEstadisticasPorIdioma();
                    break;
                case 4:
                    mostrarAutoresRegistrados();
                    break;
                case 5:
                    mostrarAutoresVivos();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
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


    private void buscarLibro() {
        System.out.println("Introduce el título del libro que deseas buscar: ");
        String titulo = sc.nextLine();
        try{
        DatosLibro datosLibro = obtenerDatosLibro(titulo);
            if (datosLibro == null) {
                System.out.println("No se encontró ningún libro con el título: " + titulo);
            } else {
                Optional<Autor> autorExistente = autorRepository.findByNombre(datosLibro.autors().get(0).nombre());
                Autor autor = autorExistente.orElseGet(() ->
                        new Autor(datosLibro.autors().get(0).nombre(),
                                datosLibro.autors().get(0).anioNacimiento(),
                                datosLibro.autors().get(0).anioFallecimiento()));

                Libro libro = new Libro(datosLibro, autor);
                System.out.println(libro.toString());
                try {
                    repository.save(libro);
                }catch (DataIntegrityViolationException e) {
                    if (e.getCause() instanceof ConstraintViolationException) {
                        System.out.println("Este libro ya ha sido guardado!");
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Error al intentar buscar el libro: " + e.getMessage());
        }

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
        mostrarLibrosPorIdioma();
    }

    public void mostrarLibrosPorIdioma() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nIntroduce el idioma de los libros que deseas buscar:\n" +
                "es -> español\n"+
                "en -> inglés\n" +
                "fr -> francés\n"+
                "pt -> portugués");
        String idiomaInput = scanner.nextLine();

        Idioma idioma;
        try {
            idioma = Idioma.obtenerIdioma(idiomaInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no válido. Asegúrate de ingresar un idioma soportado.");
            return;
        }

        List<Libro> librosEnIdioma = repository.findByIdioma(idioma);
        if (!librosEnIdioma.isEmpty()) {
            librosEnIdioma.forEach(libro -> System.out.println(libro.toString()));
        } else {
            System.out.println("No se encontraron libros registrados en ese idioma.");
        }
    }

    private void mostrarAutoresRegistrados() {
        System.out.println("-----Autores registrados-----");
        List<Autor> autores = autorRepository.findAll();
        if(!autores.isEmpty()){
            autores.forEach(this::autorToString);
        }else{
            System.out.println("No se encontraron autores!");
        }

    }

    private void mostrarAutoresVivos() {
        try{
            System.out.println("------Autores Vivos-----");
            System.out.println("Ingresa el año: ");
            int anioIngresado = sc.nextInt();
            List<Autor> autores = autorRepository.findAutoresVivosEnAno(anioIngresado);
            if(!autores.isEmpty()){
                autores.forEach(this::autorToString);
            }else{
                System.out.println("No se encontraron autores vivos!");
            }
        }catch (InputMismatchException e){
            System.out.println("Ingresa un valor válido!");
        }



    }

    private void autorToString(Autor autor) {
        System.out.println("-----Autor------\n" +
                "Nombre: " + autor.getNombre() + '\n' +
                "Nacido: " + autor.getAnioNacimiento() + '\n' +
                "Fallecido: " + (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Aún vive") + '\n' +
                "Libros: ");

        if (autor.getLibros() != null && !autor.getLibros().isEmpty()) {
            autor.getLibros().forEach(libro -> System.out.println(" - " + libro.getTitulo()));
        } else {
            System.out.println(" - No tiene libros registrados.");
        }
    }
}
