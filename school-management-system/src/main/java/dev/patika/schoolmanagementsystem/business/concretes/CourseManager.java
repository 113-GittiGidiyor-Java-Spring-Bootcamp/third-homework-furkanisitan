package dev.patika.schoolmanagementsystem.business.concretes;

import dev.patika.schoolmanagementsystem.business.abstracts.CourseService;
import dev.patika.schoolmanagementsystem.business.abstracts.InstructorService;
import dev.patika.schoolmanagementsystem.core.exceptions.EntityNotExistsException;
import dev.patika.schoolmanagementsystem.core.exceptions.ForeignKeyConstraintViolationException;
import dev.patika.schoolmanagementsystem.core.exceptions.UniqueConstraintViolationException;
import dev.patika.schoolmanagementsystem.core.helpers.Lists;
import dev.patika.schoolmanagementsystem.dataaccess.CourseRepository;
import dev.patika.schoolmanagementsystem.entities.concretes.Course;
import dev.patika.schoolmanagementsystem.entities.concretes.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class CourseManager implements CourseService {

    private final CourseRepository repository;
    private final InstructorService instructorService;

    @Autowired
    public CourseManager(CourseRepository repository, InstructorService instructorService) {
        this.repository = repository;
        this.instructorService = instructorService;
    }

    @Override
    public List<Course> findAll() {
        return Lists.from(repository.findAll());
    }

    @Override
    public List<Course> findAll(String name) {
        return name != null && !name.isEmpty() ? Lists.from(repository.findAllByNameContains(name)) : findAll();
    }

    @Override
    public Course findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Course findByCode(String code) {
        return repository.findByCode(code);
    }

    @Transactional
    @Override
    public Course create(Course course) {

        // check if 'code' is unique
        validateCodeIsUnique(course.getCode());

        // Check if there is an instructor with the foreign key 'instructorId'
        validateInstructorIsExists(course.getInstructor().getId());

        return repository.save(course);
    }

    @Transactional
    @Override
    public void update(Course course) {

        // Check if the course is exists
        if (findById(course.getId()) == null)
            throw new EntityNotExistsException("Course", Pair.of("id", course.getId()));

        // check if 'code' is unique
        validateCodeIsUniqueForUpdate(course.getCode(), course.getId());

        // Check if there is an instructor with the foreign key 'instructorId'
        validateInstructorIsExists(course.getInstructor().getId());

        repository.save(course);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        Course course = findById(id);
        if (course == null)
            throw new EntityNotExistsException("Course", Pair.of("id", id));

        course.clearStudents();
        repository.delete(course);
    }

    @Transactional
    @Override
    public void deleteByName(String name) {

        List<Course> courses = repository.findAllByName(name);

        for (Course course : courses) {
            course.clearStudents();
            repository.delete(course);
        }
    }

    //region validators

    /**
     * Checks if {@literal code} is unique.
     *
     * @param code unique value to validate.
     * @throws UniqueConstraintViolationException if {@literal code} is not unique.
     */
    private void validateCodeIsUnique(String code) {
        Course existsCourse = findByCode(code);
        if (existsCourse != null)
            throw new UniqueConstraintViolationException("code", code);
    }

    /**
     * Checks if {@literal code} is unique for update operation.
     *
     * @param code     unique value to validate.
     * @param courseId the id of the course to be updated.
     * @throws UniqueConstraintViolationException if {@literal code} is not unique.
     */
    private void validateCodeIsUniqueForUpdate(String code, Long courseId) {
        Course existsCourse = findByCode(code);
        if (existsCourse != null && !Objects.equals(existsCourse.getId(), courseId))
            throw new UniqueConstraintViolationException("code", code);
    }

    /**
     * Checks if there is an instructor with the foreign key @{literal instructorId}.
     *
     * @param instructorId foreign key
     * @throws ForeignKeyConstraintViolationException if @{literal instructorId} is not exists.
     */
    private void validateInstructorIsExists(Long instructorId) {
        Instructor existsInstructor = instructorService.findById(instructorId);
        if (existsInstructor == null)
            throw new ForeignKeyConstraintViolationException("instructorId", instructorId);
    }
    //endregion
}
