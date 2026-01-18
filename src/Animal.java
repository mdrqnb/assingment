package model;

public abstract class Animal {

    protected String name;
    protected String type;
    protected int age;
    protected boolean healthy;

    public Animal(String name, String type, int age, boolean healthy) {
        setName(name);
        setType(type);
        setAge(age);
        setHealthy(healthy);
    }

    public Animal() {
        this.name = "Unknown";
        this.type = "Unknown";
        this.age = 0;
        this.healthy = true;
    }

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getAge() {
        return age;
    }
    public boolean isHealthy() {
        return healthy;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Animal name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Animal type cannot be empty");
        }
        this.type = type.trim();
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Animal age cannot be negative: " + age);
        }
        this.age = age;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public abstract void action();

    public boolean needsCheckup() {
        return !healthy;
    }

    @Override
    public String toString() {
        return "Animal{name='" + name + "', type='" + type +
                "', age=" + age + ", healthy=" + healthy + "}";
    }
}
