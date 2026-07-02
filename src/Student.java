public class Student extends User {

    public Student(String userId, String name) {
        super(userId, name);
    }

    public void viewMenu() {
        System.out.println("\n--- Student Menu ---");
        System.out.println("1. View Events");
        System.out.println("2. Register for Event");
        System.out.println("3. Cancel Registration");
        System.out.println("4. View Status");
        System.out.println("0. Exit");
    }
}