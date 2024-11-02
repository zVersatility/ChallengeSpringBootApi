package com.literalura.Challenge.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private String fechaDeNacimineto;
    private String fechaDeMuerte;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;


    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimineto = datosAutor.fechaDeNacimiento();
        this.fechaDeMuerte = datosAutor.fechaDeMuerte();
    }

    @Override
    public String toString() {
        return "*********** - Autor - ***********\n" +
                ", Nombre= '" + nombre + '\'' +
                ", Fecha de nacimineto= '" + fechaDeNacimineto + '\'' +
                ", Fecha de muerte= '" + fechaDeMuerte + '\'' +
                ", Libros= " + libros + '\'' +
                "********************************\n";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimineto() {
        return fechaDeNacimineto;
    }

    public void setFechaDeNacimineto(String fechaDeNacimineto) {
        this.fechaDeNacimineto = fechaDeNacimineto;
    }

    public String getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(e -> e.setAutor(this));
        this.libros = libros;
    }
}
