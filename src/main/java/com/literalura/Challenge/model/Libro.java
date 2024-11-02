package com.literalura.Challenge.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Double nDescargas;

    @Override
    public String toString() {
        return "*********** - Libro - *********** \n" +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idioma='" + idioma + '\'' +
                ", Numero de descargas=" + nDescargas +'\'' +
                "*********************************\n";
    }

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = convertirIdiomas(datosLibro.idiomas()).toString();
        this.nDescargas = datosLibro.nDescargas();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getnDescargas() {
        return nDescargas;
    }

    public void setnDescargas(Double nDescargas) {
        this.nDescargas = nDescargas;
    }

    // Método que asegura que idioma siempre sea una lista válida
    private List<String> convertirIdiomas(List<String> idiomas) {
        try {
            // Verifica si la lista es nula; si no, la retorna. Si es nula, devuelve una lista vacía.
            return idiomas != null ? new ArrayList<>(idiomas) : new ArrayList<>();
        } catch (ClassCastException e) {
            System.err.println("Error al convertir idiomas: " + e.getMessage());
            return new ArrayList<>(); // En caso de error, devuelve una lista vacía para evitar fallos.
        }
    }
}
