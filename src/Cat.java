package model;

public class Cat extends Animal {

    private String color;

    public Cat(String name, int age, boolean healthy, String color) {
        super(name, "Cat", age, healthy);
        setColor(color);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()) {
            throw new IllegalArgumentException("Cat color cannot be empty");
        }
        this.color = color.trim();
    }

    @Override
    public void action() {
        System.out.println("Cat " + name + " purrs!");
    }

    @Override
    public boolean needsCheckup() {
        return !healthy || age < 1;
    }

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
