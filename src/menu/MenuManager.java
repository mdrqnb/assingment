package menu;

import database.AnimalDAO;
import exception.InvalidInputException;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuManager implements Menu {

    private final Scanner sc = new Scanner(System.in);

    private final AnimalDAO animalDAO = new AnimalDAO();

    private final ArrayList<Owner> owners = new ArrayList<>();
    private final ArrayList<Appointment> appointments = new ArrayList<>();

    public MenuManager() {
        owners.add(new Owner("Ali", "87001234567", "ali@mail.com", false));
        owners.add(new Owner("Dana", "87771234567", "dana@mail.com", true));

        appointments.add(new Appointment("10.09.2025", "Checkup", 10000, false));
    }

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
                        "8 Search Animals by Min Age (DB)\n" +
                        "9 Add Owner\n" +
                        "10 View Owners\n" +
                        "11 Add Appointment\n" +
                        "12 View Appointments\n" +
                        "13 VIP Discount for Appointment\n" +
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
                    case 8 -> searchByMinAgeDb();
                    case 9 -> addOwner();
                    case 10 -> viewOwners();
                    case 11 -> addAppointment();
                    case 12 -> viewAppointments();
                    case 13 -> vipDiscount();

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

    private void searchByMinAgeDb() throws InvalidInputException {
        int min = readInt("Min age: ");

        List<Animal> found = animalDAO.searchByMinAge(min);

        System.out.println("--- Found: " + found.size() + " ---");
        for (Animal a : found) System.out.println(a);
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
        if (s.equals("true")) return true;
        if (s.equals("false")) return false;
        throw new InvalidInputException("Enter only true or false");
    }
}
