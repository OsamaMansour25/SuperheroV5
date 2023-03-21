package com.example.superherov5.Model;

public class SuperheroModel {
    private  String heroName;
    private String realName;
    private int creationYear;
    private int id;
    private int id_City;
    private String city;
    private String powers;

    public String getPowers() {
        return powers;
    }

    public void setPowers(String powers) {
        this.powers = powers;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public SuperheroModel(int id, String heroName, String realName, int creationYear, int id_City, String powers) {
        this.heroName = heroName;
        this.realName = realName;
        this.creationYear = creationYear;
        this.id_City = id_City;
        this.id = id;
        this.powers = powers;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setCreationYear(int creationYear) {
        this.creationYear = creationYear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_City(int id_City) {
        this.id_City = id_City;
    }

    public SuperheroModel() {

    }

    public String getRealName() {
        return realName;
    }

    public String getHeroName() {
        return heroName;
    }
    public int getCreationYear() {
        return creationYear;
    }
    public int getId_City() {
        return id_City;
    }
    public int getId() {
        return id;
    }

}

