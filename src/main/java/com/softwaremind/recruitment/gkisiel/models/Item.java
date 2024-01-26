package com.softwaremind.recruitment.gkisiel.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record Item(@JsonProperty Long id, @JsonProperty @NotBlank String name, @JsonProperty String description, @JsonProperty boolean done) {
    public static Item fromEntity(ItemEntity entity) {
        return new Item(entity.getId(), entity.getName(), entity.getDescription(), entity.getDone());
    }
}
