package com.proyecto.yourmoviestrabajoinicial.service.implementation;

import com.proyecto.yourmoviestrabajoinicial.model.Actor;
import com.proyecto.yourmoviestrabajoinicial.model.Movie;
import com.proyecto.yourmoviestrabajoinicial.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MovieServiceImpl implements IMovieService {

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8090/api/movies/movies";

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        Movie[] movies = template.getForObject(url, Movie[].class);
        List<Movie> moviesList = Arrays.asList(movies);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Movie> list;

        if (moviesList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, moviesList.size());
            list = moviesList.subList(startItem, toIndex);
        }

        Page<Movie> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), moviesList.size());
        return page;
    }

    @Override
    public Movie getMovieById(Long movie_id) {
        Movie movie = template.getForObject(url + "/" + movie_id, Movie.class);
        return movie;
    }

    @Override
    public Page<Movie> getMovieByTitulo(String titulo, Pageable pageable) {
        Movie[] movies = template.getForObject(url + "/titulo/" + titulo, Movie[].class);
        List<Movie> lista = Arrays.asList(movies);
        Page<Movie> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }

    @Override
    public Page<Movie> getMovieByGenero(String genero, Pageable pageable) {
        Movie[] movies = template.getForObject(url + "/genero/" + genero, Movie[].class);
        List<Movie> lista = Arrays.asList(movies);
        Page<Movie> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }

    @Override
    public Page<Movie> getMovieByActor(String actor, Pageable pageable) {
        Movie[] movies = template.getForObject(url + "/actor/" + actor, Movie[].class);
        List<Movie> lista = Arrays.asList(movies);
        Page<Movie> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }

    @Override
    public void saveMovie(Movie movie) {
        if (movie.getMovie_id() != null && movie.getMovie_id() > 0) {
            template.put(url, movie);
        } else {
            Integer i = 0;
            Long j = i.longValue();
            movie.setMovie_id(j);
            template.postForObject(url, movie, String.class);
        }
    }

    @Override
    public void deleteMovie(Long movie_id) {template.delete(url + "/" + movie_id);
    }
}
