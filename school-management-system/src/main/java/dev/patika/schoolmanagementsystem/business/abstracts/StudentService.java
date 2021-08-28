package dev.patika.schoolmanagementsystem.business.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    /**
     * Filters those containing the name. If {@literal name} is empty, returns all students.
     *
     * @param name student name to filter.
     * @return filtered list of students.
     */
    List<Student> findAll(String name);

    Student findById(Long id);

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
