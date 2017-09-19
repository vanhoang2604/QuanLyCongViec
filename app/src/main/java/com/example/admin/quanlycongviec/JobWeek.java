package com.example.admin.quanlycongviec;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Admin on 9/16/2017.
 */

public class JobWeek {
    private String title;
    private String desciption;
    private Date dateFinish;
    private Date hourFinish;

    public JobWeek(String title, String desciption, Date dateFinish, Date hourFinish) {
        this.title = title;
        this.desciption = desciption;
        this.dateFinish = dateFinish;
        this.hourFinish = hourFinish;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(Date dateFinish) {
        this.dateFinish = dateFinish;
    }

    public Date getHourFinish() {
        return hourFinish;
    }

    public void setHourFinish(Date hourFinish) {
        this.hourFinish = hourFinish;
    }

    public String getDateFormat(Date d) {
        SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dft.format(d);
    }

    public String getHourFormat(Date d) {
        SimpleDateFormat dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dft.format(d);
    }

    public String toString() {
        return this.title + "-" + getDateFormat(this.dateFinish) + "-" + getHourFormat(this.hourFinish);
    }
}
