package com.example.medication.vinhquang.data;

public class AppointmentNotiResponse {
    private Integer id;
    private Integer type;
    private String title;
    private Integer role;
    private UserResponse receiver;
    private AppointmentResponse appointmentResponse;

    public AppointmentNotiResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public UserResponse getReceiver() {
        return receiver;
    }

    public void setReceiver(UserResponse receiver) {
        this.receiver = receiver;
    }

    public AppointmentResponse getAppointmentResponse() {
        return appointmentResponse;
    }

    public void setAppointmentResponse(AppointmentResponse appointmentResponse) {
        this.appointmentResponse = appointmentResponse;
    }
}
