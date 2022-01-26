package com.proyecto.yourmovies.service;

import com.proyecto.yourmovies.model.Actor;

import java.util.List;

public interface IActorService {

    List<Actor> getAllActors();

    Actor getActorById(Long id);

    void saveActor(Actor actor);

    void updateActor(Actor actor);

    void deleteActor(Long id);
}
