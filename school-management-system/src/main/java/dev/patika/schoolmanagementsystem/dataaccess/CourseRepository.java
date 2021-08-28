package dev.patika.schoolmanagementsystem.dataaccess;


import dev.patika.schoolmanagementsystem.entities.concretes.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findAllByNameContains(String name);

    Course findByCode(String code);
}
