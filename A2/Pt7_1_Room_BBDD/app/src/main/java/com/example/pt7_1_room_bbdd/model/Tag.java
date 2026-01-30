package com.example.pt7_1_room_bbdd.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags")
public class Tag {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nom;

    public Tag(String nom) {
        this.nom = nom;
    }
}
