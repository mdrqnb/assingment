public class Appointment {

    private String date;
    private String reason;
    private double price;
    private boolean paid;

    public Appointment(String date, String reason, double price, boolean paid) {
        setDate(date);
        setReason(reason);
        setPrice(price);
        this.paid = paid;
    }

    public Appointment() {
        this.date = "Not set";
        this.reason = "Not specified";
        this.price = 0.0;
        this.paid = false;
    }

    public String getDate() {
        return date; }
    public String getReason() {
        return reason; }
    public double getPrice() {
        return price; }
    public boolean isPaid() {
        return paid; }

    public void setDate(String date) {
        if (date != null && !date.trim().isEmpty()) {
            this.date = date;
        } else {
            this.date = "Not set";
        }
    }

    public void setReason(String reason) {
        if (reason != null && !reason.trim().isEmpty()) {
            this.reason = reason;
        } else {
            this.reason = "Not specified";
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            this.price = 0.0;
        }
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void pay() {
        paid = true;
    }

    public void applyDiscount() {
        if (!paid) {
            price = price * 0.9;
        }
    }

    @Override
    public String toString() {
        return "Appointment{date='" + date + "', reason='" + reason +
                "', price=" + price + ", paid=" + paid + "}";
    }
}