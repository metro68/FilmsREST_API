package com.example.Films;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import Films.FilmApplication;
import Films.FilmController;

@SpringBootTest(classes = FilmApplication.class)
public class FilmsApplicationTests {

	@Autowired
	private FilmController filmController;

	@Test
	public void contextLoads() {
		assertThat(filmController).isNotNull();
	}

}
