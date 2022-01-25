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
  //  private IActorService iActorService;

   /* public MovieController(IMovieService iMovieService, IActorService iActorService) {
        super();
        this.iMovieService = iMovieService;
        this.iActorService = iActorService;
    }*/

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

    @PutMapping("/movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable(value = "movie_id") Long movie_id,
                                             @RequestBody Movie movieDetails) {
        Movie movie = iMovieService.getMovieById(movie_id);

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
        System.out.println("aaaaaaa ");
        iMovieService.deleteMovie(id);
        System.out.println("bbbbbb ");
    }
}
