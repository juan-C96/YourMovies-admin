package com.proyecto.yourmovies.service.implementation;

import com.proyecto.yourmovies.model.Movie;
import com.proyecto.yourmovies.dao.IMovieDAO;
import com.proyecto.yourmovies.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    IMovieDAO movieRepository;

    public MovieServiceImpl(IMovieDAO movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.buscarTodos();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.buscarMoviePorId(id);
    }

    @Override
    public List<Movie> getMovieByTitulo(String titulo) {
        return movieRepository.getMovieByTitulo(titulo);
    }

    @Override
    public List<Movie> getMoviesByGenero(String genero) {
        return movieRepository.getMoviesByGenero(genero);
    }

    @Override
    public void saveMovie(Movie movie) {

        Movie movie_n;
            if(movie.getActors() == null){
                movie_n = new Movie(movie.getTitulo(), movie.getAnno(),
                        movie.getDuracion(), movie.getPais(), movie.getDireccion(), movie.getGenero(),
                        movie.getSinopsis(), movie.getImagen());

                movieRepository.guardarMovie(movie_n);
            } else {
                movie_n = new Movie(movie.getMovie_id(), movie.getTitulo(), movie.getAnno(),
                        movie.getDuracion(), movie.getPais(), movie.getDireccion(), movie.getGenero(),
                        movie.getSinopsis(), movie.getImagen(), movie.getActors());

                movieRepository.guardarMovie(movie_n);
            }

    }

    @Override
    public void updateMovie(Movie movie) {
        movieRepository.actualizarMovie(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.eliminarMovie(id);
    }
}
