package com.softwaremind.recruitment.gkisiel;

import com.softwaremind.recruitment.gkisiel.models.ItemEntity;
import com.softwaremind.recruitment.gkisiel.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class GkisielApplication {

	private static final Random RANDOM = new Random();

	public static void main(String[] args) {
		SpringApplication.run(GkisielApplication.class, args);
	}

	//To have a few TODO items when application starts
	@Bean
	CommandLineRunner init(ItemRepository itemRepository) {
		return args ->
			Stream.of("Learn", "Run", "Sing", "Jump").forEach(task -> {
				ItemEntity itemEntity = new ItemEntity(task, "This is a description of " + task, RANDOM.nextBoolean());
				itemRepository.save(itemEntity);
			});
	}

}
