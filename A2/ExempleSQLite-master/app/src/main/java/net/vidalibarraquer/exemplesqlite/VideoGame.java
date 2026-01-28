package net.vidalibarraquer.exemplesqlite;

public class VideoGame {private int id;
    private String nom;
    private String empresa;

    public VideoGame() {
    }

    public VideoGame(int id, String empresa, String nom) {
        this.id = id;
        this.empresa = empresa;
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmpresa() {
        return empresa;
    }
}

