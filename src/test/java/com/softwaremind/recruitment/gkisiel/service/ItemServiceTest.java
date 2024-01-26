package com.softwaremind.recruitment.gkisiel.service;

import com.softwaremind.recruitment.gkisiel.enums.SortBy;
import com.softwaremind.recruitment.gkisiel.models.Item;
import com.softwaremind.recruitment.gkisiel.models.ItemEntity;
import com.softwaremind.recruitment.gkisiel.repositories.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.softwaremind.recruitment.gkisiel.TestData.COOK;
import static com.softwaremind.recruitment.gkisiel.TestData.LEARN_DONE;
import static com.softwaremind.recruitment.gkisiel.TestData.RUN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository repository;

    @Mock
    private ValidatorService validatorService;

    @InjectMocks
    private ItemService service;

    @Test
    void getItems_sortByName() {
        when(repository.findAll()).thenReturn(List.of(LEARN_DONE, COOK, RUN));

        var result = service.getSorted(SortBy.NAME).stream().map(Item::name).toList();

        assertFalse(result.isEmpty());
        assertThat(result).containsExactly(COOK.getName(), LEARN_DONE.getName(), RUN.getName());
    }

    @Test
    void getItems_sortByDone() {
        when(repository.findAll()).thenReturn(List.of(LEARN_DONE, COOK, RUN));

        var result = service.getSorted(SortBy.DONE);

        assertFalse(result.isEmpty());
        assertThat(result.get(2).name()).isEqualTo(LEARN_DONE.getName());
    }

    @Test
    void save() {
        Item item = new Item(10L, "TestItem", "TestDescription", false);

        service.save(item);

        verify(validatorService, times(1)).valid(item);
        verify(repository, times(1)).save(any(ItemEntity.class));
    }

    @Test
    void update() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(new ItemEntity()));

        Item item = new Item(10L, "Test", "TestDescription", false);

        assertDoesNotThrow(() -> service.update(item, id));

        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(ItemEntity.class));
    }

    @Test
    void deleteById() {
        var id = 1L;

        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }

    @Test
    void getById() {
        var id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(RUN));

        var result = service.getById(id);

        assertNotNull(result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void search() {
        var searchTerm = "Test";

        service.search(searchTerm);

        verify(repository, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }
}