package com.example.drivefor2;

public class model
{
  String type,date,message,purl;
    model()
    {

    }
    public model(String type, String date, String message, String purl) {
        this.type = type;
        this.date = date;
        this.message = message;
        this.purl = purl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
