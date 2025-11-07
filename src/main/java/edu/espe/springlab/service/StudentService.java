package edu.espe.springlab.service;

import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;

import edu.espe.springlab.dto.StudentUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface StudentService {

    //Crear un estudiante a partir del DTO valiado
    StudentResponse create(StudentRequestData request);

    //Busqueda por ID
    StudentResponse getById(Long id);

    //Listar todos los estudiantes
    List<StudentResponse> list();

    //Cambiar estado del estudiante
    StudentResponse deactivate(Long id);

    //Buscar estudiante por nombre
    Page<StudentResponse> searchByName(String name, Pageable pageable);

    //Actualizar un estudiante
    StudentResponse update(Long id, StudentUpdateRequest request);

}
