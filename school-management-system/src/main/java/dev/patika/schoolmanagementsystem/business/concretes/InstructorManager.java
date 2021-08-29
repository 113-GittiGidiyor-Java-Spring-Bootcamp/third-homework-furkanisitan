package dev.patika.schoolmanagementsystem.business.concretes;

import dev.patika.schoolmanagementsystem.business.abstracts.InstructorService;
import dev.patika.schoolmanagementsystem.core.exceptions.EntityNotExistsException;
import dev.patika.schoolmanagementsystem.core.exceptions.InvalidEntityTypeException;
import dev.patika.schoolmanagementsystem.core.exceptions.UniqueConstraintViolationException;
import dev.patika.schoolmanagementsystem.dataaccess.InstructorRepository;
import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        return repository.findAll();
    }

    @Override
    public List<Instructor> findAll(String name) {
        return name != null && !name.isEmpty() ? repository.findAllByNameContains(name) : findAll();
    }

    @Override
    public Optional<Instructor> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Instructor create(Instructor instructor) {

        // Check if 'phoneNumber' is unique
        validatePhoneNumberIsUnique(instructor.getPhoneNumber());

        return repository.save(instructor);
    }

    @Transactional
    @Override
    public void update(Instructor instructor) {

        Instructor existsInstructor = findById(instructor.getId()).orElse(null);

        // Check if the instructor is exists
        if (existsInstructor == null)
            throw new EntityNotExistsException("Instructor", Pair.of("id", instructor.getId()));

        // Check if existing entity type equals type of entity to update.
        if (!instructor.getClass().equals(existsInstructor.getClass()))
            throw new InvalidEntityTypeException(existsInstructor.getClass().getSimpleName());

        // Check if 'phoneNumber' is unique for update
        validatePhoneNumberIsUnique(instructor.getPhoneNumber(), instructor.getId());

        repository.save(instructor);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        Instructor instructor = findById(id)
                // Check if the instructor is exists
                .orElseThrow(() -> new EntityNotExistsException("Instructor", Pair.of("id", id)));

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

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    //region validators

    /**
     * Checks if {@literal phoneNumber} is unique.
     *
     * @param phoneNumber unique value to validate.
     * @throws UniqueConstraintViolationException if {@literal phoneNumber} is not unique.
     */
    private void validatePhoneNumberIsUnique(String phoneNumber) {

        if (repository.existsByPhoneNumber(phoneNumber))
            throw new UniqueConstraintViolationException("phoneNumber", phoneNumber);
    }

    /**
     * Checks if {@literal phoneNumber} is unique for update operation.
     *
     * @param phoneNumber unique value to validate.
     * @param id          primary key of the instructor to be updated.
     * @throws UniqueConstraintViolationException if {@literal phoneNumber} is not unique for update.
     */
    private void validatePhoneNumberIsUnique(String phoneNumber, Long id) {

        // get proxy object
        Instructor existsInstructor = repository.getByPhoneNumber(phoneNumber);

        if (existsInstructor != null && !Objects.equals(existsInstructor.getId(), id))
            throw new UniqueConstraintViolationException("phoneNumber", phoneNumber);
    }
    //endregion
}
