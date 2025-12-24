public class Main {

    public static void main(String[] args) {

        Animal a1 = new Animal ("Buddy", "Dog", 3, true);
        Animal a2 = new Animal ("Milo", "Cat", 1, false);

        Owner o1 = new Owner ("Ali", "87001234567", "ali@mail.com", false);
        Owner o2 = new Owner ("Dana", "87771234567", "dana@mail.com", true);

        Appointment ap1 = new Appointment ("10.09.2025", "Checkup", 10000, false);

        System.out.println(a1);
        System.out.println(a2);

        System.out.println(o1.contactInfo());
        System.out.println(o2.contactInfo());

        if (a2.needsCheckup()) {
            System.out.println("Milo needs a checkup.");
        }

        if (o2.isVip()) {
            ap1.applyDiscount();
        }

        ap1.pay();

        System.out.println(ap1);
    }
}
