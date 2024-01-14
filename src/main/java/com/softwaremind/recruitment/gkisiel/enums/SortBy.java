package com.softwaremind.recruitment.gkisiel.enums;

import com.softwaremind.recruitment.gkisiel.models.Item;

import java.util.Comparator;

import static java.util.Comparator.comparing;

public enum SortBy {
    DONE(comparing(Item::isDone)),
    NAME(comparing(Item::getName)),
    ID(comparing(Item::getId));

    private final Comparator<Item> comparator;

    SortBy(Comparator<Item> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Item> getComparator() {
        return comparator;
    }
}