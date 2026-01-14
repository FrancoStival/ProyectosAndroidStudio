package net.vidalibarraquer.exemplehttpjson;

public class Personatge {
    private String name;
    private String planet;
    private String image;

    public Personatge(String name, String planet, String image) {
        this.name = name;
        this.planet = planet;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
