package com.miage.altea.trainer_api.bo;

import org.junit.Test;

import javax.persistence.Embeddable;

import static junit.framework.TestCase.assertNotNull;

class PokemonTest {

    @Test
    void pokemon_shouldBeAnEmbeddable(){
        assertNotNull(Pokemon.class.getAnnotation(Embeddable.class));
    }

}
