package com.example.superherov5.DTO;

import java.util.List;

public class SuperPowerDTO {
    private String heroName;
    private List<String> powerlist;

    public List<String> getPowerlist() {
        return powerlist;
    }

    public SuperPowerDTO(String heroName, List<String> powerlist) {
        this.heroName = heroName;
        this.powerlist = powerlist;
    }


    public String getHeroName() {
        return heroName;
    }
    public void addPower(String power) {
        powerlist.add(power);
    }

}
