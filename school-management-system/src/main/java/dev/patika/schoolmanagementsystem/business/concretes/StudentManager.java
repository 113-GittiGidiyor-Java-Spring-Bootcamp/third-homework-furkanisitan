package dev.patika.schoolmanagementsystem.business.concretes;

import dev.patika.schoolmanagementsystem.business.abstracts.StudentService;
import dev.patika.schoolmanagementsystem.core.helpers.Lists;
import dev.patika.schoolmanagementsystem.dataaccess.StudentRepository;
import dev.patika.schoolmanagementsystem.entities.concretes.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class StudentManager implements StudentService {

    private final StudentRepository repository;

    @Autowired
    public StudentManager(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> findAll() {
        return Lists.from(repository.findAll());
    }

    @Override
    public List<Student> findAll(String name) {
        return name != null && !name.isEmpty() ? Lists.from(repository.findAllByNameContains(name)) : findAll();
    }

    @Override
    public Student findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Student create(Student student) {
        return repository.save(student);
    }

    @Transactional
    @Override
    public void update(Student student) {
        repository.save(student);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        repository.deleteAllByName(name);
    }
}
