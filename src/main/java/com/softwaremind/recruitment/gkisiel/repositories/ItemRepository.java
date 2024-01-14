package com.softwaremind.recruitment.gkisiel.repositories;

import com.softwaremind.recruitment.gkisiel.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {}
