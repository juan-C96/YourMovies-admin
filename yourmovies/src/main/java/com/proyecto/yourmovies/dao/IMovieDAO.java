package com.proyecto.yourmovies.dao;

import com.proyecto.yourmovies.model.Movie;

import java.util.List;

public interface IMovieDAO {

    List<Movie> buscarTodos();

    Movie buscarMoviePorId(Long movie_id);

    List<Movie> getMovieByTitulo(String titulo);

    List<Movie> getMoviesByGenero(String genero);

    void guardarMovie(Movie movie);

    void actualizarMovie(Movie movie);

    void eliminarMovie(Long movie_id);
}
