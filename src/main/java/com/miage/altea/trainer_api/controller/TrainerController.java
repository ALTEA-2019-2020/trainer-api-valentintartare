package com.miage.altea.trainer_api.controller;

import com.miage.altea.trainer_api.bo.Trainer;
import com.miage.altea.trainer_api.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private TrainerService trainerService;

    @Autowired
    TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/")
    Iterable<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    @GetMapping("/{name}")
    Trainer getTrainer(@PathVariable String name) {
        return trainerService.getTrainer(name);
    }

    @PostMapping
    Trainer postTrainer(@RequestBody Trainer trainer){
        return trainerService.saveTrainer(trainer);
    }

    @PutMapping()
    Trainer updateTrainer(@RequestBody Trainer trainer){
        return trainerService.saveTrainer(trainer);
    }

    @DeleteMapping("/{name}")
    ResponseEntity deleteTrainer(@PathVariable String name){
        trainerService.deleteTrainer(name);
        return ResponseEntity.status(200).body(null);
    }

}
