package model;

public class Owner {

    private String name;
    private String phone;
    private String email;
    private boolean vip;

    public Owner(String name, String phone, String email, boolean vip) {
        setName(name);
        setPhone(phone);
        setEmail(email);
        setVip(vip);
    }

    public Owner() {
        this.name = "Unknown";
        this.phone = "0000000000";
        this.email = "unknown@email.com";
        this.vip = false;
    }

    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public boolean isVip() {
        return vip;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().length() < 10) {
            throw new IllegalArgumentException("Phone must be at least 10 chars");
        }
        this.phone = phone.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.email = email.trim();
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String contactInfo() {
        return name + " (" + phone + ")";
    }

    @Override
    public String toString() {
        return "Owner{name='" + name + "', phone='" + phone +
                "', email='" + email + "', vip=" + vip + "}";
    }
}
