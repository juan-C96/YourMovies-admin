package com.proyecto.yourmoviestrabajoinicial.service;

import com.proyecto.yourmoviestrabajoinicial.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMovieService {

    Page<Movie> getAllMovies(Pageable pageable);

    Movie getMovieById(Long movie_id);
  /*
    Page<Movie> getMovieByTitulo(String titulo);

    Page<Movie> getMovieByAnno(Integer anno);

    Page<Movie> getMovieByGenero(String genero);
    */
    void saveMovie(Movie movie);

    void deleteMovie(Long movie_id);
}
