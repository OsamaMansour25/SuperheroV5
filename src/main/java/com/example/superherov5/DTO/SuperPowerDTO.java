package com.example.superherov5.DTO;

public class SuperPowerDTO {
    int count;
    String heroName;
    String realName;

    public SuperPowerDTO(String heroName, String realName, int count) {
        this.heroName = heroName;
        this.realName = realName;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getHeroName() {
        return heroName;
    }

    public String getRealName() {
        return realName;
    }
}
