package com.proyecto.yourmoviestrabajoinicial.controller;

import com.proyecto.yourmoviestrabajoinicial.model.Movie;
import com.proyecto.yourmoviestrabajoinicial.paginator.PageRender;
import com.proyecto.yourmoviestrabajoinicial.service.IMovieService;
import com.proyecto.yourmoviestrabajoinicial.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
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

@Controller
@RequestMapping("mmovies")
public class MovieController {

    @Autowired
    IMovieService movieService;

    @Autowired
    private IUploadFileService uploadFileService;

    public MovieController(IMovieService movieService) {
        super();
        this.movieService = movieService;
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
    public String home(Model model) {
        return "home";
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(Model model, @PathVariable("id") Long id, RedirectAttributes attributes) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("titulo", "Detalle de la película: " + movie.getTitulo());
        return "movies/verMovie";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Movie movie = new Movie();
        model.addAttribute("titulo", "Nueva película");
        model.addAttribute("movie", movie);
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

    @GetMapping("/idcurso/{id}")
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

        movieService.saveMovie(movie);
        model.addAttribute("titulo", "Nueva película");
        attributes.addFlashAttribute("msg", "Los datos de la película fueron guardados!");
        return "redirect:/mmovies/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarMovie(Model model, @PathVariable("id") Long id) {
        Movie movie = movieService.getMovieById(id);
        model.addAttribute("titulo", "Editar película");
        model.addAttribute("movie", movie);
        return "movies/formMovie";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarCurso(Model model, @PathVariable("id") Long id, RedirectAttributes attributes) {
        Movie movie = movieService.getMovieById(id);
        if (uploadFileService.delete(movie.getImagen())) {
            attributes.addFlashAttribute("msg", "Imagen " + movie.getImagen() + " eliminada con exito!");
        }

        movieService.deleteMovie(id);
        attributes.addFlashAttribute("msg", "Los datos de la peícula fueron borrados!");

        return "redirect:/mmovies/listado";
    }
}
