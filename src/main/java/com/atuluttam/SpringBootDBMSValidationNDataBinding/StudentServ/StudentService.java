package com.atuluttam.SpringBootDBMSValidationNDataBinding.StudentServ;

import com.atuluttam.SpringBootDBMSValidationNDataBinding.Model.Student;
import com.atuluttam.SpringBootDBMSValidationNDataBinding.Repo.StudentRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private Validator validator;

    public void saveStudent(Student student) {
        validateStudent(student);
        studentRepo.save(student);
    }

    public void deleteStudentByRoll(int roll) throws NoSuchStudentExist {
        if (studentRepo.deleteByRoll(roll)==0) {
            throw new NoSuchStudentExist("Student not exist with this roll");
        }



    }

    public void updateStudent(Student student) throws NoSuchStudentExist {
        validateStudent(student);
       if( studentRepo.updateStudent(student)==0)
           throw new NoSuchStudentExist("Student does not Exist");
    }

    public Student findStudentByRoll(int roll) throws Exception {
        /*if (studentRepo.findByRoll(roll)) {
            System.out.println("No such student exist");
            throw new NoSuchStudentExist("Student with this roll Does Not Exist");

        }*/
       // System.out.println("studentRepo.findByRoll(roll)"+studentRepo.findByRoll(roll));
        return studentRepo.findByRoll(roll);
    }

    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    private void validateStudent(Student student) {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
