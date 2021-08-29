package dev.patika.schoolmanagementsystem.dataaccess;

import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findAllByName(String name);

    List<Instructor> findAllByNameContains(String name);

    Optional<Instructor> findByPhoneNumber(String phoneNumber);

    Instructor getByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}
