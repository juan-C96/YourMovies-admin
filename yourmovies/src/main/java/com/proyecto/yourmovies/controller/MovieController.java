package com.proyecto.yourmovies.controller;

import com.proyecto.yourmovies.model.Movie;
import com.proyecto.yourmovies.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private IMovieService iMovieService;

    public MovieController(IMovieService iMovieService) {
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

    @GetMapping("/movies/actor/{actor}")
    public List<Movie> getMoviesByActor(@PathVariable("actor") String actor) {
        List<Movie> movies = iMovieService.getAllMovies();
        List<Movie> movies_2 = new ArrayList<>();

        Boolean flag = true;

        for(int i = 0; i < movies.size(); i++) {
            for (int j = 0; j < movies.get(i).getActors().size(); j++) {
                if (movies.get(i).getActors().get(j).getName().contains(actor)){
                    for(int a = 0; a < movies_2.size(); a++) {
                        if(movies_2.get(a).getMovie_id() == movies.get(i).getMovie_id()){
                            flag = false;
                        }
                    }
                    if(flag){
                        movies_2.add(movies.get(i));
                    }
                    flag = true;
                }
            }
        }
        return movies_2;
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
