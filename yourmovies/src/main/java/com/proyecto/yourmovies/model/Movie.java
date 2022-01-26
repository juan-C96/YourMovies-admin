package com.proyecto.yourmovies.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "anno")
    private int anno;

    @Column(name = "duracion")
    private int duracion;

    @Column(name = "pais")
    private String pais;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "genero")
    private String genero;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "imagen")
    private String imagen;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    @JoinTable(name = "t_movies_actors", joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id"))
    List<Actor> actors;

    public Movie() {

    }

    public Movie(Long movie_id, String titulo, int anno, int duracion, String pais, String direccion, String genero, String sinopsis, String imagen, List<Actor> actors) {
        this.movie_id = movie_id;
        this.titulo = titulo;
        this.anno = anno;
        this.duracion = duracion;
        this.pais = pais;
        this.direccion = direccion;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
        this.actors = actors;
    }

    public Movie(String titulo, int anno, int duracion, String pais, String direccion, String genero, String sinopsis, String imagen) {
        this.titulo = titulo;
        this.anno = anno;
        this.duracion = duracion;
        this.pais = pais;
        this.direccion = direccion;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
    }

    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
