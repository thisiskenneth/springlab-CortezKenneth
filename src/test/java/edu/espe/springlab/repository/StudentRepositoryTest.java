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

    // Spring inyecta la instancia del repositorio que vamos a probar.
    @Autowired
    private StudentRepository repository;

    /**
     * Prueba para verificar que se puede guardar un estudiante y luego encontrarlo
     * usando su correo electrónico (email).
     */
    @Test
    void shouldSaveAndFindStudentByEmail() {
        // --- 1. ARRANGE (PREPARACIÓN) ---
        // Crea una nueva instancia de la entidad Student.
        Student s = new Student();
        // Establece los datos necesarios para la prueba.
        s.setFullName("Test User");
        s.setEmail("test@example.com"); // Email que usaremos para la búsqueda.
        s.setBirthDate(LocalDate.of(2000, 10, 10));
        s.setActive(true);

        // --- 2. ACT (EJECUCIÓN) ---
        // Llama al metodo 'save' del repositorio para persistir la entidad.
        repository.save(s);

        // Llama al metodo personalizado 'findByEmail' para recuperar el estudiante.
        // Se espera que este metodo devuelva un Optional<Student>.
        var result = repository.findByEmail("test@example.com");

        // --- 3. ASSERT (VERIFICACIÓN) ---
        // Aserción 1: Verifica que el Optional contenga un valor, es decir, que el estudiante fue encontrado.
        assertThat(result).isPresent();

        // Aserción 2: Verifica que el nombre del estudiante encontrado sea el correcto.
        // Se usa .get() solo después de haber verificado que .isPresent() es true.
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

        //Kenneth Cortez
        var results = repository.findByFullNameContainingIgnoreCase("an", Pageable.ofSize(2));


        assertThat(results.stream().anyMatch(s -> s.getFullName().equals("Ana"))).isTrue();
        assertThat(results.stream().anyMatch(s -> s.getFullName().equals("Andrea"))).isTrue();
        assertThat(results.stream().noneMatch(s -> s.getFullName().equals("Juan"))).isTrue();
    }
}
