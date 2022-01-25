package com.proyecto.yourmovies.service;

import com.proyecto.yourmovies.model.Movie;

import java.util.List;

public interface IMovieService {

    List<Movie> getAllMovies();

    Movie getMovieById(Long id);

    void saveMovie(Movie movie);

    Movie updateMovie(Movie movie);

    void deleteMovie(Long id);
}
