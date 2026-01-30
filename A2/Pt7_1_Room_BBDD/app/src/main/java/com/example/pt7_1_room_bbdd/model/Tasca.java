package com.example.pt7_1_room_bbdd.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasques")
public class Tasca {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String titol;
    public String descripcio;
    public String estat; // pendent, en procés, completada
    public long dataCreacio;
    public long dataCanvi;

    public Tasca(String titol, String descripcio, String estat, long dataCreacio, long dataCanvi) {
        this.titol = titol;
        this.descripcio = descripcio;
        this.estat = estat;
        this.dataCreacio = dataCreacio;
        this.dataCanvi = dataCanvi;
    }
}
