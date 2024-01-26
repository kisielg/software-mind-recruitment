package com.softwaremind.recruitment.gkisiel.repositories;

import com.softwaremind.recruitment.gkisiel.models.Item;
import com.softwaremind.recruitment.gkisiel.models.ItemEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    List<Item> findByNameContainingIgnoreCase(String searchTerm);
}
