package dev.patika.schoolmanagementsystem.dataaccess.concretes;

import dev.patika.schoolmanagementsystem.dataaccess.abstracts.BaseRepository;
import dev.patika.schoolmanagementsystem.dataaccess.abstracts.StudentRepository;
import dev.patika.schoolmanagementsystem.entities.concretes.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
class StudentRepositoryImpl extends BaseRepository<Student, Long> implements StudentRepository {

    @Autowired
    protected StudentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
