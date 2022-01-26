package com.proyecto.yourmovies.dao;

import com.proyecto.yourmovies.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActorJPA extends JpaRepository<Actor, Long> {

}
