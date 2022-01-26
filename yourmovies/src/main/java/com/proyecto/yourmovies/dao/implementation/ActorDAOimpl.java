package com.proyecto.yourmovies.dao.implementation;

import com.proyecto.yourmovies.dao.IActorDAO;
import com.proyecto.yourmovies.dao.IActorJPA;
import com.proyecto.yourmovies.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActorDAOimpl implements IActorDAO {

    @Autowired
    IActorJPA actorJPA;

    public ActorDAOimpl(IActorJPA actorJPA) {
        super();
        this.actorJPA = actorJPA;
    }

    @Override
    public List<Actor> getAllActors() {
        return actorJPA.findAll();
    }

    @Override
    public Actor getActorPorId(Long actor_id) {
        Optional<Actor> optional = actorJPA.findById(actor_id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public void guardarActor(Actor actor) {
        actorJPA.save(actor);
    }

    @Override
    public void actualizarActor(Actor actor) {
        actorJPA.save(actor);
    }

    @Override
    public void eliminarActor(Long actor_id) {
        actorJPA.deleteById(actor_id);
    }
}
