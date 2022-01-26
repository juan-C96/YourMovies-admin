package com.proyecto.yourmovies.dao;

import com.proyecto.yourmovies.model.Actor;

import java.util.List;


public interface IActorDAO {

    List<Actor> getAllActors();

    Actor getActorPorId(Long actor_id);

    void guardarActor(Actor actor);

    void actualizarActor(Actor actor);

    void eliminarActor(Long actor_id);
}
