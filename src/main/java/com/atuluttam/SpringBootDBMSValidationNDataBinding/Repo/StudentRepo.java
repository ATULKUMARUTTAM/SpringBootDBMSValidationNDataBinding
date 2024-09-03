package com.atuluttam.SpringBootDBMSValidationNDataBinding.Repo;

import com.atuluttam.SpringBootDBMSValidationNDataBinding.Model.Student;
import com.atuluttam.SpringBootDBMSValidationNDataBinding.StudentServ.NoSuchStudentExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for mapping rows of the result set to Student objects
    private static final RowMapper<Student> STUDENT_ROW_MAPPER = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setRoll(rs.getInt("roll"));
            student.setName(rs.getString("name"));
            student.setDob(rs.getDate("dob").toLocalDate());
            student.setMobileno(rs.getInt("mobileno"));
            return student;
        }
    };

    // Save a student
    public int save(Student student) {
        String sql = "INSERT INTO student (roll, name, dob, mobileno) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, student.getRoll(), student.getName(), student.getDob(), student.getMobileno());
    }

    // Delete a student by roll number
    public int deleteByRoll(int roll) {
        String sql = "DELETE FROM student WHERE roll = ?";
        return jdbcTemplate.update(sql, roll);
    }

    // Update a student's details
    public int updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, dob = ?, mobileno = ? WHERE roll = ?";
        return jdbcTemplate.update(sql, student.getName(), student.getDob(), student.getMobileno(), student.getRoll());
    }

    // Find a student by roll number
    public Student findByRoll(int roll) throws NoSuchStudentExist{
        String sql = "SELECT * FROM student WHERE roll = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{roll}, STUDENT_ROW_MAPPER);
        }
        catch (Exception e)
        {
            throw new NoSuchStudentExist("No Such Student Exist");
        }
    }

    // Display all students
    public List<Student> findAll() {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, STUDENT_ROW_MAPPER);
    }
}
