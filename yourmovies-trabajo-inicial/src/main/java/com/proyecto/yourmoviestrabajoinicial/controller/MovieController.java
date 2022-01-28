package com.proyecto.yourmoviestrabajoinicial.controller;

import com.proyecto.yourmoviestrabajoinicial.model.Actor;
import com.proyecto.yourmoviestrabajoinicial.model.Movie;
import com.proyecto.yourmoviestrabajoinicial.paginator.PageRender;
import com.proyecto.yourmoviestrabajoinicial.service.IActorService;
import com.proyecto.yourmoviestrabajoinicial.service.IMovieService;
import com.proyecto.yourmoviestrabajoinicial.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("mmovies")
public class MovieController {

    @Autowired
    IMovieService movieService;

    @Autowired
    IActorService actorService;

    @Autowired
    private IUploadFileService uploadFileService;

    private List<Actor> actores;

    public MovieController(IMovieService movieService, IActorService actorService, List<Actor> actores) {
        super();
        this.movieService = movieService;
        this.actorService = actorService;
        this.actores = new ArrayList<>();
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Movie> listadoP = movieService.getAllMovies(pageable);
        PageRender<Movie> pageRender = new PageRender<Movie>("/mmovies/home", listadoP);
        model.addAttribute("titulo", "Películas");
        model.addAttribute("listadoMovies", listadoP);
        model.addAttribute("page", pageRender);
        return "home";
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(Model model, @PathVariable("id") Long id, RedirectAttributes attributes) {
        Movie movie = movieService.getMovieById(id);

        List<String> nombres = new ArrayList<>();

        for (int i = 0; i < movie.getActors().size(); i++) {
            nombres.add(movie.getActors().get(i).getName());
        }

        model.addAttribute("nombres", nombres);
        model.addAttribute("movie", movie);
        model.addAttribute("titulo", "Detalle de la película: " + movie.getTitulo());
        return "movies/verMovie";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Movie movie = new Movie();
        List<Actor> actores_list = movie.getActors();
        model.addAttribute("titulo", "Nueva película");
        model.addAttribute("movie", movie);
        model.addAttribute("actores_list", actores_list);
        return "movies/formMovie";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        return "movies/searchMovie";
    }

    @GetMapping("/listado")
    public String listadoMovies(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Movie> listado = movieService.getAllMovies(pageable);
        PageRender<Movie> pageRender = new PageRender<Movie>("/mmovies/listado", listado);
        model.addAttribute("titulo", "Listado de todas las películas");
        model.addAttribute("listadoMovies", listado);
        model.addAttribute("page", pageRender);
        return "movies/listMovie";
    }

    @GetMapping("/idMovie/{id}")
    public String buscarMoviePorId(Model model, @PathVariable("id") Long id) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        return "movies/formMovie";
    }

