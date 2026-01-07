public class Animal {

    protected String name;
    protected String type;
    protected int age;
    protected boolean healthy;

    public Animal(String name, String type, int age, boolean healthy) {
        setName(name);
        setType(type);
        setAge(age);
        this.healthy = healthy;
    }

    public Animal() {
        this.name = "Unknown";
        this.type = "Unknown";
        this.age = 0;
        this.healthy = true;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getAge() { return age; }
    public boolean isHealthy() { return healthy; }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
        else this.name = "Unknown";
    }

    public void setType(String type) {
        if (type != null && !type.trim().isEmpty()) this.type = type;
        else this.type = "Unknown";
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            this.age = 0;
        }
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public void action() {
        System.out.println("Animal " + name + " is doing something...");
    }

    public boolean needsCheckup() {
        return !healthy;
    }

    @Override
    public String toString() {
        return "Animal{name='" + name + "', type='" + type +
                "', age=" + age + ", healthy=" + healthy + "}";
    }
}