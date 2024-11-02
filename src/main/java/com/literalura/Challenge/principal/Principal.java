package com.literalura.Challenge.principal;

import com.literalura.Challenge.model.*;
import com.literalura.Challenge.repository.AutorRepository;
import com.literalura.Challenge.repository.LibroRepository;
import com.literalura.Challenge.service.ConsumoAPI;
import com.literalura.Challenge.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private  LibroRepository libroRepository;
    private  AutorRepository autorRepository;


    @Autowired
    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void muestraElMenu() {

        while (true) {
            int option = validaOptionCase(teclado);

            switch (option) {
                case 1 -> buscarLibroMenu();
                case 2 -> mostrarLibrosRegistrados();
                case 3 -> mostrarAutoresRegistrados();
                case 4 -> buscarAutoresVivosanho();
                case 5 -> buscarLibrosPorIdioma();
                case 6 -> buscarAutoresPorNombre();
                case 7 -> top10LibrosMasDescargados();
                case 8 -> estadisticas();
                case 0 -> {
                    System.out.println("Cerrando la aplicación...");
                    return;
                }
                default -> System.out.println("Opción inválida");
            }
        }
    }




    //Busqueda de libros por nombre
    private void buscarLibroMenu() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();

        try {

            var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
            var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
            Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream().findFirst();

            if (libroBuscado.isPresent()) {
                DatosLibro datosLibro = libroBuscado.get();
                System.out.println("Libro encontrado: " + datosLibro.titulo());

                datosLibro.autor().stream().findFirst().ifPresent(datosAutor -> {
                    Autor autor = autorRepository.findByNombre(datosAutor.nombre());

                    // Verificar si el autor ya existe en la base de datos
                    if (autor == null) {
                        autor = new Autor();
                        autor.setNombre(datosAutor.nombre());
                        autor.setFechaDeNacimineto(datosAutor.fechaDeNacimiento());
                        autor.setFechaDeMuerte(datosAutor.fechaDeMuerte());
                        autorRepository.save(autor);
                        System.out.println("Autor guardado: " + autor.getNombre());
                    }

                    // Verificar si el libro ya existe en la base de datos
                    boolean libroExiste = libroRepository.existsByTituloAndAutor(datosLibro.titulo(), autor);
                    if (!libroExiste) {
                        // Crear y guardar el libro si no existe
                        Libro libro = new Libro();
                        libro.setTitulo(datosLibro.titulo());
                        libro.setAutor(autor);
                        libro.setIdioma(!datosLibro.idiomas().isEmpty() ? datosLibro.idiomas().get(0) : "Desconocido");
                        libro.setnDescargas(datosLibro.nDescargas());

                        libroRepository.save(libro);
                        System.out.println("Libro guardado exitosamente: " + libro.getTitulo());
                    } else {
                        System.out.println("El libro ya existe en la base de datos.");
                    }
                });

            } else {
                System.out.println("No se encontró el libro.");
            }

        } catch (Exception e) {
            System.out.println("Ocurrió un error al buscar o guardar el libro: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private void mostrarLibrosRegistrados() {

        // Recuperar todos los libros desde la base de datos
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("Libros registrados:");

            // Mostrar la información de cada libro
            for (Libro libro : libros) {
                System.out.println("-----------LIBRO-------------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Descargas: " + libro.getnDescargas());
                System.out.println("-------------------------------\n");
            }
        }
    }

    private void mostrarAutoresRegistrados() {

        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("Autores y sus libros registrados:");

            for (Autor autor : autores) {
                System.out.println("********** AUTOR ************");
                System.out.println("\nNombre: " + autor.getNombre());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaDeNacimineto());
                System.out.println("Fecha de Muerte: " +
                        (autor.getFechaDeMuerte() != null ? autor.getFechaDeMuerte() : "Sigue Vivo"));
                System.out.println("Libros:");

                if (autor.getLibros().isEmpty()) {
                    System.out.println("No tiene libros registrados.");
                    System.out.println("****************************");
                } else {
                    // Usamos StringBuilder para concatenar los títulos de libros con " || "
                    StringBuilder librosConcatenados = new StringBuilder();
                    for (Libro libro : autor.getLibros()) {
                        librosConcatenados.append(libro.getTitulo()).append(" || ");
                    }
                    // Eliminar el último " || "
                    librosConcatenados.setLength(librosConcatenados.length() - 4);

                    // Imprimir la lista de libros formateada
                    System.out.println(librosConcatenados);
                    System.out.println("****************************");
                }
            }
        }
    }

    private void buscarAutoresVivosanho() {

        int anho = -1;

        while (true) {
            System.out.print("Ingrese un año (4 dígitos): ");

            if (teclado.hasNextInt()) {
                anho = teclado.nextInt();
                teclado.nextLine();


                if (anho >= 1000 && anho <= 9999) {
                    break;
                } else {
                    System.out.println("Por favor, ingrese un año de 4 dígitos.");
                }
            } else {
                System.out.println("Error: Solo se permiten números. Inténtalo de nuevo.");
                teclado.next();
            }
        }

        List<Autor> autores = autorRepository.findAutoresVivosEnAnho(anho);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("Autores vivos en el año " + anho + ":\n");
            autores.forEach(autor -> System.out.println("- " + autor.getNombre() +
                    " || Fecha de nacimineto: " +autor.getFechaDeNacimineto() +
                    " || Fecha de muerte : " + autor.getFechaDeMuerte() +"\n"));
        }
    }


    private void buscarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar libros por ejemplo:");

        var menuIdiomas = """
                'en' -> Inglés
                'es' -> Español
                'fr' -> Francés
                'pt' -> Portugués
                """;

        System.out.println(menuIdiomas);
        System.out.print("Idioma : ");
        String idioma = teclado.nextLine();


        List<Object[]> libros = libroRepository.findLibrosPorIdioma(idioma);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma especificado.");
        } else {
            System.out.println("Libros encontrados:");
            for (Object[] libro : libros) {
                String titulo = (String) libro[0];
                String idiomaLibro = (String) libro[1];
                String autorNombre = (String) libro[2];

                System.out.println("-----------LIBRO-------------");
                System.out.println("Título: " + titulo);
                System.out.println("Autor: " + autorNombre);
                System.out.println("Idioma: " + idiomaLibro);
                System.out.println("-------------------------------\n");
            }
        }
    }

    public int validaOptionCase(Scanner teclado) {
        boolean inputValido = false;
        int option = -1;

        while (!inputValido) {
            var menu = """
                    1 - Buscar libro por titulo. 
                    2 - Listar libros registrados.
                    3 - Listar autores registrados.
                    4 - listar autores vivos en un determinado año.
                    5 - Listar libros por idioma
                    6 - Buscar autor por nombre
                    7 - Top 10 libros mas descargados
                    8 - Estadisticas
                  
                                  
                    0 - Salir
                    """;
            System.out.println(menu);

            System.out.print("Seleccione una opción: ");

            if (teclado.hasNextInt()) {
                option = teclado.nextInt();
                teclado.nextLine();

                if (option >= 0 && option <= 8) {
                    inputValido = true;
                } else {
                    System.out.println("Por favor, ingrese una opción válida entre 0 y 8.");
                }
            } else {
                System.out.println("Error: Solo se permiten números. Inténtalo de nuevo.");
                teclado.next();
            }
        }

        return option;
    }

    private void buscarAutoresPorNombre() {
        System.out.println("Ingrese el nombre o parte del nombre del autor:");
        String nombre = teclado.nextLine();

        List<Autor> autores = autorRepository.buscarAutoresPorNombre(nombre);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre.");
        } else {
            System.out.println("Autores encontrados: \n");
            autores.forEach(autor -> System.out.println("- " + autor.getNombre()+ "\n"));
        }
    }

    private void top10LibrosMasDescargados(){

        Pageable topTen = PageRequest.of(0, 10);
        List<Libro> libros = libroRepository.findTop10ByOrderByDescargasDesc(topTen);

        if (libros.isEmpty()){
            System.out.println("No se encontraron libros");
        }else {
            System.out.println("-------TOP 10 LIBROS -----------");
            libros.forEach(libro -> System.out.println("- " + libro.getTitulo() +" || Descargas : " + libro.getnDescargas()+"\n"));
            System.out.println("--------------------------------");

        }
    }

    private void estadisticas(){

        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros disponibles.");
            return;
        }

        // Obtener estadísticas de descargas
        DoubleSummaryStatistics stats = libros.stream()
                .mapToDouble(Libro::getnDescargas)
                .summaryStatistics();

        double maxDescargas = stats.getMax();
        double minDescargas = stats.getMin();

        // Filtrar los libros con máximo y mínimo descargas
        Optional<Libro> libroMasDescargado = libros.stream()
                .filter(libro -> libro.getnDescargas() == maxDescargas)
                .findFirst();

        Optional<Libro> libroMenosDescargado = libros.stream()
                .filter(libro -> libro.getnDescargas() == minDescargas)
                .findFirst();


        libroMasDescargado.ifPresent(libro ->
                System.out.println("\nLibro más descargado: " + libro.getTitulo() + " con " + libro.getnDescargas() + " descargas  \n"));

        libroMenosDescargado.ifPresent(libro ->
                System.out.println("  Libro menos descargado: " + libro.getTitulo() + " con " + libro.getnDescargas() + " descargas \n"));
    }







}
