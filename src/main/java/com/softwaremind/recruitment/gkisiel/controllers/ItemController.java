package com.softwaremind.recruitment.gkisiel.controllers;

import com.softwaremind.recruitment.gkisiel.enums.SortBy;
import com.softwaremind.recruitment.gkisiel.models.Item;
import com.softwaremind.recruitment.gkisiel.service.ItemService;
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
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/items", produces = "application/json")
    public List<Item> getItems(@RequestParam(defaultValue = "id") SortBy sortBy) {
        return itemService.getSorted(sortBy);
    }

    @GetMapping(value = "/items/{id}", produces = "application/json")
    public Item getItem(@PathVariable Long id) {
        return itemService.getById(id);
    }

    @GetMapping(value = "/items/search", produces = "application/json")
    public List<Item> search(@RequestParam(defaultValue = "searchTerm") String searchTerm) {
        return itemService.search(searchTerm);
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public void postItem(@Valid @RequestBody Item item) {
        itemService.save(item);
    }

    @PutMapping("/items/{id}")
    public void putItem(@RequestBody Item item, @PathVariable Long id) {
        itemService.update(item, id);
    }

    @DeleteMapping("/items/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteById(id);
    }

}
