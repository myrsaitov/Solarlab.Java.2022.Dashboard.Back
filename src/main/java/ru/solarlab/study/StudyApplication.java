package ru.solarlab.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Агрегирующая аннотация
public class StudyApplication {

	// Точка входа в приложение
	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	}

}
