import java.util.Scanner;

public class Main {


    static Scanner input = new Scanner(System.in);


    static EventManager manager = new EventManager();

    public static void main(String[] args) {

        // Try  load any events that were saved before the program closed
        try {
            manager.loadFromFile();
        } catch (Exception e) {
            System.out.println("Could not load saved events.");
        }


        boolean running = true;   // keeps the main menu running until the user chooses to exit

        while (running) {

            System.out.println("\nWelcome to Campus Event System");
            System.out.println("1. Student");
            System.out.println("2. Staff");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int role;
            // Try to read the user's choice, catch any wrong input like letters
            try {
                role = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid option.");
                input.nextLine();
                continue;
            }


            if (role == 1) {
                studentMenu();
            } else if (role == 2) {
                staffMenu();
            } else if (role == 3) {

                running = false; //Set running false to stop the while loop

            } else {
                System.out.println("Invalid option.");
            }
        }

        // Save all events to a file before the program exits
        try {
            manager.saveToFile();
        } catch (Exception e) {
            System.out.println("Could not save events.");
        }

        System.out.println("Goodbye.");
    }

    public static void studentMenu() {


        System.out.print("Enter Student ID: ");
        String id = input.nextLine();

        System.out.print("Enter Name: ");
        String name = input.nextLine();


        Student student = new Student(id, name);


        boolean back = false;   // Keep the student menu open until they choose to go back

        while (!back) {

            System.out.println("\nStudent Menu");
            System.out.println("1. View Events");
            System.out.println("2. Register");
            System.out.println("3. Cancel Registration");
            System.out.println("4. View Status");
            System.out.println("5. Search Events");
            System.out.println("6. Back");
            System.out.print("Choose option: ");

            int choice;

            try {
                choice = input.nextInt();
                input.nextLine(); // clear the buffer
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                input.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    manager.viewAllEvents();
                    break;

                case 2:

                    System.out.print("Enter Event ID: ");
                    int regId = input.nextInt();
                    input.nextLine();


                    Event event = manager.findEvent(regId);
                    if (event != null) {
                        event.registerStudent(student);
                    } else {
                        System.out.println("Event not found.");
                    }
                    break;

                case 3:

                    System.out.print("Enter Event ID: ");
                    int cancelId = input.nextInt();
                    input.nextLine();


                    Event ev = manager.findEvent(cancelId);
                    if (ev != null) {
                        ev.cancelRegistration(student);
                    } else {
                        System.out.println("Event not found.");
                    }
                    break;

                case 4:

                    System.out.print("Enter Event ID: ");
                    int statusId = input.nextInt();
                    input.nextLine();

                    Event e = manager.findEvent(statusId);
                    if (e != null) {
                        System.out.println("Status: " + e.getStudentStatus(student));
                    } else {
                        System.out.println("Event not found.");
                    }
                    break;

                case 5:

                    searchMenu();
                    break;

                case 6:

                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void staffMenu() {

        boolean back = false;

        while (!back) {
            System.out.println("\nStaff Menu");
            System.out.println("1. Create Event");
            System.out.println("2. View Events");
            System.out.println("3. Update Event");
            System.out.println("4. Cancel Event");
            System.out.println("5. Sort Events");     // new
            System.out.println("6. Search Events");   // new
            System.out.println("7. Back");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid choice.");
                input.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    createEvent();
                    break;

                case 2:
                    manager.viewAllEvents();
                    break;

                case 3:

                    updateEvent();
                    break;

                case 4:

                    System.out.print("Enter Event ID to cancel: ");
                    int cancelId = input.nextInt();
                    input.nextLine();
                    manager.cancelEvent(cancelId);
                    break;

                case 5:

                    sortMenu();
                    break;

                case 6:

                    searchMenu();
                    break;

                case 7:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }


        }
    }

    public static void createEvent() {

        //  try-catch to wrap everything in case the staff enters wrong input
        try {

            System.out.print("Enter Event ID: ");
            int id = input.nextInt();
            input.nextLine();

            System.out.print("Enter Name: ");
            String name = input.nextLine();

            System.out.print("Enter Date(dd/mm/yy): ");
            String date = input.nextLine();

            if (!date.matches("\\d{2}/\\d{2}/\\d{2}")) {
                System.out.println("Invalid date format. Please use dd/mm/yy.");
                return;
            }

            System.out.print("Enter Time(HH:MM): ");
            String time = input.nextLine();

            if (!time.matches("\\d{2}:\\d{2}")) {
                System.out.println("Invalid time format. Please use HH:MM.");
                return;
            }

            System.out.print("Enter Location: ");
            String location = input.nextLine();

            System.out.print("Enter Max Participants: ");
            int max = input.nextInt();
            input.nextLine();


            Event event = new Event(id, name, date, time, location, max);
            manager.addEvent(event);

        } catch (Exception e) {
            System.out.println("Invalid input.");
            input.nextLine(); // clear bad input
        }
    }


    public static void sortMenu() {
        System.out.println("\nSort Events By:");
        System.out.println("1. Event Name");
        System.out.println("2. Event Date");
        System.out.print("Choose option: ");

        int sortChoice;
        try {
            sortChoice = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid choice.");
            input.nextLine();
            return;
        }

        if (sortChoice == 1) {
            manager.sortByName();
            manager.viewAllEvents();
        } else if (sortChoice == 2) {
            manager.sortByDate();
            manager.viewAllEvents();
        } else {
            System.out.println("Invalid choice.");
        }
    }


    public static void searchMenu() {
        System.out.println("\nSearch Events By:");
        System.out.println("1. Event Name");
        System.out.println("2. Event Date");
        System.out.print("Choose option: ");

        int searchChoice;
        try {
            searchChoice = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid choice.");
            input.nextLine();
            return;
        }

        if (searchChoice == 1) {
            System.out.print("Enter event name to search: ");
            String keyword = input.nextLine();
            manager.searchEvents(keyword, false); // false = search by name
        } else if (searchChoice == 2) {
            System.out.print("Enter date to search (dd/mm/yy): ");
            String date = input.nextLine();
            manager.searchEvents(date, true); // true = search by date
        } else {
            System.out.println("Invalid choice.");
        }
    }


    public static void updateEvent() {

        try {

            System.out.print("Enter Event ID to update: ");
            int id = input.nextInt();
            input.nextLine();


            Event event = manager.findEvent(id);
            if (event == null) {
                System.out.println("Event not found.");
                return;
            }

            // show current details so staff knows what they are changing
            System.out.println("\nCurrent Details:");
            event.displayEvent();


            System.out.println("\nEnter new details (press Enter to keep current value):");

            System.out.print("New Name [" + event.getName() + "]: ");
            String newName = input.nextLine();

            System.out.print("New Time (HH:MM) [" + event.getTime() + "]: ");
            String newTime = input.nextLine();


            if (!newTime.isEmpty() && !newTime.matches("\\d{2}:\\d{2}")) {
                System.out.println("Invalid time format. Please use HH:MM.");
                return;
            }

            System.out.print("New Location [" + event.getLocation() + "]: ");
            String newLocation = input.nextLine();


            event.updateEvent(newName, newTime, newLocation);

        } catch (Exception e) {
            System.out.println("Invalid input.");
            input.nextLine();
        }
    }
}