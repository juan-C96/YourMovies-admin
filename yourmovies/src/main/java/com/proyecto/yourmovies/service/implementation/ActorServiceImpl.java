package com.proyecto.yourmovies.service.implementation;

import com.proyecto.yourmovies.model.Actor;
import com.proyecto.yourmovies.dao.IActorDAO;
import com.proyecto.yourmovies.service.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements IActorService {

    @Autowired
    IActorDAO actorRepository;

    public ActorServiceImpl(IActorDAO actorRepository) {
        super();
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.getAllActors();
    }

    @Override
    public Actor getActorById(Long id) {
        return actorRepository.getActorPorId(id);
    }

    @Override
    public void saveActor(Actor actor) {

        Actor actor_n = new Actor(actor.getName(), actor.getF_born(), actor.getCountry());

        actorRepository.guardarActor(actor_n);
    }

    @Override
    public void updateActor(Actor actor) {
        actorRepository.guardarActor(actor);
    }

    @Override
    public void deleteActor(Long id) {
        actorRepository.eliminarActor(id);
    }
}
