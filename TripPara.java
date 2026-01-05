package project;

// trip format



public class TripPara {
    private int tripId;
    private String city;
    private double distance;
    private int duration;
    private double fare;
    private double tip;
    private int rating;

    public TripPara(int tripId, String city, double distance, int duration, double fare, double tip, int rating) {
        this.tripId = tripId;
        this.city = city;
        this.distance = distance;
        this.duration = duration;
        this.fare = fare;
        this.tip = tip;
        this.rating = rating;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getTip() {
        return tip;
    }

    public void setTip(double tip) {
        this.tip = tip;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}