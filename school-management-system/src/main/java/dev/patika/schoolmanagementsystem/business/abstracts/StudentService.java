package dev.patika.schoolmanagementsystem.business.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> findAll();

    List<Student> findAllByNameContains(String name);

    Optional<Student> findById(Long id);

    Student create(Student student);

    void update(Student student);

    void deleteById(Long id);

    /**
     * Deletes all matching students by {@literal name}.
     *
     * @param name student name to delete.
     */
    void deleteByName(String name);
}
