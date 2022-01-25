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

  /*  public MovieServiceImpl(IMovieDAO movieRepository) {
        super();
        this.movieRepository = movieRepository;
    }*/

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.getById(id);
    }

    @Override
    public void saveMovie(Movie movie) {

        Movie movie_n;
            if(movie.getActors() == null){
                movie_n = new Movie(movie.getTitulo(), movie.getAnno(),
                        movie.getDuracion(), movie.getPais(), movie.getDireccion(), movie.getGenero(),
                        movie.getSinopsis());

                movieRepository.save(movie_n);
            } else {
                movie_n = new Movie(movie.getMovie_id(), movie.getTitulo(), movie.getAnno(),
                        movie.getDuracion(), movie.getPais(), movie.getDireccion(), movie.getGenero(),
                        movie.getSinopsis(), movie.getImagen(), movie.getActors());

                movieRepository.save(movie_n);
            }

    }

    @Override
    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
