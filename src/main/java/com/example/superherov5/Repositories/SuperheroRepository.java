package com.example.superherov5.Repositories;

import com.example.superherov5.DTO.HeroInfoDTO;
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


    public List<HeroInfoDTO> getAll() {
        List<HeroInfoDTO> superheroes = new ArrayList<>();
        //  List<String> superheroPowers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT * from superhero";
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(SQL);
            while (rst.next()) {
                String heroName = rst.getString("heroName");
                String realName = rst.getString("realName");
                int creationYear = Integer.parseInt(rst.getString("creationYear"));


                superheroes.add(new HeroInfoDTO(heroName, realName, creationYear));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return superheroes;
    }

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT city_name\n" +
                    "FROM `superheroes`.`city`";
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(SQL);
            while (rst.next()) {
                String name = rst.getString("city_name");
                cities.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }

    public List<String> getSuperPowers() {
        List<String> superPowers = new ArrayList<>();
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
                        rs.getInt(4), rs.getInt(5), rs.getString(6));
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
                        rs.getInt(4), rs.getInt(5), rs.getString(6));
                listOfSuperheroes.add(sr);

            }
            return listOfSuperheroes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public SuperPowerDTO getSuperheroPower(String name) {
        SuperPowerDTO superPowers = null;
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            String SQL = "SELECT * from superhero join superpower join superheropower\n" +
                    "ON superheropower.id_Superpower = superpower.id\n" +
                    "AND superhero.id = superheropower.id_Superhero\n" +
                    "AND heroName = ?";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            String currentName = "";
            while (rs.next()) {
               String heroesName = rs.getString("heroName");
               String powers = rs.getString("name");
               if(currentName.equals(heroesName)) {
                   superPowers.addPower(powers);
               }
               else {

                   superPowers = new SuperPowerDTO(heroesName, new ArrayList<>(List.of(powers)));
                   currentName = heroesName;
               }
            }
            return superPowers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addSuperHero(SuperheroFormDTO form) {
        try (Connection con = DriverManager.getConnection(db_url, u_id, pwd)) {
            // ID's
            int id_city = 0;
            int heroId = 0;
            List<Integer> powerIDs = new ArrayList<>();
            // find city_id
            String SQL1 = "select id_city from city where name = ?;";
            PreparedStatement pstmt = con.prepareStatement(SQL1);
            pstmt.setString(1, form.getCity());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                id_city = rs.getInt("id_city");
            }
            // insert row in superhero table
            String SQL2 = "insert into superhero (heroName, realName, creationYear, id_City) " +
                    "values(?, ?, ?, ?);";
            pstmt = con.prepareStatement(SQL2, Statement.RETURN_GENERATED_KEYS); // return autoincremented key
            pstmt.setString(1, form.getHeroName());
            pstmt.setString(2, form.getRealName());
            pstmt.setInt(3, form.getCreationYear());
            pstmt.setInt(4, id_city);
            int rows = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                heroId = rs.getInt(1);
            }
            // find power_ids
            String SQL3 = "select id_Superpower from superpower where name = ?;";
            pstmt = con.prepareStatement(SQL3);
            for (String power : form.getPowerList()) {
                pstmt.setString(1, power);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    powerIDs.add(rs.getInt("id_Superpower"));
                }
            }
            // insert entries in superhero_powers join table
            //TODO;
            String SQL4 = "insert into superheropower values (?,?,'high');";
            pstmt = con.prepareStatement(SQL4);
            for (int i = 0; i < powerIDs.size(); i++) {
                pstmt.setInt(1, heroId);
                pstmt.setInt(2, powerIDs.get(i));
                rows = pstmt.executeUpdate();
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

}
