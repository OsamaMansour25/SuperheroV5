package com.example.superherov5.SuperheroInterface;

import com.example.superherov5.DTO.SuperPowerDTO;
import com.example.superherov5.Model.SuperheroModel;

import java.util.List;

public interface ISuperHeroRepository {
    default List<SuperheroModel> getAll() {
        return null;
    }
    default List<SuperheroModel> getSuperhero(String name) {
        return null;
    }
    default List<SuperheroModel> getSuperheroesFromCity(String cityName) {
        return null;
    }
    default List<SuperheroModel> getSuperheroPower(String name) {
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
