package com.proyecto.yourmovies.dao.implementation;

import com.proyecto.yourmovies.dao.IMovieDAO;
import com.proyecto.yourmovies.dao.IMovieJPA;
import com.proyecto.yourmovies.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDAOimpl implements IMovieDAO {

    @Autowired
    IMovieJPA movieJPA;

    public MovieDAOimpl(IMovieJPA movieJPA) {
        super();
        this.movieJPA = movieJPA;
    }

    @Override
    public List<Movie> buscarTodos() {
        return movieJPA.findAll();
    }

    @Override
    public Movie buscarMoviePorId(Long movie_id) {
        Optional<Movie> optional = movieJPA.findById(movie_id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Movie> getMovieByTitulo(String titulo) {
        return movieJPA.findByTituloContainingIgnoreCase(titulo);
    }

    @Override
    public List<Movie> getMoviesByGenero(String genero) {
        return movieJPA.findByGeneroContainingIgnoreCase(genero);
    }

    @Override
    public void eliminarMovie(Long movie_id) {
        movieJPA.deleteById(movie_id);
    }

    @Override
    public void guardarMovie(Movie movie) {
        movieJPA.save(movie);
    }

    @Override
    public void actualizarMovie(Movie movie) {
        movieJPA.save(movie);
    }
}
