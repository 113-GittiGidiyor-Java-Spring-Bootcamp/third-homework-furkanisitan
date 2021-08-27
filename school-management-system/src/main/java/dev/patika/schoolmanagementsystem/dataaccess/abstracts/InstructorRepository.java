package dev.patika.schoolmanagementsystem.dataaccess.abstracts;

import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;

public interface InstructorRepository extends Repository<Instructor, Long> {

    Instructor findByPhoneNumber(String phoneNumber);
}
