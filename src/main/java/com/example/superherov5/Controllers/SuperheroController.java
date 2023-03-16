package com.example.superherov5.Controllers;

import com.example.superherov5.DTO.SuperPowerDTO;
import com.example.superherov5.DTO.SuperheroFormDTO;
import com.example.superherov5.Model.SuperheroModel;
import com.example.superherov5.SuperheroInterface.ISuperHeroRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@RequestMapping(path="/superhero")
@Controller
public class SuperheroController {
    //  private SuperheroRepository repository;
    ISuperHeroRepository repository;
    public SuperheroController(ApplicationContext context, @Value("${superhero.respository.impl}") String impl) {
        repository = (ISuperHeroRepository) context.getBean(impl);
    }

    /*    public SuperheroController(SuperheroRepository repository) {
           // this.repository = repository;
            this.repository = repository;
        }*/
    @GetMapping("/register")
    public String showForm(Model model) {
        SuperheroFormDTO sh = new SuperheroFormDTO();
        model.addAttribute("sh", sh);

        List<String> Cities = repository.getCities();
        model.addAttribute("Cities", Cities);

        List<String> superPowers = repository.getSuperPowers();
        model.addAttribute("superPowers", superPowers);

        return "register_form";
    }

    @GetMapping("/superherolist")
    public ResponseEntity<List<SuperheroModel>> getAll() {
        System.out.println("hej");
        List<SuperheroModel> superheroList = repository.getAll();
        return new ResponseEntity<>(superheroList, HttpStatus.OK);
    }
    @GetMapping("/{name}")
    public ResponseEntity<List<SuperheroModel>> getSuperhero(@PathVariable String name) {
        List<SuperheroModel> superheroList = repository.getSuperhero(name);
        return new ResponseEntity<>(superheroList, HttpStatus.OK);
    }
    @GetMapping("/city/{name}")
    public ResponseEntity<List<SuperheroModel>> getSuperheroesFromCity(@PathVariable String name) {
        List<SuperheroModel> superhero = repository.getSuperheroesFromCity(name);
        return new ResponseEntity<>(superhero, HttpStatus.OK);
    }
    @GetMapping("/superpower/{name}")
    public ResponseEntity<List<SuperheroModel>> getSuperheroPower(@PathVariable String name) {
        List<SuperheroModel> superhero = repository.getSuperheroPower(name);
        return new ResponseEntity<>(superhero, HttpStatus.OK);
    }
    @GetMapping("count/{name}")
    public ResponseEntity<List<SuperPowerDTO>> countSuperheroPower(@PathVariable String name) {
        List<SuperPowerDTO> superPowerDT = repository.countSuperheroPower(name);
        return new ResponseEntity<>(superPowerDT, HttpStatus.OK);
    }
}


