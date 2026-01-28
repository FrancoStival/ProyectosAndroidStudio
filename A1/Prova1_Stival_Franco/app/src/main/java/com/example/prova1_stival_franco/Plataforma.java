package com.example.prova1_stival_franco;

import java.io.Serializable;

public class Plataforma implements Serializable{

    private Integer id;

    private String name;

    private String slug;

    private Integer games_count;

    private String image_background;
    private String description;

    public Plataforma(Integer id, String slug, String name, Integer games_count, String image_background, String description) {
        this.id = id;
        this.slug = slug;
        this.name = name;
        this.games_count = games_count;
        this.image_background = image_background;
        this.description = description;
    }
    public Integer getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public Integer getGames_count() { return games_count; }
    public String getImageBackground() { return image_background; }
    public String getDescription() { return description; }


    public void setId(Integer id){this.id = id; }
    public void setSlug(String slug){this.slug = slug; }

    public void setName(String name){this.name = name; }
    public void setGames_count(Integer games_count){this.games_count = games_count; }
    public void setImageBackground(String image_background){this.image_background = image_background; }
    public void setDescription(String description){this.description = description; }

}
