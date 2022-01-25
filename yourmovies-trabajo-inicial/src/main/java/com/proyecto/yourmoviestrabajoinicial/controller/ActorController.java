package com.proyecto.yourmoviestrabajoinicial.controller;

import com.proyecto.yourmoviestrabajoinicial.model.Actor;
import com.proyecto.yourmoviestrabajoinicial.paginator.PageRender;
import com.proyecto.yourmoviestrabajoinicial.service.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/aactors")
public class ActorController {

    @Autowired
    IActorService actorService;

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

    @PostMapping("/guardar/")
    public String guardarActor(Model model, Actor actor, RedirectAttributes attributes) {
        actorService.saveActor(actor);
        model.addAttribute("titulo", "Nuevo actor");
        attributes.addFlashAttribute("msg", "Los datos del actor fueron guardados!");
        return "redirect:/aactors/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarActor(Model model, @PathVariable("id") Integer id) {
        Actor actor = actorService.getActorById(id);
        model.addAttribute("titulo", "Editar actor");
        model.addAttribute("actor", actor);
        return "actors/formActor";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarActor(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Actor actor = actorService.getActorById(id);
        if (actor != null) {
            actorService.deleteActor(id);
            attributes.addFlashAttribute("msg", "Los datos del actor fueron borrados!");
        } else {
            attributes.addFlashAttribute("msg", "Actor no encontrado!");
        }

        return "redirect:/aactors/listado";
    }
}