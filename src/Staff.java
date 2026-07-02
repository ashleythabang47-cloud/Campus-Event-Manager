public class Staff extends User {

    public Staff(String userId, String name) {
        super(userId, name);
    }

    public void viewMenu() {
        System.out.println("\n--- Staff Menu ---");
        System.out.println("1. Create Event");
        System.out.println("2. Update Event");
        System.out.println("3. Cancel Event");
        System.out.println("4. View All Events");
        System.out.println("0. Exit");
    }
}