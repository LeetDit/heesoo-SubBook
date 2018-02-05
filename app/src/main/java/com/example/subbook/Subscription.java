package com.example.subbook;

import java.util.Date;

/**
 * Created by heesoopark on 2018-02-04.
 */

public abstract class Subscription implements Subscriptable {
    private String name;
    private String date;
    private double charge;
    private String comment;

    Subscription(String name, String date, double charge) {
        this.name = name;
        this.date = date;
        this.charge = charge;
    }

    Subscription(String name, String date, double charge, String comment) {
        this.name = name;
        this.date = date;
        this.charge = charge;
        this.comment = comment;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getCharge() {
        return charge;
    }

    public String getComment() {
        return comment;
    }

    public void setName(String name) throws NameTooLongException {
        if (name.length() <= 20) {
            this.name = name;
        } else {
            throw new NameTooLongException();
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setComment(String comment) throws CommentTooLongException {
        if (comment.length() <= 30) {
            this.comment = comment;
        }else{
            throw new CommentTooLongException();
        }
    }



    public String toString() {
        return "Name: " + name + "\nDate: " + date + "\nCharge: " + String.valueOf(charge) + "\nComment: " + comment;

    }

}
