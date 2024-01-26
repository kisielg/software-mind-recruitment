package com.softwaremind.recruitment.gkisiel.service;

import com.softwaremind.recruitment.gkisiel.models.Item;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidatorServiceTest {

    private static final ValidatorService SERVICE = new ValidatorService();

    @ParameterizedTest
    @ValueSource(strings = {"kiss my ass", "fuck it"})
    void testValidation(String itemDescription){
        var item = new Item(10L, "name", itemDescription, false);
        var ex = assertThrows(IllegalArgumentException.class, () -> SERVICE.valid(item));
        assertThat(ex.getMessage()).isEqualTo("Description can not contain : [ass, fuck]");
    }
}