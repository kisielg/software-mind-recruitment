package com.softwaremind.recruitment.gkisiel.service;

import com.softwaremind.recruitment.gkisiel.models.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidatorService {

    private static final List<String> INVALID_WORDS = List.of("ass", "fuck");

    void valid(Item item) {
        //bardzo prosta walidacja
        if (INVALID_WORDS.stream().anyMatch(word -> item.description().contains(word))) {
            throw new IllegalArgumentException("Description can not contain : " + INVALID_WORDS);
        }
    }
}
