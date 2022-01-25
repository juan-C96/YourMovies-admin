package com.proyecto.yourmovies.controller;

import com.proyecto.yourmovies.service.IMovieService;
import com.proyecto.yourmovies.model.Actor;
import com.proyecto.yourmovies.service.IActorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ActorController {

    private IMovieService IMovieService;
    private IActorService iActorService;

    public ActorController(IMovieService IMovieService, IActorService iActorService) {
        super();
        this.IMovieService = IMovieService;
        this.iActorService = iActorService;
    }

    @GetMapping("/actors")
    public ResponseEntity<List<Actor>> allActors(){
        return ResponseEntity.ok(iActorService.getAllActors());
    }

    @PostMapping("/actors")
    public Actor addActor(@RequestBody Actor actor){
        return iActorService.saveActor(actor);
    }

    @GetMapping("/actors/{id}")
    public ResponseEntity<Actor> findActorById(@PathVariable(value = "actor_id")
                                                       Long actor_id){
        Actor actor = iActorService.getActorById(actor_id);
        return ResponseEntity.ok().body(actor);
    }

    @PutMapping("/actors/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable(value = "actor_id") Long actor_id,
                                             @RequestBody Actor actorDetails) {
        Actor actor = iActorService.getActorById(actor_id);

        actor.setName(actorDetails.getName());
        actor.setF_born(actorDetails.getF_born());
        actor.setCountry(actorDetails.getCountry());

        iActorService.updateActor(actor);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/actors/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable(value = "actor_id")
                                                    Long actor_id){
        iActorService.deleteActor(actor_id);

        return ResponseEntity.ok().build();
    }
}
