package dev.patika.schoolmanagementsystem.business.concretes;

import dev.patika.schoolmanagementsystem.business.abstracts.InstructorService;
import dev.patika.schoolmanagementsystem.core.exceptions.EntityNotExistsException;
import dev.patika.schoolmanagementsystem.core.exceptions.InvalidEntityTypeException;
import dev.patika.schoolmanagementsystem.core.exceptions.UniqueConstraintViolationException;
import dev.patika.schoolmanagementsystem.core.helpers.Lists;
import dev.patika.schoolmanagementsystem.dataaccess.InstructorRepository;
import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class InstructorManager implements InstructorService {

    private final InstructorRepository repository;

    @Autowired
    public InstructorManager(InstructorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Instructor> findAll() {
        return Lists.from(repository.findAll());
    }

    @Override
    public List<Instructor> findAll(String name) {
        return name != null && !name.isEmpty() ? Lists.from(repository.findAllByNameContains(name)) : findAll();
    }

    @Override
    public Instructor findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Instructor findByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber);
    }

    @Transactional
    @Override
    public Instructor create(Instructor instructor) {
        validatePhoneNumberIsUnique(instructor.getPhoneNumber());
        return repository.save(instructor);
    }

    @Transactional
    @Override
    public void update(Instructor instructor) {

        Instructor existsInstructor = findById(instructor.getId());

        // Check if the Instructor is exists
        if (existsInstructor == null)
            throw new EntityNotExistsException("Instructor", Pair.of("id", instructor.getId()));

        // check if existing entity type equals type of entity to update.
        if (!instructor.getClass().equals(existsInstructor.getClass()))
            throw new InvalidEntityTypeException(existsInstructor.getClass().getSimpleName());

        validatePhoneNumberIsUniqueForUpdate(instructor.getPhoneNumber(), instructor.getId());

        repository.save(instructor);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        // Check if the Instructor is exists
        Instructor instructor = findById(id);
        if (instructor == null)
            throw new EntityNotExistsException("Instructor", Pair.of("id", id));

        instructor.clearCourses();
        repository.delete(instructor);
    }

    @Transactional
    @Override
    public void deleteByName(String name) {

        List<Instructor> instructors = repository.findAllByName(name);

        for (Instructor instructor : instructors) {
            instructor.clearCourses();
            repository.delete(instructor);
        }
    }

    //region validators

    /**
     * Checks if {@literal phoneNumber} is unique.
     *
     * @param phoneNumber unique value to validate.
     * @throws UniqueConstraintViolationException if {@literal phoneNumber} is not unique.
     */
    private void validatePhoneNumberIsUnique(String phoneNumber) {
        Instructor existsInstructor = findByPhoneNumber(phoneNumber);
        if (existsInstructor != null)
            throw new UniqueConstraintViolationException("phoneNumber", phoneNumber);
    }

    /**
     * Checks if {@literal phoneNumber} is unique for update_ operation.
     *
     * @param phoneNumber  unique value to validate.
     * @param instructorId the id of the instructor to be updated.
     * @throws UniqueConstraintViolationException if {@literal phoneNumber} is not unique.
     */
    private void validatePhoneNumberIsUniqueForUpdate(String phoneNumber, Long instructorId) {
        Instructor existsInstructor = findByPhoneNumber(phoneNumber);
        if (existsInstructor != null && !Objects.equals(existsInstructor.getId(), instructorId))
            throw new UniqueConstraintViolationException("phoneNumber", phoneNumber);
    }
    //endregion
}
