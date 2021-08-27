package dev.patika.schoolmanagementsystem.dataaccess;


import dev.patika.schoolmanagementsystem.entities.concretes.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

    Course findByCode(String code);
}
