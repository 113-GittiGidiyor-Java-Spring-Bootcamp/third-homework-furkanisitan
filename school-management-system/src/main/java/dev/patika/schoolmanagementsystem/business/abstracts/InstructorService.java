package dev.patika.schoolmanagementsystem.business.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;

import java.util.List;
import java.util.Optional;

public interface InstructorService {

    List<Instructor> findAll();

    /**
     * Filters those containing the name. If {@literal name} is empty, returns all instructors.
     *
     * @param name instructor name to filter.
     * @return filtered list of instructors.
     */
    List<Instructor> findAll(String name);

    Optional<Instructor> findById(Long id);

    Optional<Instructor> findByPhoneNumber(String phoneNumber);

    Instructor create(Instructor instructor);

    void update(Instructor instructor);

    void deleteById(Long id);

    /**
     * Deletes all matching instructors by {@literal name}.
     *
     * @param name instructor name to delete.
     */
    void deleteByName(String name);

    boolean existsById(Long id);
}
