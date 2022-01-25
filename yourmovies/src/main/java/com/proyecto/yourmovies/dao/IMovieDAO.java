package com.proyecto.yourmovies.dao;

import com.proyecto.yourmovies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieDAO extends JpaRepository<Movie, Long> {

}
