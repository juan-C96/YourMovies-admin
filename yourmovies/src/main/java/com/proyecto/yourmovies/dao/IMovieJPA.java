package com.proyecto.yourmovies.dao;

import com.proyecto.yourmovies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMovieJPA extends JpaRepository<Movie,Long> {

    List<Movie> findByTituloContainingIgnoreCase(String titulo);

    List<Movie> findByGeneroContainingIgnoreCase(String genero);
}
