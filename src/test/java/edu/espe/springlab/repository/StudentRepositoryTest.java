package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldSaveAndFindStudentByEmail() {
        // Crear el estudiante correctamente
        Student s = new Student();
        s.setFullName("Test User");
        s.setEmail("test@example.com");
        s.setBirthDate(LocalDate.of(2000, 10, 10));
        s.setActive(true);

        // Guardar en la base de datos en memoria (H2)
        repository.save(s);

        // Buscar por email
        var result = repository.findByEmail("test@example.com");

        // Verificar que el resultado existe y coincide
        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Test User");

    }
}