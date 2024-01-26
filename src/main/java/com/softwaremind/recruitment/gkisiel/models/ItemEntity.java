package com.softwaremind.recruitment.gkisiel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;
    private String description;
    private Boolean done;


    public ItemEntity(String name, String description, boolean done) {
        this.name = name;
        this.description = description;
        this.done = done;
    }


    public static ItemEntity from(Item item) {
        return new ItemEntity(item.name(), item.description(), item.done());
    }

    public ItemEntity merge(ItemEntity itemEntity) {
        setDescription(itemEntity.getDescription());
        setDone(itemEntity.getDone());
        setName(itemEntity.getName());
        return this;
    }
}
