package dev.patika.schoolmanagementsystem.business.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    /**
     * Filters those containing the name. If {@literal name} is empty, returns all courses.
     *
     * @param name course name to filter.
     * @return filtered list of courses.
     */
    List<Course> findAll(String name);

    Course findById(Long id);

    Course findByCode(String code);

    Course create(Course course);

    void update(Course course);

    void deleteById(Long id);
}
