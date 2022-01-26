package com.proyecto.yourmoviestrabajoinicial.service;

import com.proyecto.yourmoviestrabajoinicial.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActorService {

    Page<Actor> getAllActor(Pageable pageable);

    Actor getActorById(Long actor_id);
    /*
      Page<Actor> getActorByName(String name);

      Page<Actor> getActorByPais(String pais);
      */
    void saveActor(Actor actor);

    void deleteActor(Long actor_id);
}
