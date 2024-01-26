package com.softwaremind.recruitment.gkisiel.enums;

import com.softwaremind.recruitment.gkisiel.models.Item;
import lombok.Getter;

import java.util.Comparator;

import static java.util.Comparator.comparing;

public enum SortBy {
    DONE(comparing(Item::done)),
    NAME(comparing(Item::name)),
    ID(comparing(Item::id));

    @Getter
    private final Comparator<Item> comparator;

    SortBy(Comparator<Item> comparator) {
        this.comparator = comparator;
    }
}