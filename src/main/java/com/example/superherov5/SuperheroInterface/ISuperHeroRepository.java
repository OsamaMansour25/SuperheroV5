package com.example.superherov5.SuperheroInterface;

import com.example.superherov5.DTO.HeroInfoDTO;
import com.example.superherov5.DTO.SuperPowerDTO;
import com.example.superherov5.DTO.SuperheroFormDTO;
import com.example.superherov5.Model.SuperheroModel;

import java.util.List;

public interface ISuperHeroRepository {
    default void addSuperhero(SuperheroFormDTO form) {
    }
    default List<HeroInfoDTO> getAll() {
        return null;
    }
    default List<SuperheroModel> getSuperhero(String name) {
        return null;
    }
    default List<SuperheroModel> getSuperheroesFromCity(String cityName) {
        return null;
    }
    default SuperPowerDTO getSuperheroPower(String name) {
        return null;
    }
    default List<SuperPowerDTO> countSuperheroPower(String name) {
        return null;
    }

    default List<String> getSuperPowers() {
    return null;}

    default List<String> getCities() {
        return null;
    }

}
