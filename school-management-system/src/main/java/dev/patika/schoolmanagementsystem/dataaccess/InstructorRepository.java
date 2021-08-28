package dev.patika.schoolmanagementsystem.dataaccess;

import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends CrudRepository<Instructor, Long> {

    List<Instructor> findAllByName(String name);

    List<Instructor> findAllByNameContains(String name);

    Instructor findByPhoneNumber(String phoneNumber);
}
