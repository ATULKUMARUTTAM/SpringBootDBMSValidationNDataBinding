package com.atuluttam.SpringBootDBMSValidationNDataBinding;

import com.atuluttam.SpringBootDBMSValidationNDataBinding.Model.Student;
import com.atuluttam.SpringBootDBMSValidationNDataBinding.StudentServ.NoSuchStudentExist;
import com.atuluttam.SpringBootDBMSValidationNDataBinding.StudentServ.StudentService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
public class SpringBootDbmsValidationNDataBindingApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringBootDbmsValidationNDataBindingApplication.class, args);
		StudentService studentService = ctx.getBean(StudentService.class);
		Validator validator = ctx.getBean(Validator.class);
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Choose an option:");
			System.out.println("1. Add a new student");
			System.out.println("2. Find a student by roll number");
			System.out.println("3. Display all students");
			System.out.println("4. Delete a student by roll number");
			System.out.println("5. Update a student's details");
			System.out.println("6. Exit");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			try {
				switch (choice) {
					case 1:
						Student newStudent = new Student();
						System.out.print("Enter roll number: ");
						newStudent.setRoll(scanner.nextInt());
						scanner.nextLine(); // Consume newline
						System.out.print("Enter name: ");
						newStudent.setName(scanner.nextLine());
						System.out.print("Enter date of birth (YYYY-MM-DD): ");
						newStudent.setDob(LocalDate.parse(scanner.nextLine()));
						System.out.print("Enter mobile number: ");
						newStudent.setMobileno(scanner.nextInt());

						validateStudent(newStudent, validator);
						studentService.saveStudent(newStudent);
						System.out.println("Student added successfully.");
						break;
					case 2:
						System.out.print("Enter roll number: ");
						Student student = studentService.findStudentByRoll(scanner.nextInt());
						System.out.println("Found Student: " + student);
						break;
					case 3:
						List<Student> students = studentService.findAllStudents();
						System.out.println("All Students:");
						for (Student s : students) {
							System.out.println(s);
						}
						break;
					case 4:
						System.out.print("Enter roll number: ");
						studentService.deleteStudentByRoll(scanner.nextInt());
						System.out.println("Student deleted successfully.");
						break;
					case 5:
						Student updatedStudent = new Student();
						System.out.print("Enter roll number: ");
						updatedStudent.setRoll(scanner.nextInt());
						scanner.nextLine(); // Consume newline
						System.out.print("Enter new name: ");
						updatedStudent.setName(scanner.nextLine());
						System.out.print("Enter new date of birth (YYYY-MM-DD): ");
						updatedStudent.setDob(LocalDate.parse(scanner.nextLine()));
						System.out.print("Enter new mobile number: ");
						updatedStudent.setMobileno(scanner.nextInt());

						validateStudent(updatedStudent, validator);
						studentService.updateStudent(updatedStudent);
						System.out.println("Student updated successfully.");
						break;
					case 6:
						System.out.println("Exiting...");
						scanner.close();
						System.exit(0);
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
				}
			} catch (ConstraintViolationException e) {
				System.out.println("Validation Error: " + e.getMessage());
			} catch (NoSuchStudentExist e) {
				System.out.println("Error: " + e.getMessage());
			}
			catch(Exception e)
			{
				System.out.println("Error !!!!"+e.getMessage());
			}
		}
	}

	private static void validateStudent(Student student, Validator validator) {
		Set<ConstraintViolation<Student>> violations = validator.validate(student);
		if (!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("Validation errors: ");
			for (ConstraintViolation<Student> violation : violations) {
				errorMessage.append(violation.getMessage()).append("; ");
			}
			throw new ConstraintViolationException(errorMessage.toString(), violations);
		}
	}
}
