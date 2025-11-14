package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldSaveAndFindStudentByEmail() {
        Student s = new Student();
        s.setFullName("Test User");
        s.setEmail("test@example.com");
        s.setBirthDate(LocalDate.of(2000, 10, 10));
        s.setActive(true);

        repository.save(s);

        var result = repository.findByEmail("test@example.com");

        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Test User");
    }

    // Kenneth Cortez
    @Test
    void shouldFindStudentsByPartialName() {
        // Ana
        Student s1 = new Student();
        s1.setFullName("Ana");
        s1.setEmail("ana@example.com");
        s1.setBirthDate(LocalDate.of(1995, 8, 20));
        s1.setActive(true);
        repository.save(s1);

        // Andrea
        Student s2 = new Student();
        s2.setFullName("Andrea");
        s2.setEmail("andrea@example.com");
        s2.setBirthDate(LocalDate.of(1996, 3, 15));
        s2.setActive(true);
        repository.save(s2);

        // Juan
        Student s3 = new Student();
        s3.setFullName("Juan");
        s3.setEmail("juan@example.com");
        s3.setBirthDate(LocalDate.of(1997, 6, 10));
        s3.setActive(true);
        repository.save(s3);

        var results = repository.findByFullNameContainingIgnoreCase("an", Pageable.ofSize(2));


        assertThat(results.stream().anyMatch(s -> s.getFullName().equals("Ana"))).isTrue();
        assertThat(results.stream().anyMatch(s -> s.getFullName().equals("Andrea"))).isTrue();
        assertThat(results.stream().noneMatch(s -> s.getFullName().equals("Juan"))).isTrue();
    }
}
