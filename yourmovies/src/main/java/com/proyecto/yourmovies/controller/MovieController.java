package com.proyecto.yourmovies.controller;

import com.proyecto.yourmovies.model.Movie;
import com.proyecto.yourmovies.service.IActorService;
import com.proyecto.yourmovies.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private IMovieService iMovieService;

    public MovieController(IMovieService iMovieService, IActorService iActorService) {
        super();
        this.iMovieService = iMovieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> allMovies(){
        return ResponseEntity.ok(iMovieService.getAllMovies());
    }

    @PostMapping("/movies")
    public void addMovie(@RequestBody Movie movie){
        iMovieService.saveMovie(movie);
    }

    @GetMapping("/movies/{id}")
    public Movie findMovieById(@PathVariable(value = "id") Long movie_id){
        return iMovieService.getMovieById(movie_id);
    }

    @GetMapping("/movies/titulo/{titulo}")
    public List<Movie> findMoviesByTitulo(@PathVariable("titulo") String titulo) {
        return iMovieService.getMovieByTitulo(titulo);
    }

    @GetMapping("/movies/genero/{genero}")
    public List<Movie> getMoviesByGenero(@PathVariable("genero") String genero) {
        return iMovieService.getMoviesByGenero(genero);
    }

    @PutMapping("/movies")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movieDetails) {

        Movie movie = iMovieService.getMovieById(movieDetails.getMovie_id());

        movie.setTitulo(movieDetails.getTitulo());
        movie.setSinopsis(movieDetails.getSinopsis());
        movie.setGenero(movieDetails.getGenero());
        movie.setPais(movieDetails.getPais());
        movie.setDireccion(movieDetails.getDireccion());
        movie.setDuracion(movieDetails.getDuracion());
        movie.setAnno(movieDetails.getAnno());
        movie.setImagen(movieDetails.getImagen());
        movie.setActors(movieDetails.getActors());

        iMovieService.updateMovie(movie);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@PathVariable(value = "id") Long id){
        iMovieService.deleteMovie(id);
    }
}
