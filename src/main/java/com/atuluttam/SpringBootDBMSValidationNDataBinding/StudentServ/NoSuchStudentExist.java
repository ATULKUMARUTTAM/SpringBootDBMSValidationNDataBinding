package com.atuluttam.SpringBootDBMSValidationNDataBinding.StudentServ;




public class NoSuchStudentExist extends Exception {

    private String message;
    // Constructor with a custom message
    public NoSuchStudentExist(String message) {
        super(message);  // Pass the custom message to the superclass (Exception)
    }

}
