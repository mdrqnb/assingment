public class Owner {

    private String name;
    private String phone;
    private String email;
    private boolean vip;

    public Owner(String name, String phone, String email, boolean vip) {
        setName(name);
        setPhone(phone);
        setEmail(email);
        this.vip = vip;
    }

    public Owner() {
        this.name = "Unknown";
        this.phone = "N/A";
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
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            this.name = "Unknown";
        }
    }

    public void setPhone(String phone) {
        if (phone != null && phone.trim().length() >= 10) {
            this.phone = phone;
        } else {
            this.phone = "N/A";
        }
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            this.email = "unknown@email.com";
        }
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