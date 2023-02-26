package com.example.coursework.vacation;

import java.sql.*;

public class VacationItem {
    private int id;
    private String name;
    private String descr;
    private String type;
    private String transport;
    private String days;
    private Byte food;
    private Integer price;

    public VacationItem(int id, String name, String descr, String type, String transport, String days, Byte food, Integer price) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.type = type;
        this.transport = transport;
        this.days = days;
        this.food = food;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescr() {
        return descr;
    }

    public String getType() {
        return type;
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

    public String getTransport() {
        return transport;
    }

    public String getDays() {
        return days;
    }

    public Byte getFood() {
        return food;
    }

    public String getFoodS() {
        if(food==1){
            return "yes";
        }
        else{
            return "no";
        }
    }

    public Integer getPrice() {
        return price;
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
                    "\n\nMay include food: " + this.getFoodS() +
                    "\n\nStart price: from $" + this.getPrice();
            return details;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
