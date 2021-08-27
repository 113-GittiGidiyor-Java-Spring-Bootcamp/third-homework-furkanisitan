package dev.patika.schoolmanagementsystem.business.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;

import java.util.List;

public interface InstructorService {

    List<Instructor> findAll();

    Instructor findById(Long id);

    Instructor findByPhoneNumber(String phoneNumber);

    Instructor create(Instructor instructor);

    Instructor update(Instructor instructor);

    void deleteById(Long id);
}
