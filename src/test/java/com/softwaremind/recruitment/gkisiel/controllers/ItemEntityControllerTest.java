package com.softwaremind.recruitment.gkisiel.controllers;

import com.softwaremind.recruitment.gkisiel.models.ItemEntity;
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

import static com.softwaremind.recruitment.gkisiel.TestData.COOK;
import static com.softwaremind.recruitment.gkisiel.TestData.LEARN_DONE;
import static com.softwaremind.recruitment.gkisiel.TestData.RUN;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class ItemEntityControllerTest {

    @Autowired
    private ItemController controller;
    @Autowired
    private ItemRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        repository.save(RUN);
        repository.save(COOK);
        repository.save(LEARN_DONE);
    }

    @Test
    void getItems() throws Exception {
        var resultContent = mockMvc.perform(MockMvcRequestBuilders.get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(resultContent).contains(RUN.getName(), COOK.getDescription(), LEARN_DONE.getName());
    }

    @Test
    void postItem() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .content("{\"name\":\"TestItem\",\"description\":\"TestDescription\",\"done\":true}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        assertThat(((List<ItemEntity>) repository.findAll())).hasSize(4);
    }

    @Test
    void postItem_nameIsMissed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .content("{\"description\":\"TestDescription\",\"done\":true}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        assertThat(((List<ItemEntity>) repository.findAll())).hasSize(3);
    }

    @Test
    void putItem() throws Exception {
        var cookItem = ((List<ItemEntity>) repository.findAll()).stream().filter(item -> item.getName().equals(COOK.getName())).findFirst().get();
        mockMvc.perform(MockMvcRequestBuilders.put("/items/"+cookItem.getId())
                        .content("{\"name\":\"Update\",\"description\":\"NewDescription\",\"done\":true}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        assertThat(((List<ItemEntity>) repository.findAll())).hasSize(3);
        var updatedItem = repository.findById(cookItem.getId()).get();
        assertThat(updatedItem.getDone()).isTrue();
        assertThat(updatedItem.getName()).isEqualTo("Update");
    }

    @Test
    void deleteItem() throws Exception {
        var cookItem = ((List<ItemEntity>) repository.findAll()).stream().filter(item -> item.getName().equals(COOK.getName())).findFirst().get();
        mockMvc.perform(MockMvcRequestBuilders.delete("/items/"+cookItem.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertThat(((List<ItemEntity>) repository.findAll())).hasSize(2);
        assertThat(repository.findById(cookItem.getId())).isEmpty();
    }
}
