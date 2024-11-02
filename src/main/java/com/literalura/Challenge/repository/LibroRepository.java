package com.literalura.Challenge.repository;

import com.literalura.Challenge.model.Autor;
import com.literalura.Challenge.model.Libro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    boolean existsByTituloAndAutor(String titulo, Autor autor);

    @Query("SELECT l.titulo, l.idioma, a.nombre " +
            "FROM Libro l " +
            "JOIN l.autor a " +
            "WHERE l.idioma = :idioma")
    List<Object[]> findLibrosPorIdioma(@Param("idioma") String idioma);

    @Query("SELECT l FROM Libro l ORDER BY l.nDescargas DESC")
    List<Libro> findTop10ByOrderByDescargasDesc(Pageable pageable);


}
