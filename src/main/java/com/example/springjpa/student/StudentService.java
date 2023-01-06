package com.example.springjpa.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student addNewStudent(Student student) {
        if (!emailAlreadyExists(student))
            return studentRepository.save(student);
        return null;
    }

    public void deleteStudentById(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exists"
            );
        }
        studentRepository.deleteById(studentId);
    }

    public boolean emailAlreadyExists(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Email already Taken!");
        } else {
            return false;
        }
    }

    @Transactional
    public Student updateStudentById(Long studentId, String name, String email, LocalDate date) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id " + studentId + " does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (date != null && !date.isEqual(student.getDob()))
            student.setDob(date);

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);

            if (studentByEmail.isPresent())
                throw new IllegalStateException("Email already Taken!");

            student.setEmail(email);
        }

        return student;
    }
}
