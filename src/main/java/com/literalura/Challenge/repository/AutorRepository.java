package com.literalura.Challenge.repository;

import com.literalura.Challenge.model.Autor;
import com.literalura.Challenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {

    Autor findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimineto < :anho AND (a.fechaDeMuerte IS NULL OR a.fechaDeMuerte > :anho)")
    List<Autor> findAutoresVivosEnAnho(@Param("anho") int anho);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Autor> buscarAutoresPorNombre(@Param("nombre") String nombre);




}
