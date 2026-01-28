package com.example.prova1_stival_franco;

import java.io.Serializable;

public class Juego implements Serializable {

    private Integer id;

    private String slug;

    private String name;

    private String released;

    private Double rating;

    public Juego(Integer id, String slug, String name, String released , Double rating) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.released = released;
        this.rating = rating;
    }
    public Integer getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getReleased() { return released; }
    public Double getRating() { return rating; }

    public void setId(Integer id){this.id = id; }
    public void setSlug(String slug){this.slug = slug; }
    public void setName(String name){this.name = name; }
    public void setReleased(String released){this.released = released; }
    public void setRating(Double rating){this.rating = rating; }

}
