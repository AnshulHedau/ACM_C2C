package com.sada.edubuddy;

/**
 * Created by Shantanu on 3/10/2018.
 */

public class Timeline {
    String from;
    String to;
    String date;
    String description;
    String icon_identifier;

    String duration;

    public Timeline() {
    }

    public Timeline(String from, String to, String date, String description, String icon_identifier, String duration) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.description = description;
        this.icon_identifier = icon_identifier;
        this.duration = duration;
    }


    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon_identifier() {
        return icon_identifier;
    }

    public void setIcon_identifier(String icon_identifier) {
        this.icon_identifier = icon_identifier;
    }
}
