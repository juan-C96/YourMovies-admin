package com.proyecto.yourmoviestrabajoinicial.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long actor_id;

    @Column(name = "name")
    private String name;

    @Column(name = "f_born")
    private Date f_born;

    @Column(name = "country")
    private String country;

    public Actor() {

    }

    public Actor(Long actor_id, String name, Date f_born, String country) {
        this.actor_id = actor_id;
        this.name = name;
        this.f_born = f_born;
        this.country = country;
    }

    public Long getActor_id() {
        return actor_id;
    }

    public void setActor_id(Long actor_id) {
        this.actor_id = actor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getF_born() {
        return f_born;
    }

    public void setF_born(Date f_born) {
        this.f_born = f_born;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
