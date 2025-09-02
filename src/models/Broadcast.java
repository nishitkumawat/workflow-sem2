package models;

import java.sql.Timestamp;

public class Broadcast {

    private int id;
    private int companyId;
    private String message;
    private Timestamp timestamp;

    public Broadcast(int id, int companyId, String message, Timestamp timestamp) {
        this.id = id;
        this.companyId = companyId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

