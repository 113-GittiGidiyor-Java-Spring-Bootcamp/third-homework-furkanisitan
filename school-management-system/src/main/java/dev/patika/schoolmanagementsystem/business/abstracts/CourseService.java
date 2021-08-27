package dev.patika.schoolmanagementsystem.business.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course findById(Long id);

    Course findByCode(String code);

    Course create(Course course);

    Course update(Course course);

    void deleteById(Long id);
}
