package menu;

import database.AnimalDAO;
import exception.InvalidInputException;
import model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner sc = new Scanner(System.in);

    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Owner> owners = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();

    private final AnimalDAO animalDAO = new AnimalDAO();

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
                        "1 Add Dog\n" +
                        "2 Add Cat\n" +
                        "3 View Animals\n" +
                        "4 Train Dog\n" +
                        "5 Add Owner\n" +
                        "6 View Owners\n" +
                        "7 Add Appointment\n" +
                        "8 View Appointments\n" +
                        "9 VIP Discount for Appointment\n" +
                        "0 Exit"
        );
    }

    @Override
    public void run() {
        while (true) {
            displayMenu();
            try {
                int ch = readInt("Choice: ");

                switch (ch) {
                    case 1 -> addDog();
                    case 2 -> addCat();
                    case 3 -> viewAnimals();
                    case 4 -> trainDog();
                    case 5 -> addOwner();
                    case 6 -> viewOwners();
                    case 7 -> addAppointment();
                    case 8 -> viewAppointments();
                    case 9 -> vipDiscount();
                    case 0 -> {
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Wrong choice");
                }

            } catch (InvalidInputException e){
                System.out.println("Input error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
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

        Dog dog = new Dog(name, age, healthy, breed);
        animals.add(dog);
        System.out.println("Dog added");

        boolean ok = animalDAO.insertAnimal(dog);
        if (!ok) System.out.println("WARNING: Dog was NOT saved to database!");
    }

    private void addCat() throws InvalidInputException {
        String name = readNonEmpty("Name: ");
        int age = readInt("Age: ");
        boolean healthy = readBool("Healthy (true/false): ");
        String color = readNonEmpty("Color: ");

        Cat cat = new Cat(name, age, healthy, color);
        animals.add(cat);
        System.out.println("Cat added");

        boolean ok = animalDAO.insertAnimal(cat);
        if (!ok) System.out.println("WARNING: Cat was NOT saved to database!");
    }

    private void viewAnimals() {
        animalDAO.printAllAnimals();

        int i = 1;
        for (Animal a : animals) {
            System.out.println(i++ + ") " + a);
            if (a.needsCheckup()) System.out.println("  --> needs Checkup");
            System.out.println();
        }
    }

    private void trainDog() throws InvalidInputException {
        ArrayList<Dog> dogs = new ArrayList<>();

        for (Animal a : animals)
            if (a instanceof Dog)
                dogs.add((Dog) a);

        if (dogs.isEmpty()) {
            System.out.println("No dogs available");
            return;
        }

        for (int i = 0; i < dogs.size(); i++)
            System.out.println((i + 1) + ") " + dogs.get(i));

        int idx = readInt("Choose dog number: ") - 1;
        if (idx < 0 || idx >= dogs.size())
            throw new InvalidInputException("Wrong dog number");

        dogs.get(idx).train(readNonEmpty("Trick: "));
        System.out.println("Trained. Now trick = " + dogs.get(idx).getTrick());
    }

    private void addOwner() throws InvalidInputException {
        String name = readNonEmpty("Name: ");
        String phone = readNonEmpty("Phone: ");
        String email = readNonEmpty("Email: ");
        boolean vip = readBool("VIP (true/false): ");
        owners.add(new Owner(name, phone, email, vip));
        System.out.println("Owner added");
    }

    private void viewOwners() {
        for (int i = 0; i < owners.size(); i++) {
            Owner o = owners.get(i);
            System.out.println((i + 1) + ") " + o.contactInfo() + " | VIP = " + o.isVip());
        }
    }

    private void addAppointment() throws InvalidInputException {
        String date = readNonEmpty("Date: ");
        String reason = readNonEmpty("Reason: ");
        double price = readDouble("Price: ");
        boolean paid = readBool("Paid (true/false): ");
        appointments.add(new Appointment(date, reason, price, paid));
        System.out.println("Appointment added");
    }

    private void viewAppointments() {
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ") " + appointments.get(i));
        }
    }

    private void vipDiscount() throws InvalidInputException {
        int oIdx = readInt("Owner number: ") - 1;
        int aIdx = readInt("Appointment number: ") - 1;

        if (oIdx < 0 || oIdx >= owners.size())
            throw new InvalidInputException("Wrong owner number");
        if (aIdx < 0 || aIdx >= appointments.size())
            throw new InvalidInputException("Wrong appointment number");

        if (!owners.get(oIdx).isVip()) {
            System.out.println("Owner is NOT VIP");
            return;
        }

        System.out.println(appointments.get(aIdx).applyDiscount()
                ? "Discount applied"
                : "Discount NOT applied");
    }

    private String readNonEmpty(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String s = sc.nextLine().trim();
        if (s.isEmpty())
            throw new InvalidInputException("Input cannot be empty");
        return s;
    }

    private int readInt(String prompt) throws InvalidInputException {
        try {
            return Integer.parseInt(readNonEmpty(prompt));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Enter a valid INTEGER number");
        }
    }

    private double readDouble(String prompt) throws InvalidInputException {
        try {
            return Double.parseDouble(readNonEmpty(prompt));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Enter a valid DOUBLE number");
        }
    }

    private boolean readBool(String prompt) throws InvalidInputException {
        String s = readNonEmpty(prompt).toLowerCase();
        if (s.equals("true"))
            return true;
        if (s.equals("false"))
            return false;
        throw new InvalidInputException("Enter only true or false");
    }
}
