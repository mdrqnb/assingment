package menu;
import exception.InvalidInputException;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner sc = new Scanner(System.in);

    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Owner> owners = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();

    public MenuManager() {
        animals.add(new Dog("Rex", 8, true, "Ovcharka"));
        animals.add(new Cat("Barsik", 1, false, "White"));

        owners.add(new Owner("Ali", "87001234567", "ali@mail.com", false));
        owners.add(new Owner("Dana", "87771234567", "dana@mail.com", true));

        appointments.add(new Appointment("10.09.2025", "Checkup", 10000, false));
    }

    @Override
    public void displayMenu() {
        System.out.println(
                "\n=== MENU ===\n" +
                        "1 Add Dog (Child)\n" +
                        "2 Add Cat (Child)\n" +
                        "3 View Animals\n" +
                        "4 Demo Polymorphism\n" +
                        "5 View Animals by Type (Dog/Cat)\n" +
                        "6 Train Dog (interface demo)\n" +
                        "7 Add Owner\n" +
                        "8 View Owners\n" +
                        "9 Add Appointment\n" +
                        "10 View Appointments\n" +
                        "11 Pay Appointment\n" +
                        "12 VIP Discount for Appointment\n" +
                        "0 Exit"
        );
    }

    @Override
    public void run() {
        while (true) {
            displayMenu();
            System.out.print("Choice: ");

            try {
                int ch = readIntLine();

                switch (ch) {
                    case 1 -> addDog();
                    case 2 -> addCat();
                    case 3 -> viewAnimals();
                    case 4 -> demoPolymorphism();
                    case 5 -> viewAnimalsByType();
                    case 6 -> trainDog();
                    case 7 -> addOwner();
                    case 8 -> viewOwners();
                    case 9 -> addAppointment();
                    case 10 -> viewAppointments();
                    case 11 -> payAppointment();
                    case 12 -> vipDiscount();
                    case 0 -> {
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Wrong choice.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: enter a NUMBER!");
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private void addDog() throws InvalidInputException {
        String name = readNonEmpty("Name: ");
        int age = readInt("Age: ");
        boolean healthy = readBool("Healthy (true/false): ");
        String breed = readNonEmpty("Breed: ");

        animals.add(new Dog(name, age, healthy, breed));
        System.out.println("Dog added!");
    }

    private void addCat() throws InvalidInputException {
        String name = readNonEmpty("Name: ");
        int age = readInt("Age: ");
        boolean healthy = readBool("Healthy (true/false): ");
        String color = readNonEmpty("Color: ");

        animals.add(new Cat(name, age, healthy, color));
        System.out.println("Cat added!");
    }

    private void viewAnimals() {
        for (int i = 0; i < animals.size(); i++) {
            Animal a = animals.get(i);
            System.out.println((i + 1) + ") " + a);

            if (a.needsCheckup()) {
                System.out.println("   -> needs checkup");
            }

            if (a instanceof Dog d) {
                System.out.println("   Dog extra: breed=" + d.getBreed() + ", trick=" + d.getTrick());
                System.out.println("   Old dog? " + d.isOldDog());
            } else if (a instanceof Cat c) {
                System.out.println("   Cat extra: color=" + c.getColor());
                System.out.println("   Kitten? " + c.isKitten());
            }
        }
    }

    private void demoPolymorphism() {
        // ✅ Полиморфизм как в твоём case 5
        for (Animal a : animals) {
            a.action(); // один метод — разная реализация у Dog/Cat
        }
    }

    private void viewAnimalsByType() throws InvalidInputException {
        String t = readNonEmpty("Type (dog/cat): ").trim().toLowerCase();
        for (Animal a : animals) {
            if (t.equals("dog") && a instanceof Dog) System.out.println(a);
            if (t.equals("cat") && a instanceof Cat) System.out.println(a);
        }
    }

    private void trainDog() throws InvalidInputException {
        int idx = readInt("Dog number: ") - 1;
        if (idx < 0 || idx >= animals.size() || !(animals.get(idx) instanceof Dog)) {
            throw new InvalidInputException("Wrong number or it's not a Dog.");
        }
        Dog d = (Dog) animals.get(idx);
        String trick = readNonEmpty("Trick: ");
        d.train(trick);
        System.out.println("✅ Trained! Now trick = " + d.getTrick());
    }

    private void addOwner() throws InvalidInputException {
        String name = readNonEmpty("Name: ");
        String phone = readNonEmpty("Phone: ");
        String email = readNonEmpty("Email: ");
        boolean vip = readBool("VIP (true/false): ");

        owners.add(new Owner(name, phone, email, vip));
        System.out.println("✅ Owner added!");
    }

    private void viewOwners() {
        for (int i = 0; i < owners.size(); i++) {
            Owner o = owners.get(i);
            System.out.println((i + 1) + ") " + o.contactInfo() + " | VIP=" + o.isVip());
        }
    }

    private void addAppointment() throws InvalidInputException {
        String date = readNonEmpty("Date: ");
        String reason = readNonEmpty("Reason: ");
        double price = readDouble("Price: ");
        boolean paid = readBool("Paid (true/false): ");

        appointments.add(new Appointment(date, reason, price, paid));
        System.out.println("✅ Appointment added!");
    }

    private void viewAppointments() {
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ") " + appointments.get(i));
        }
    }

    private void payAppointment() throws InvalidInputException {
        int idx = readInt("Appointment number: ") - 1;
        if (idx < 0 || idx >= appointments.size()) {
            throw new InvalidInputException("Wrong number.");
        }
        appointments.get(idx).pay();
        System.out.println("✅ Paid!");
    }

    private void vipDiscount() throws InvalidInputException {
        int oIdx = readInt("Owner number: ") - 1;
        int aIdx = readInt("Appointment number: ") - 1;

        if (oIdx < 0 || oIdx >= owners.size() || aIdx < 0 || aIdx >= appointments.size()) {
            throw new InvalidInputException("Wrong number.");
        } else if (owners.get(oIdx).isVip()) {
            appointments.get(aIdx).applyDiscount();
            System.out.println("Discount applied!");
        } else {
            System.out.println("Owner is NOT VIP.");
        }
    }

    private int readIntLine() {
        return Integer.parseInt(sc.nextLine().trim());
    }

    private String readNonEmpty(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String s = sc.nextLine();
        if (s == null || s.trim().isEmpty()) {
            throw new InvalidInputException("Input cannot be empty.");
        }
        return s.trim();
    }

    private int readInt(String prompt) throws InvalidInputException {
        String s = readNonEmpty(prompt);
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Enter a valid INTEGER number.");
        }
    }

    private double readDouble(String prompt) throws InvalidInputException {
        String s = readNonEmpty(prompt);
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Enter a valid DOUBLE number.");
        }
    }

    private boolean readBool(String prompt) throws InvalidInputException {
        String s = readNonEmpty(prompt).toLowerCase();
        if (s.equals("true"))
            return true;
        if (s.equals("false"))
            return false;
        throw new InvalidInputException("Enter only true or false.");
    }
}
