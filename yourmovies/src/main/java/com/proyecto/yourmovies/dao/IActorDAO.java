package com.proyecto.yourmovies.dao;

import com.proyecto.yourmovies.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActorDAO extends JpaRepository<Actor, Long> {

}
