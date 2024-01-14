package com.softwaremind.recruitment.gkisiel.controllers;

import com.softwaremind.recruitment.gkisiel.enums.SortBy;
import com.softwaremind.recruitment.gkisiel.models.Item;
import com.softwaremind.recruitment.gkisiel.repositories.ItemRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {
    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/items", produces="application/json")
    public List<Item> getItems(@RequestParam(defaultValue = "id") SortBy sortBy) {
        var items = (List<Item>) repository.findAll();

        return items.stream()
                .sorted(sortBy.getComparator())
                .toList();
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void postItem(@Valid @RequestBody Item item) {
        repository.save(item);
    }

    @PutMapping("/items/{id}")
    @Transactional
    public void putItem(@RequestBody Item item, @PathVariable Long id) {
        var maybeItem = repository.findById(id);
        maybeItem.ifPresentOrElse(i -> i.merge(item), () ->repository.save(item));
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
