package edu.northwestu.intc3283.datasourcestarter.reports;

public class WeeklyDonationRow {
    private String donationWeek;
    private Integer totalDonated;

    public String getDonationWeek() {
        return donationWeek;
    }

    public void setDonationWeek(String donationWeek) {
        this.donationWeek = donationWeek;
    }

    public Integer getTotalDonated() {
        return totalDonated;
    }

    public void setTotalDonated(Integer totalDonated) {
        this.totalDonated = totalDonated;
    }
}
