public class Dog extends Animal {

    private String breed;

    public Dog(String name, int age, boolean healthy, String breed) {
        super(name, "Dog", age, healthy);   // super() first
        if (breed == null || breed.trim().isEmpty()) {
            this.breed = "Unknown";
        } else {
            this.breed = breed;
        }
    }

    public String getBreed() {
        return breed; }

    //№1
    @Override
    public void action() {
        System.out.println("Dog " + name + " barks!");
    }

    //№2
    @Override
    public boolean needsCheckup() {
        return !healthy || age >= 7;
    }

    // 2 unique methods
    public void bark() {
        System.out.println(name + ": Woof!");
    }

    public boolean isOldDog() {
        return age >= 7;
    }

    @Override
    public String toString() {
        return super.toString() + " | breed=" + breed;
    }
}
