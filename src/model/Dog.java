package model;

public class Dog extends Animal implements Trainable {

   private String breed;
   private String trick = "None";

    public Dog(String name, int age, boolean healthy, String breed) {
        super(name, "Dog", age, healthy);
        setBreed(breed);
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()){
            throw new IllegalArgumentException("Dog breed cannot be empty");
        }
        this.breed = breed.trim();
    }

    @Override
    public void action() {
        System.out.println("Dog " + name + " barks!");
    }

    @Override
    public boolean needsCheckup() {
        return !healthy || age >= 7;
    }

    public void barks() {
        System.out.println(name + " woofs!");
    }

    public boolean isOldDog() {
        return age >= 7;
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
        return super.toString() + " | breed=" + breed + " | trick=" + trick;
    }
}