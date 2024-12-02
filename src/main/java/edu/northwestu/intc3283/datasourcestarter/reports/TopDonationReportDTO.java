package edu.northwestu.intc3283.datasourcestarter.reports;

import java.math.BigDecimal;

public class TopDonationReportDTO {

    private String firstName;
    private String lastName;
    private String email;
    private int year;
    private int month;
    private BigDecimal totalDonationAmount;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getTotalDonationAmount() {
        return totalDonationAmount;
    }

    public void setTotalDonationAmount(BigDecimal totalDonationAmount) {
        this.totalDonationAmount = totalDonationAmount;
    }
}