public class Cat extends Animal {

    private String color;

    public Cat(String name, int age, boolean healthy, String color) {
        super(name, "Cat", age, healthy);
        if (color == null || color.trim().isEmpty()) {
            this.color = "Unknown";
        } else {
            this.color = color;
        }
    }

    public String getColor() { return color; }

    //№1
    @Override
    public void action() {
        System.out.println("Cat " + name + " purrs!");
    }

    //№2
    @Override
    public boolean needsCheckup() {
        return !healthy || age < 1;
    }

    // 2 unique methods
    public void meow() {
        System.out.println(name + ": Meow!");
    }

    public boolean isKitten() {
        return age < 1;
    }

    @Override
    public String toString() {
        return super.toString() + " | color=" + color;
    }
}
