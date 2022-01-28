package com.proyecto.yourmoviestrabajoinicial.service.implementation;

import com.proyecto.yourmoviestrabajoinicial.model.Actor;
import com.proyecto.yourmoviestrabajoinicial.service.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ActorServiceImpl implements IActorService {

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8090/api/movies/actors";

    @Override
    public Page<Actor> getAllActor(Pageable pageable) {
        Actor[] actors = template.getForObject(url, Actor[].class);
        List<Actor> actorsList = Arrays.asList(actors);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Actor> list;

        if(actorsList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, actorsList.size());
            list = actorsList.subList(startItem, toIndex);
        }
        Page<Actor> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), actorsList.size());
        return page;
    }

    @Override
    public Actor getActorById(Long actor_id) {
        Actor actor = template.getForObject(url + "/" + actor_id , Actor.class);
        return actor;
    }

    @Override
    public void saveActor(Actor actor) {
        if (actor.getActor_id() != null && actor.getActor_id() > 0) {
            template.put(url, actor);
        } else {
            Integer i = 0;
            Long j = i.longValue();
            actor.setActor_id(j);
            template.postForObject(url, actor, String.class);
        }
    }

    @Override
    public void deleteActor(Long actor_id) {
        template.delete(url + "/" + actor_id);
    }
}
