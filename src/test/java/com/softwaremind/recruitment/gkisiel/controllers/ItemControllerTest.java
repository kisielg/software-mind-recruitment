package com.softwaremind.recruitment.gkisiel.controllers;

import com.softwaremind.recruitment.gkisiel.enums.SortBy;
import com.softwaremind.recruitment.gkisiel.models.Item;
import com.softwaremind.recruitment.gkisiel.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    private static final Item RUN_DONE = new Item("Run", "Desc of run", false);
    private static final Item COOK = new Item("Cook", "Desc of cook", false);
    private static final Item LEARN_DONE = new Item("Learn", "Desc of cook", true);

    @Autowired
    private ItemController controller;
    @Autowired
    private ItemRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.save(RUN_DONE);
        repository.save(COOK);
        repository.save(LEARN_DONE);
    }

    @Test
    void getItems() throws Exception {
        var resultContent = mockMvc.perform(MockMvcRequestBuilders.get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(resultContent).contains(RUN_DONE.getName(), COOK.getDescription(), LEARN_DONE.getName());
    }

    @Test
    void getItems_sortByName() {
        var resultContent = controller.getItems(SortBy.NAME).stream().map(Item::getName).toList();
        assertThat(resultContent).containsExactly(COOK.getName(), LEARN_DONE.getName(), RUN_DONE.getName());
    }

    @Test
    void getItems_sortByDone() {
        var resultContent = controller.getItems(SortBy.DONE);
        assertThat(resultContent).hasSize(3);
        assertThat(resultContent.get(2).getName()).isEqualTo(LEARN_DONE.getName());
    }

    @Test
    void postItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .content("{\"name\":\"TestItem\",\"description\":\"TestDescription\",\"isDone\":true}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertThat(((List<Item>) repository.findAll())).hasSize(4);
    }

    @Test
    void postItem_nameIsMissed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .content("{\"description\":\"TestDescription\",\"isDone\":true}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertThat(((List<Item>) repository.findAll())).hasSize(3);
    }

    @Test
    void putItem() throws Exception {
        var cookItem = ((List<Item>) repository.findAll()).stream().filter(item -> item.getName().equals(COOK.getName())).findFirst().get();
        mockMvc.perform(MockMvcRequestBuilders.put("/items/"+cookItem.getId())
                        .content("{\"name\":\"Update\",\"description\":\"NewDescription\",\"done\":true}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(((List<Item>) repository.findAll())).hasSize(3);
        var updatedItem = repository.findById(cookItem.getId()).get();
        assertThat(updatedItem.isDone()).isTrue();
        assertThat(updatedItem.getName()).isEqualTo("Update");
    }

    @Test
    void deleteItem() throws Exception {
        var cookItem = ((List<Item>) repository.findAll()).stream().filter(item -> item.getName().equals(COOK.getName())).findFirst().get();
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/"+cookItem.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(((List<Item>) repository.findAll())).hasSize(2);
        assertThat(repository.findById(cookItem.getId())).isEmpty();
    }
}
