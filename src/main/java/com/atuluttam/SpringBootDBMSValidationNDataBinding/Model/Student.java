package com.atuluttam.SpringBootDBMSValidationNDataBinding.Model;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class Student {
    @NotNull(message = "Roll Number can't be NULL")
    @Positive(message = "Roll Must be +ve")
    @Min(value = 100, message = "Min value for ROll is 100")
    @Max(value = 1000, message = "Max value for Roll is 1000")
    int roll;

    @Past(message = "DOB must be past Date")
    LocalDate dob;

    @NotBlank(message = "Name can't be blank")
    @Size(min=1, max=20, message = "Name must be between 1 to 20 char")
    String name;

    @Digits(integer = 10, fraction = 0, message = "Mobile number must be exactly 10 digits.")
    int mobileno;

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMobileno() {
        return mobileno;
    }

    public void setMobileno(int mobileno) {
        this.mobileno = mobileno;
    }

    @Override
    public String toString() {
        return "Student{" +
                "roll=" + roll +
                ", dob=" + dob +
                ", name='" + name + '\'' +
                ", mobileno=" + mobileno +
                '}';
    }
}
