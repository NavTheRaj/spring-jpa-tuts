package com.example.springjpa.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            LocalDate dob = LocalDate.of(1997, Month.OCTOBER, 19);

            Student a = new Student(
                    "Rabindranath Tagore",
                    "rtagore@india.com",
                    dob
            );

            Student b = new Student(
                    "Osho Rajneesh",
                    "rajneesh@osho.com",
                    dob
            );

            studentRepository.saveAll(List.of(a, b));
        };
    }
}
