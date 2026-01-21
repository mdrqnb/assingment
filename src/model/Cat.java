package model;

public class Cat extends Animal implements Trainable {

    private String color;
    private String trick = "None";

    public Cat(String name, int age, boolean healthy, String color) {
        super(name, "Cat" , age, healthy);
        setColor(color);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == null || color.trim().isEmpty()){
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
        return !healthy || age <= 1;
    }

    public void meow() {
        System.out.println(name + ": meow!");
    }

    public boolean isKitten() {
        return age <=1;
    }

    @Override
    public void train(String trick) {
        if (trick == null || trick.trim().isEmpty()) {
            throw new IllegalArgumentException("Trick cannot be empty");
        }
        this.trick = trick.trim();
    }

    @Override
    public String getTrick() {
        return trick;
    }

    @Override
    public String toString() {
        return super.toString() + " | color=" + color + " | trick=" + trick;
    }
}