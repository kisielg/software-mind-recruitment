package com.softwaremind.recruitment.gkisiel.service;

import com.softwaremind.recruitment.gkisiel.enums.SortBy;
import com.softwaremind.recruitment.gkisiel.models.Item;
import com.softwaremind.recruitment.gkisiel.models.ItemEntity;
import com.softwaremind.recruitment.gkisiel.repositories.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class ItemService {


    private final ItemRepository repository;
    private final ValidatorService validatorService;

    public ItemService(ItemRepository repository, ValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    public List<Item> getSorted(SortBy sortBy) {

        var items = (List<ItemEntity>) repository.findAll();

        return items.stream()
                .map(Item::fromEntity)
                .sorted(sortBy.getComparator())
                .toList();
    }

    public void save(Item item) {
        validatorService.valid(item);
        repository.save(ItemEntity.from(item));
    }


    @Transactional
    public void update(Item item, Long id) {
        repository.save(repository.findById(id)
                .orElseThrow(exceptionSupplier(id))
                .merge(ItemEntity.from(item)));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Item getById(Long id) {
        return Item.fromEntity(repository.findById(id).orElseThrow(exceptionSupplier(id)));
    }

    public List<Item> search(String searchTerm) {
        return repository.findByNameContainingIgnoreCase(searchTerm);
    }

    Supplier<IllegalArgumentException> exceptionSupplier(Long id) {
        return () -> new IllegalArgumentException("Item id was not found " + id);
    }
}