    @GetMapping("/titulo")
    public String buscarMoviesPorTitulo(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("titulo") String titulo) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Movie> listado;
        if (titulo.equals("")) {
            listado = movieService.getAllMovies(pageable);
        } else {
            listado = movieService.getMovieByTitulo(titulo, pageable);
        }
        PageRender<Movie> pageRender = new PageRender<Movie>("/listado", listado);
        model.addAttribute("titulo", "Listado de películas por título");
        model.addAttribute("listadoMovies", listado.getContent());
        model.addAttribute("page", pageRender);
        return "movies/listMovie";
    }

    @GetMapping("/genero")
    public String buscarMoviesPorGenero(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("genero") String genero) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Movie> listado = movieService.getMovieByGenero(genero, pageable);
        PageRender<Movie> pageRender = new PageRender<Movie>("/listado", listado);
        model.addAttribute("titulo", "Listado de películas por genero");
        model.addAttribute("listadoMovies", listado.getContent());
        model.addAttribute("page", pageRender);
        return "movies/listMovie";
    }

    @GetMapping("/actor")
    public String buscarMoviesPorActor(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("actor") String actor) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Movie> listado;
        if (actor.equals("")) {
            listado = movieService.getAllMovies(pageable);
        } else {
            listado = movieService.getMovieByActor(actor, pageable);
        }
        PageRender<Movie> pageRender = new PageRender<Movie>("/listado", listado);
        model.addAttribute("titulo", "Listado de películas por Actor");
        model.addAttribute("listadoMovies", listado.getContent());
        model.addAttribute("page", pageRender);
        return "movies/listMovie";
    }

    @PostMapping("/guardar/")
    public String guardarMovie(Model model, Movie movie,
                               @RequestParam("file") MultipartFile foto, RedirectAttributes attributes) {
//    	if(curso != null) {
//    		System.out.println(curso.getNombre());
//    	}

        if (!foto.isEmpty()) {
            if (movie.getMovie_id() != null && movie.getMovie_id() > 0 && movie.getImagen() != null
                    && movie.getImagen().length() > 0) {

                uploadFileService.delete(movie.getImagen());
            }

            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }

            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");

            movie.setImagen(uniqueFilename);
        }

        if(movie.getMovie_id() == null){
            actores = new ArrayList<>();
        }

        movie.setActors(actores);
        movieService.saveMovie(movie);
        model.addAttribute("titulo", "Nueva película");
        attributes.addFlashAttribute("msg", "Los datos de la película fueron guardados!");
        return "redirect:/mmovies/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarMovie(Model model, @PathVariable("id") Long id) {
        Movie movie = movieService.getMovieById(id);
        List<Actor> actores_list = movie.getActors();
        model.addAttribute("titulo", "Editar película");
        model.addAttribute("movie", movie);
        model.addAttribute("actores_list", actores_list);
        return "movies/formMovie";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarMovie(Model model, @PathVariable("id") Long id, RedirectAttributes attributes) {
        Movie movie = movieService.getMovieById(id);
        if (uploadFileService.delete(movie.getImagen())) {
            attributes.addFlashAttribute("msg", "Imagen " + movie.getImagen() + " eliminada con exito!");
        }

        movieService.deleteMovie(id);
        attributes.addFlashAttribute("msg", "Los datos de la peícula fueron borrados!");

        return "redirect:/mmovies/listado";
    }

    @GetMapping("/repartoAdd/{id}")
    public String addReparto(Model model, @RequestParam(name="page", defaultValue="0") int page, @PathVariable("id") Long id) {
        Pageable pageable = PageRequest.of(page, 100);
        Page<Actor> listado = actorService.getAllActor(pageable);
        List<Actor> listado_3 = listado.getContent();
        List<Actor> listado_2 = new ArrayList<>();

        Movie movie = movieService.getMovieById(id);
        List<Actor> movie_actor = movie.getActors();

        Boolean flag = true;

        for(int i = 0; i < listado_3.size(); i++){
            for(int j = 0; j < movie_actor.size(); j++){
                if(listado_3.get(i).getActor_id() == movie_actor.get(j).getActor_id()){
                  flag = false;
                }
            }
            if(flag){
                listado_2.add(listado_3.get(i));
            }
            flag = true;
        }

        Page<Actor> newPage = new PageImpl<>(listado_2);

        PageRender<Actor> pageRender = new PageRender<Actor>("/mmovies/reparto", newPage);
        model.addAttribute("titulo", "Listado de todos los actores");
        model.addAttribute("listadoActores", newPage);
        model.addAttribute("page", pageRender);
        model.addAttribute("idMovie", id);
        return "movies/reparto";
    }

    @GetMapping("/repartoDelet/{id}")
    public String deletReparto(Model model, @RequestParam(name="page", defaultValue="0") int page, @PathVariable("id") Long id) {
        Pageable pageable = PageRequest.of(page, 100);
        Page<Actor> listado = actorService.getAllActor(pageable);
        Movie movie = movieService.getMovieById(id);
        List<Actor> movie_actor = movie.getActors();

        Page<Actor> newPage = new PageImpl<>(movie_actor);


        PageRender<Actor> pageRender = new PageRender<Actor>("/mmovies/listado", newPage);
        model.addAttribute("titulo", "Listado de todos los actores");
        model.addAttribute("listadoActores", newPage);
        model.addAttribute("page", pageRender);
        model.addAttribute("idMovie", id);
        return "movies/repartoDelet";
    }

    @GetMapping("/addActorM/{id}A{movie_id}")
    public String addActorM(@PathVariable("id") String id, @PathVariable("movie_id") String movie_id, RedirectAttributes attributes) {

        Long a = Long.parseLong(id);
        Long b = Long.parseLong(movie_id);

        Actor actor = actorService.getActorById(a);
        Movie movie = movieService.getMovieById(b);

        List<Actor> actors_list = movie.getActors();

        if (actor != null) {
            actors_list.add(actor);
            movie.setActors(actors_list);
            movieService.saveMovie(movie);
            actores = movie.getActors();
        } else {
            attributes.addFlashAttribute("msg", "Actor no encontrado!");
        }

        String url = "redirect:/mmovies/repartoAdd/";

        return url+b;
    }

    @GetMapping("/deleteActorM/{id}A{movie_id}")
    public String deleteActorM(@PathVariable("id") String id, @PathVariable("movie_id") String movie_id,RedirectAttributes attributes) {

        Long a = Long.parseLong(id);
        Long b = Long.parseLong(movie_id);

        Actor actor = actorService.getActorById(a);
        Movie movie = movieService.getMovieById(b);

        List<Actor> actors_list = movie.getActors();

        if (actor != null) {
            for(int i = 0; i < actors_list.size(); i++){
                if(actors_list.get(i).getActor_id() == a){
                    actors_list.remove(i);
                }
            }
            movie.setActors(actors_list);
            movieService.saveMovie(movie);
            actores = movie.getActors();
        } else {
            attributes.addFlashAttribute("msg", "Actor no encontrado!");
        }

        String url = "redirect:/mmovies/editar/";

        return url+b;
    }
}
