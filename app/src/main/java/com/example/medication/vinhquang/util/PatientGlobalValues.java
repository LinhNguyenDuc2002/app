package com.example.medication.vinhquang.util;

public class PatientGlobalValues {
    private static PatientGlobalValues instance;
    private Integer id;
    private String fullName;
    private String dateOfBirth;
    private String phoneNumber;

    private PatientGlobalValues() {
        // Khởi tạo các giá trị mặc định
    }

    public static PatientGlobalValues getInstance() {
        if (instance == null) {
            instance = new PatientGlobalValues();
        }
        return instance;
    }

    public static void setInstance(PatientGlobalValues instance) {
        PatientGlobalValues.instance = instance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
