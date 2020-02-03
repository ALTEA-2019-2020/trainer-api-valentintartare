package com.miage.altea.trainer_api.controller;

import com.miage.altea.trainer_api.bo.Pokemon;
import com.miage.altea.trainer_api.bo.Trainer;
import com.miage.altea.trainer_api.repository.TrainerRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class TrainerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TrainerController controller;

    @Autowired
    private TrainerRepository repository;

    @BeforeEach
    void deleteAllBeforeEachTest() {
        repository.deleteAll();
        var ash = new Trainer("Ash");
        var pikachu = new Pokemon(25, 18);
        ash.setTeam(List.of(pikachu));

        var misty = new Trainer("Misty");
        var staryu = new Pokemon(120, 18);
        var starmie = new Pokemon(121, 21);
        misty.setTeam(List.of(staryu, starmie));

        repository.save(ash);
        repository.save(misty);

    }

    @Test
    void trainerController_shouldBeInstanciated() {
        assertNotNull(controller);
    }

    @Test
    void getTrainer_withNameAsh_shouldReturnAsh() {
        var ash = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(25, ash.getTeam().get(0).getPokemonTypeId());
        assertEquals(18, ash.getTeam().get(0).getLevel());
    }

    @Test
    void getAllTrainers_shouldReturnAshAndMisty() {
        var trainers = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        assertEquals("Ash", trainers[0].getName());
        assertEquals("Misty", trainers[1].getName());
    }

    @Test
    void getAllTrainers_shouldInsertBugCatchers() {
        Trainer trainer = new Trainer("Bug Catchers");
        trainer.setTeam(Arrays.asList(new Pokemon(33, 58)));
        var trainers = this.restTemplate.postForEntity("http://localhost:" + port + "/trainers/", trainer, Trainer.class);
        assertNotNull(trainers);

        assertEquals("Bug Catchers", trainer.getName());
    }

    @Test
    void getAllTrainers_shouldUpdateBugCatchers() {
        Trainer trainer = new Trainer("Bug Catchers");
        trainer.setTeam(Arrays.asList(new Pokemon(33, 58)));
        this.restTemplate.postForEntity("http://localhost:" + port + "/trainers/", trainer, Trainer.class);
        trainer.setTeam(Arrays.asList(new Pokemon(33, 78)));
        this.restTemplate.put("http://localhost:" + port + "/trainers/", trainer, Trainer.class);
        var bugCatchers = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Bug Catchers", Trainer.class);
        assertNotNull(bugCatchers);

        assertEquals(78, bugCatchers.getTeam().get(0).getLevel());
    }

    @Test
    void getAllTrainers_shouldDeleteBugCatchers() {
        Trainer trainer = new Trainer("Bug Catchers");
        trainer.setTeam(Arrays.asList(new Pokemon(33, 58)));
        this.restTemplate.postForEntity("http://localhost:" + port + "/trainers/", trainer, Trainer.class);
        var bugCatchers = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Bug Catchers", Trainer.class);
        this.restTemplate.delete("http://localhost:" + port + "/trainers/Bug Catchers");
        var bugCatchersDeleted = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Bug Catchers", Trainer.class);

        assertEquals("Bug Catchers", bugCatchers.getName());
        assertNull(bugCatchersDeleted.getName());
    }
}
