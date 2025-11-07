package edu.espe.springlab.service.impl;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateRequest;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.StudentService;
import edu.espe.springlab.web.advice.ConflictException;
import edu.espe.springlab.web.advice.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repo;

    public  StudentServiceImpl(StudentRepository repo) {
        this.repo = repo;
    }

    @Override
    public StudentResponse create(StudentRequestData request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new ConflictException("El email ya esta registrado");
        }

        Student student = new Student();
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setBirthDate(request.getBirthDate());
        student.setActive(true);

        Student saved = repo.save(student);
        return toResponse(saved);
    }

    @Override
    public StudentResponse getById(Long id) {
        Student student = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        return toResponse(student);
    }

    @Override
    public List<StudentResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public StudentResponse deactivate(Long id) {
        Student student = repo.findById(id).orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        student.setActive(false);
        return toResponse(repo.save(student));
    }

    @Override
    public Page<StudentResponse> searchByName(String name, Pageable pageable) {
        Page<Student> page = repo.findByFullNameContainingIgnoreCase(name, pageable);
        return page.map(student -> {
            StudentResponse dto = new StudentResponse();
            dto.setId(student.getId());
            dto.setFullName(student.getFullName());
            dto.setEmail(student.getEmail());
            dto.setBirthDate(student.getBirthDate());
            dto.setActive(student.getActive());
            return dto;
        });
    }

    @Override
    public StudentResponse update(Long id, StudentUpdateRequest request) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado con ID " + id));

        // Validar email duplicado si cambia
        if (!student.getEmail().equals(request.getEmail())
                && repo.existsByEmail(request.getEmail())) {
            throw new ConflictException("El correo ya está en uso");
        }

        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setBirthDate(request.getBirthDate());

        Student updated = repo.save(student);

        StudentResponse response = new StudentResponse();
        response.setId(updated.getId());
        response.setFullName(updated.getFullName());
        response.setEmail(updated.getEmail());
        response.setBirthDate(updated.getBirthDate());
        response.setActive(updated.getActive());
        return response;
    }


    private StudentResponse toResponse(Student student){
        StudentResponse r = new StudentResponse();
        r.setId(student.getId());
        r.setFullName(student.getFullName());
        r.setEmail(student.getEmail());
        r.setBirthDate(student.getBirthDate());
        r.setActive(true);
        return r;

    }
}
