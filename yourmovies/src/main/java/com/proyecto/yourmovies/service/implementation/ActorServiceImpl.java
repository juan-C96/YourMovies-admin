package com.proyecto.yourmovies.service.implementation;

import com.proyecto.yourmovies.model.Actor;
import com.proyecto.yourmovies.dao.IActorDAO;
import com.proyecto.yourmovies.service.IActorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements IActorService {

    IActorDAO actorRepository;

    public ActorServiceImpl(IActorDAO actorRepository) {
        super();
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Actor getActorById(Long id) {
        return actorRepository.getById(id);
    }

    @Override
    public Actor saveActor(Actor actor) {
        Actor actor_n = new Actor(actor.getActor_id(), actor.getName(), actor.getF_born(), actor.getCountry());

        return actorRepository.save(actor_n);
    }

    @Override
    public Actor updateActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}
