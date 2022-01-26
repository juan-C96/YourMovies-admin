package com.proyecto.yourmovies.service;

import com.proyecto.yourmovies.model.Movie;

import java.util.List;

public interface IMovieService {

    List<Movie> getAllMovies();

    Movie getMovieById(Long id);

    List<Movie> getMovieByTitulo(String titulo);

    List<Movie> getMoviesByGenero(String genero);

    void saveMovie(Movie movie);

    void updateMovie(Movie movie);

    void deleteMovie(Long id);
}
