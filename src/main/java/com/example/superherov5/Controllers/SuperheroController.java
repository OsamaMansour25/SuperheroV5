package com.example.superherov5.Controllers;

import com.example.superherov5.DTO.HeroInfoDTO;
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
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/create")
    public String addSuperHero(Model model){
        SuperheroFormDTO sm = new SuperheroFormDTO();
        model.addAttribute("sm", sm);
        return "create";
    }
    @PostMapping("/add")
    public String createSuperHero(@ModelAttribute SuperheroFormDTO sm){
        //gem i repository
        repository.addSuperhero(sm);
        return "redirect:/superhero";
    }


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
    @GetMapping("superheroes")
    public String getSuperheroes(Model model) {
        List<HeroInfoDTO> all = repository.getAll();
        model.addAttribute("all", all);
       // List<String> superpowers = repository.getSuperPowers();
      //  model.addAttribute("superPowers", superpowers);
        return "all_superheroes";

    }
    @GetMapping("superpower/{name}")
    public String getPowers (@PathVariable String name, Model model){
        SuperPowerDTO superpower = repository.getSuperheroPower(name);
        model.addAttribute("name", superpower.getHeroName());
        model.addAttribute("powers", superpower.getPowerlist());
        return "powers";
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
    @GetMapping("count/{name}")
    public ResponseEntity<List<SuperPowerDTO>> countSuperheroPower(@PathVariable String name) {
        List<SuperPowerDTO> superPowerDT = repository.countSuperheroPower(name);
        return new ResponseEntity<>(superPowerDT, HttpStatus.OK);
    }
}


