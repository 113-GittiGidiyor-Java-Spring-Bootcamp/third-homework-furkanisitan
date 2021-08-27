package dev.patika.schoolmanagementsystem.dataaccess.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Course;

public interface CourseRepository extends Repository<Course, Long> {

    Course findByCode(String code);
}
