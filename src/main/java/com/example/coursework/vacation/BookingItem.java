package com.example.coursework.vacation;

import com.example.coursework.logs.Logs;

import java.sql.*;

public class BookingItem {
    private int id;
    private int user_id;
    private int vacation_id;
    private String transport;
    private String days;
    private Byte food;

    private String name;
    private String descr;
    private String type;

    private Logs logs = Logs.getInstance();


    public BookingItem(int id, int user_id, int vacation_id, String transport, String days, Byte food, String name, String descr, String type) {
        this.id = id;
        this.user_id = user_id;
        this.vacation_id = vacation_id;
        this.transport = transport;
        this.days = days;
        this.food = food;
        this.name = name;
        this.descr = descr;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVacation_id() {
        return vacation_id;
    }

    public void setVacation_id(int vacation_id) {
        this.vacation_id = vacation_id;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public Byte getFood() {
        return food;
    }

    public void setFood(Byte food) {
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFoodS() {
        if(food==1){
            return "yes";
        }
        else{
            return "no";
        }
    }

    public String getTypeS() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/vacations_db","mysql","mysql");
        Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=stmt.executeQuery("select name from types where id=" + type + ";");
        if(rs.next())
            return rs.getString(1);
        else
            return "Error";
    }

    @Override
    public String toString(){
        String details;
        try {
            details = "Name: " + this.getName() +
                    "\n\nDescription: " + this.getDescr() +
                    "\n\nType: " + this.getTypeS() +
                    "\n\nTransport: " + this.getTransport() +
                    "\n\nDays: " + this.getDays() +
                    "\n\nMay include food: " + this.getFoodS();
            return details;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
