package com.example.hethongdatvexemphim.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Ticket implements Serializable {
    private int Id_Ticket;
    private int Id_ShowTime;
    private String UserName;
    private Timestamp Date;
    private String Status;

    public Ticket() {
    }

    public Ticket(int id_Ticket, int id_ShowTime, String userName, Timestamp date, String status) {
        Id_Ticket = id_Ticket;
        Id_ShowTime = id_ShowTime;
        UserName = userName;
        Date = date;
        Status = status;
    }
    public Ticket( int id_ShowTime, String userName, Timestamp date, String status) {

        Id_ShowTime = id_ShowTime;
        UserName = userName;
        Date = date;
        Status = status;
    }

    // Getters and Setters
    public int getId_Ticket() {
        return Id_Ticket;
    }

    public void setId_Ticket(int id_Ticket) {
        this.Id_Ticket = id_Ticket;
    }

    public int getId_ShowTime() {
        return Id_ShowTime;
    }

    public void setId_ShowTime(int id_ShowTime) {
        this.Id_ShowTime = id_ShowTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public Timestamp getDate() {
        return Date;
    }

    public void setDate(Timestamp date) {
        this.Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
