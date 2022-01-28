package com.proyecto.yourmoviestrabajoinicial.controller;

import com.proyecto.yourmoviestrabajoinicial.model.Actor;
import com.proyecto.yourmoviestrabajoinicial.model.Movie;
import com.proyecto.yourmoviestrabajoinicial.paginator.PageRender;
import com.proyecto.yourmoviestrabajoinicial.service.IActorService;
import com.proyecto.yourmoviestrabajoinicial.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/aactors")
public class ActorController {

    @Autowired
    IActorService actorService;

    @Autowired
    IMovieService movieService;

    @GetMapping("/listado")
    public String listadoActores(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> listado = actorService.getAllActor(pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/aactors/listado", listado);
        model.addAttribute("titulo", "Listado de todos los actores");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "actors/listActor";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Actor actor = new Actor();
        model.addAttribute("titulo", "Nuevo Actor");
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(Model model, @PathVariable("id") Long id, RedirectAttributes attributes) {
        Actor actor = actorService.getActorById(id);
        System.out.println("AAAAA");
        model.addAttribute("actor", actor);
        model.addAttribute("name", "Detalle del actor: " + actor.getName());
        System.out.println("actor "+actor.getActor_id());
        return "actors/verActor";
    }

    @PostMapping("/guardar/")
    public String guardarActor(Model model, Actor actor, RedirectAttributes attributes) {
        actorService.saveActor(actor);
        model.addAttribute("titulo", "Nuevo actor");
        attributes.addFlashAttribute("msg", "Los datos del actor fueron guardados!");
        return "redirect:/aactors/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarActor(Model model, @PathVariable("id") Long id) {
        Actor actor = actorService.getActorById(id);
        model.addAttribute("titulo", "Editar actor");
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarActor(Model model, @PathVariable("id") Long id, RedirectAttributes attributes,
                                @RequestParam(name="page", defaultValue="0") int page) {

        Pageable pageable = PageRequest.of(page, 5);
        Actor actor = actorService.getActorById(id);
        Page<Movie> listado = movieService.getAllMovies(pageable);
        List<Movie> peliculas = listado.getContent();

        Boolean flag = true;

        for (int i = 0; i < peliculas.size(); i++) {
            for (int j= 0; j < peliculas.get(i).getActors().size(); j++) {
                if(peliculas.get(i).getActors().get(j).getActor_id() == id){
                    flag = false;
                }
            }
        }
        if(!flag){
            attributes
                    .addFlashAttribute("mensaje", "No puede eliminar el actor porque se ecuentra en una película.")
                    .addFlashAttribute("clase", "success");
        }else{
            if (actor != null) {
                actorService.deleteActor(id);
                attributes.addFlashAttribute("msg", "Los datos del actor fueron borrados!");
            } else {
                attributes.addFlashAttribute("msg", "Actor no encontrado!");
            }
        }

        return "redirect:/aactors/listado";
    }
}
