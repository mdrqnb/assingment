import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    static ArrayList<Animal> animals = new ArrayList<>();
    static ArrayList<Owner> owners = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();

    public static void main(String[] args) {
        animals.add(new Animal("Aktos", "Dog", 3, true));
        animals.add(new Dog("Rex", 8, true, "Ovcharka"));
        animals.add(new Cat("Barsik", 1, false, "White"));

        owners.add(new Owner("Ali", "87001234567", "ali@mail.com", false));
        owners.add(new Owner("Dana", "87771234567", "dana@mail.com", true));

        appointments.add(new Appointment("10.09.2025", "Checkup", 10000, false));

        while (true) {
            System.out.println(
                    "\n=== MENU ===\n" +
                            "1 Add Animal (Parent)\n" +
                            "2 Add Dog (Child)\n" +
                            "3 Add Cat (Child)\n" +
                            "4 View Animals (+ instanceof)\n" +
                            "5 Demo Polymorphism (action)\n" +
                            "6 View Animals by Type (Dog/Cat)\n" +
                            "7 Add Owner\n" +
                            "8 View Owners\n" +
                            "9 Add Appointment\n" +
                            "10 View Appointments\n" +
                            "11 Pay Appointment\n" +
                            "12 VIP Discount for Appointment\n" +
                            "0 Exit"
            );

            int ch = inInt("Choice: ");

            switch (ch) {
                case 1 -> animals.add(new Animal(
                        in("Name: "), in("Type: "),
                        inInt("Age: "), inBool("Healthy (true/false): ")
                ));

                case 2 -> animals.add(new Dog(
                        in("Name: "),
                        inInt("Age: "),
                        inBool("Healthy (true/false): "),
                        in("Breed: ")
                ));

                case 3 -> animals.add(new Cat(
                        in("Name: "),
                        inInt("Age: "),
                        inBool("Healthy (true/false): "),
                        in("Color: ")
                ));

                case 4 -> viewAnimals();

                case 5 -> {
                    for (Animal a : animals) a.action(); // polymorphism
                }

                case 6 -> viewAnimalsByType();

                case 7 -> owners.add(new Owner(
                        in("Name: "), in("Phone: "), in("Email: "),
                        inBool("VIP (true/false): ")
                ));

                case 8 -> {
                    for (int i = 0; i < owners.size(); i++) {
                        Owner o = owners.get(i);
                        System.out.println((i + 1) + ") " + o.contactInfo() + " | VIP=" + o.isVip());
                    }
                }

                case 9 -> appointments.add(new Appointment(
                        in("Date: "), in("Reason: "),
                        inDouble("Price: "), inBool("Paid (true/false): ")
                ));

                case 10 -> {
                    for (int i = 0; i < appointments.size(); i++) {
                        System.out.println((i + 1) + ") " + appointments.get(i));
                    }
                }

                case 11 -> {
                    int idx = inInt("Appointment number: ") - 1;
                    if (idx >= 0 && idx < appointments.size()) appointments.get(idx).pay();
                    else System.out.println("Wrong number.");
                }

                case 12 -> {
                    int oIdx = inInt("Owner number: ") - 1;
                    int aIdx = inInt("Appointment number: ") - 1;

                    if (oIdx < 0 || oIdx >= owners.size() || aIdx < 0 || aIdx >= appointments.size()) {
                        System.out.println("Wrong number.");
                    } else if (owners.get(oIdx).isVip()) {
                        appointments.get(aIdx).applyDiscount();
                        System.out.println("Discount applied!");
                    } else {
                        System.out.println("Owner is NOT VIP.");
                    }
                }

                case 0 -> {
                    sc.close();
                    return;
                }

                default -> System.out.println("Wrong choice.");
            }
        }
    }

    static void viewAnimals() {
        for (int i = 0; i < animals.size(); i++) {
            Animal a = animals.get(i);
            System.out.println((i + 1) + ") " + a);

            if (a.needsCheckup()) System.out.println("   -> needs checkup");

            if (a instanceof Dog) System.out.println("   Dog extra: breed=" + ((Dog) a).getBreed());
            if (a instanceof Cat) System.out.println("   Cat extra: color=" + ((Cat) a).getColor());
        }
    }

    static void viewAnimalsByType() {
        String t = in("Type (dog/cat): ").toLowerCase();
        for (Animal a : animals) {
            if (t.equals("dog") && a instanceof Dog) System.out.println(a);
            if (t.equals("cat") && a instanceof Cat) System.out.println(a);
        }
    }

    static String in(String p) { System.out.print(p); return sc.nextLine(); }
    static int inInt(String p) { return Integer.parseInt(in(p)); }
    static double inDouble(String p) { return Double.parseDouble(in(p)); }
    static boolean inBool(String p) { return Boolean.parseBoolean(in(p)); }
}