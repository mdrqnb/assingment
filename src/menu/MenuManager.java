package menu;

import database.AnimalDAO;
import exception.InvalidInputException;
import model.*;

import java.util.List;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner sc = new Scanner(System.in);
    private final AnimalDAO animalDAO = new AnimalDAO();

    @Override
    public void displayMenu() {
        System.out.println(
                "\n=== MENU ===\n" +
                        "1 Add Dog (DB)\n" +
                        "2 Add Cat (DB)\n" +
                        "3 View Animals (DB)\n" +
                        "4 Update Animal (DB)\n" +
                        "5 Delete Animal (DB)\n" +
                        "6 Search Animal by Name (DB)\n" +
                        "7 Search Animals by Age Range (DB)\n" +
                        "8 Add Owner\n" +
                        "9 View Owners\n" +
                        "10 Add Appointment\n" +
                        "11 View Appointments\n" +
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
                    case 1 -> addDogDb();
                    case 2 -> addCatDb();
                    case 3 -> viewAnimalsDb();
                    case 4 -> updateAnimalDb();
                    case 5 -> deleteAnimalDb();
                    case 6 -> searchByNameDb();
                    case 7 -> searchByAgeRangeDb();
                    case 8 -> tempOwner();
                    case 9 -> tempAppointment();
                    case 10 -> tempOwnerAndAppointment();
                    case 11 -> tempVipDiscount();

                    case 0 -> {
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Wrong choice");
                }

            } catch (InvalidInputException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private void addDogDb() throws InvalidInputException {
        String name = readNonEmpty("Name: ");
        int age = readInt("Age: ");
        boolean healthy = readBool("Healthy (true/false): ");
        String breed = readNonEmpty("Breed: ");

        Dog dog = new Dog(name, age, healthy, breed);

        boolean ok = animalDAO.insertAnimal(dog);
        System.out.println(ok ? "Dog saved to DB" : "Dog NOT saved to DB");
    }

    private void addCatDb() throws InvalidInputException {
        String name = readNonEmpty("Name: ");
        int age = readInt("Age: ");
        boolean healthy = readBool("Healthy (true/false): ");
        String color = readNonEmpty("Color: ");

        Cat cat = new Cat(name, age, healthy, color);

        boolean ok = animalDAO.insertAnimal(cat);
        System.out.println(ok ? "Cat saved to DB" : "Cat NOT saved to DB");
    }

    private void viewAnimalsDb() {
        animalDAO.printAllAnimals();
    }

    private void updateAnimalDb() throws InvalidInputException {
        int id = readInt("Enter animal_id to update: ");

        Animal existing = animalDAO.getAnimalById(id);
        if (existing == null) {
            System.out.println("No animal found with ID: " + id);
            return;
        }
        System.out.println("Current: " + existing);

        String newName = readNonEmpty("New name: ");
        String newType = readNonEmpty("New type (Dog/Cat): ");
        int newAge = readInt("New age: ");
        boolean newHealthy = readBool("New healthy (true/false): ");

        boolean ok = animalDAO.updateAnimal(id, newName, newType, newAge, newHealthy);
        System.out.println(ok ? "Updated" : "Update failed");
    }

    private void deleteAnimalDb() throws InvalidInputException {
        int id = readInt("Enter animal_id to delete: ");

        Animal existing = animalDAO.getAnimalById(id);
        if (existing == null) {
            System.out.println("No animal found with ID: " + id);
            return;
        }

        System.out.println("Animal to delete: " + existing);
        String confirm = readNonEmpty("Are you sure? (yes/no): ");
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        boolean ok = animalDAO.deleteAnimal(id);
        System.out.println(ok ? "Deleted" : "Delete failed");
    }

    private void searchByNameDb() throws InvalidInputException {
        String q = readNonEmpty("Enter part of name: ");
        List<Animal> found = animalDAO.searchByName(q);

        System.out.println("--- Found: " + found.size() + " ---");
        for (Animal a : found) System.out.println(a);
    }

    private void searchByAgeRangeDb() throws InvalidInputException {
        int min = readInt("Min age: ");
        int max = readInt("Max age: ");

        List<Animal> found = animalDAO.searchByAgeRange(min, max);

        System.out.println("--- Found: " + found.size() + " ---");
        for (Animal a : found) System.out.println(a);
    }

    private Owner inputOwner() throws InvalidInputException {
        String name = readNonEmpty("Owner name: ");
        String phone = readNonEmpty("Phone: ");
        String email = readNonEmpty("Email: ");
        boolean vip = readBool("VIP (true/false): ");
        return new Owner(name, phone, email, vip);
    }

    private Appointment inputAppointment() throws InvalidInputException {
        String date = readNonEmpty("Date: ");
        String reason = readNonEmpty("Reason: ");
        double price = readDouble("Price: ");
        boolean paid = readBool("Paid (true/false): ");
        return new Appointment(date, reason, price, paid);
    }

    private void tempOwner() throws InvalidInputException {
        System.out.println("\n--- OWNER ---");
        Owner o = inputOwner();
        System.out.println(o);
    }

    private void tempAppointment() throws InvalidInputException {
        System.out.println("\n--- APPOINTMENT ---");
        Appointment a = inputAppointment();
        System.out.println(a);
    }

    private void tempOwnerAndAppointment() throws InvalidInputException {
        System.out.println("\n---  OWNER + APPOINTMENT ---");
        Owner o = inputOwner();
        Appointment a = inputAppointment();
        System.out.println(o);
        System.out.println(a);
    }

    private void tempVipDiscount() throws InvalidInputException {
        System.out.println("\n--- VIP DISCOUNT ---");
        Owner o = inputOwner();
        Appointment a = inputAppointment();

        if (o.isVip()) {
            boolean ok = a.applyDiscount();
            System.out.println(ok ? "Discount applied!" : "Discount NOT applied.");
        } else {
            System.out.println("Owner is not VIP. No discount.");
        }
        System.out.println("Result appointment: " + a);
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
        if (s.equals("true")) return true;
        if (s.equals("false")) return false;
        throw new InvalidInputException("Enter only true or false");
    }
}