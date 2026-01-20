package model;

public class Appointment {

    private String date;
    private String reason;
    private double price;
    private boolean paid;

    private boolean discounted;

    public Appointment(String date, String reason, double price, boolean paid) {
        setDate(date);
        setReason(reason);
        setPrice(price);
        setPaid(paid);
        this.discounted = false;
    }

    public Appointment() {
        this.date = "Not set";
        this.reason = "Not specified";
        this.price = 0.0;
        this.paid = false;
        this.discounted = false;
    }

    public String getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Date cannot be empty");
        }
        this.date = date.trim();
    }

    public void setReason(String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Reason cannot be empty");
        }
        this.reason = reason.trim();
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void pay() {
        if (paid) return;
        paid = true;
    }

    public boolean applyDiscount() {
        if (paid)
            return false;
        if (discounted)
            return false;

        price = price * 0.9;
        discounted = true;
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{date='" + date + "', reason='" + reason +
                "', price=" + price + ", paid=" + paid +
                ", discounted=" + discounted + "}";
    }
}
