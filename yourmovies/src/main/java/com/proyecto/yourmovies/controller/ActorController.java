package com.proyecto.yourmovies.controller;

import com.proyecto.yourmovies.model.Actor;
import com.proyecto.yourmovies.service.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActorController {

    @Autowired
    private IActorService iActorService;

    public ActorController(IActorService iActorService) {
        super();
        this.iActorService = iActorService;
    }

    @GetMapping("/actors")
    public ResponseEntity<List<Actor>> allActors(){
        return ResponseEntity.ok(iActorService.getAllActors());
    }

    @PostMapping("/actors")
    public void addActor(@RequestBody Actor actor){
        iActorService.saveActor(actor);
    }

    @GetMapping("/actors/{id}")
    public Actor findActorById(@PathVariable(value = "id") Long actor_id){
        return iActorService.getActorById(actor_id);
    }

    @PutMapping("/actors")
    public ResponseEntity<Actor> updateActor(@RequestBody Actor actorDetails) {
        Actor actor = iActorService.getActorById(actorDetails.getActor_id());

        actor.setName(actorDetails.getName());
        actor.setF_born(actorDetails.getF_born());
        actor.setCountry(actorDetails.getCountry());

        iActorService.updateActor(actor);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/actors/{id}")
    public void deleteActor(@PathVariable(value = "id") Long actor_id){
        iActorService.deleteActor(actor_id);
    }
}
