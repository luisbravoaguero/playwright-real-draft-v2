package com.mapfre.models;

public class CustomerData {
    private final String fullName;
    private final String email;
    private final String documentId;
    private final String startDate;

    public CustomerData(String fullName, String email, String documentId, String startDate) {
        this.fullName = fullName;
        this.email = email;
        this.documentId = documentId;
        this.startDate = startDate;
    }

    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getDocumentId() { return documentId; }
    public String getStartDate() { return startDate; }
}