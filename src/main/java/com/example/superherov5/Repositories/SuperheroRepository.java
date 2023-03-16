package com.example.superherov5.Repositories;

import com.example.superherov5.DTO.SuperPowerDTO;
import com.example.superherov5.DTO.SuperheroFormDTO;
import com.example.superherov5.Model.SuperheroModel;
import com.example.superherov5.SuperheroInterface.ISuperHeroRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository("Superhero_db")
public class SuperheroRepository implements ISuperHeroRepository {
    @Value("${spring.datasource.url}")
    String db_url;
    @Value("${spring.datasource.username}")
    String u_id;
    @Value("${spring.datasource.password}")
    String pwd;
    List<String> superPowers = new ArrayList<>();
    public List<SuperheroFormDTO> getAll() {
      //  List<SuperheroModel> superheroes = new ArrayList<>();
        List<SuperheroFormDTO> superheroesDTO = new ArrayList<>();
        List<String> superheroPowers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT * from superhero join superpower join superheropower join city\n" +
                    "ON superheropower.id_Superpower = superpower.id\n" +
                    "AND superhero.id = superheropower.id_Superhero\n" +
                    "AND city.id = superhero.id_City";
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(SQL);
            while (rst.next()) {
                String heroName = rst.getString("heroName");
                String realName = rst.getString("realName");
                int creationYear = Integer.parseInt(rst.getString("creationYear"));
                String powers = rst.getString("name");
                superheroPowers.add(powers);
                int id = Integer.parseInt(rst.getString("id"));
                String city = rst.getString("city_name");


                superheroesDTO.add(new SuperheroFormDTO(id, heroName, realName, creationYear, city, superheroPowers));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return superheroesDTO;
    }
    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT name\n" +
                    "FROM `superheroes`.`city`";
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(SQL);
            while (rst.next()) {
                String name = rst.getString("name");
                cities.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }
    public List<String> getSuperPowers() {
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT name FROM superheroes.superpower";
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(SQL);
            while (rst.next()) {
                String name = rst.getString("name");
                superPowers.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return superPowers;
    }

    public List<SuperheroModel> getSuperhero(String name) {
        List<SuperheroModel> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT * FROM superhero WHERE heroName = ? OR realName = ?";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, name);
            pst.setString(2, name);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                SuperheroModel superhero = new SuperheroModel(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5));
                superheroes.add(superhero);
            }

            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SuperheroModel> getSuperheroesFromCity(String cityName) {
        List<SuperheroModel> listOfSuperheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT * from superhero join city where city.id = superhero.id_city AND city.name= ?";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, cityName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SuperheroModel sr = new SuperheroModel(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5));
                listOfSuperheroes.add(sr);

            }
            return listOfSuperheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SuperheroModel> getSuperheroPower(String name) {
        List<SuperheroModel> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT * from superhero join superpower join superheropower\n" +
                    "ON superheropower.id_Superpower = superpower.id\n" +
                    "AND superhero.id = superheropower.id_Superhero\n" +
                    "AND superpower.name = ?";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                SuperheroModel superhero = new SuperheroModel(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getInt(4), rs.getInt(5));
                superheroes.add(superhero);
            }
            System.out.println(superheroes);
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<SuperPowerDTO> countSuperheroPower(String name) {
        List<SuperPowerDTO> superheroes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT superhero.id, superhero.heroName, realName, count(superheropower.id_Superhero) " +
                    "AS count from superhero join superheropower where heroName = ? group by superhero.id;";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String heroName = rs.getString("heroName");
                String realName = rs.getString("realName");
                int count = rs.getInt("count");
                SuperPowerDTO spd = new SuperPowerDTO(heroName, realName, count);

                superheroes.add(spd);
            }
            return superheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
