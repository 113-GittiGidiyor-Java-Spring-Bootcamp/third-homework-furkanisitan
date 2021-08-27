package dev.patika.schoolmanagementsystem.dataaccess.concretes;

import dev.patika.schoolmanagementsystem.dataaccess.abstracts.BaseRepository;
import dev.patika.schoolmanagementsystem.dataaccess.abstracts.InstructorRepository;
import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
public class InstructorRepositoryImpl extends BaseRepository<Instructor, Long> implements InstructorRepository {

    private final EntityManager entityManager;

    @Autowired
    protected InstructorRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Instructor findByPhoneNumber(String phoneNumber) {
        try {
            return entityManager.createQuery("select i from Instructor i where i.phoneNumber=:phoneNumber", Instructor.class)
                    .setParameter("phoneNumber", phoneNumber).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
