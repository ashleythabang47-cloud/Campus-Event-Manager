
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Event {


    private int eventId;
    private String name;
    private String date;
    private String time;
    private String location;
    private int maxParticipants;


    private ArrayList<String> registered;
    private Queue<String> waitlist;


    // Constructor that sets up the event
    public Event(int eventId, String name, String date, String time, String location, int maxParticipants) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.maxParticipants = maxParticipants;
        registered = new ArrayList<>();
        waitlist = new LinkedList<>();
    }


    public void registerStudent(Student student) {
        String id = student.getUserId();


        if (registered.contains(id) || waitlist.contains(id)) {
            System.out.println("Student already registered or waitlisted.");
            return;
        }


        if (registered.size() < maxParticipants) {
            registered.add(id);
            System.out.println(id + " registered successfully.");
        } else {

            waitlist.add(id);
            System.out.println("Event full. " + id + " added to waitlist.");
        }
    }


    public void cancelRegistration(Student student) {
        String id = student.getUserId();


        if (registered.remove(id)) {
            System.out.println("Registration cancelled for " + id);


            if (!waitlist.isEmpty()) {
                String nextStudent = waitlist.poll(); // poll() removes and returns the first person in the queue
                registered.add(nextStudent);
                System.out.println("Student " + nextStudent + " promoted from waitlist.");
            }
        } else if (waitlist.remove(id)) {

            System.out.println("Removed from waitlist.");
        } else {

            System.out.println("Student not found in this event.");
        }
    }


    public void updateEvent(String name, String time, String location) {

        if (name != null && !name.isEmpty()) {
            this.name = name;
        }

        if (time != null && !time.isEmpty()) {
            this.time = time;
        }

        if (location != null && !location.isEmpty()) {
            this.location = location;
        }

        System.out.println("Event updated successfully.");
    }


    public String getStudentStatus(Student student) {
        String id = student.getUserId();

        if (registered.contains(id)) {
            return "Registered";
        } else if (waitlist.contains(id)) {
            return "Waitlisted";
        } else {
            return "Not registered";
        }
    }


    public void displayEvent() {
        System.out.println("\nEvent ID: " + eventId);
        System.out.println("Name: " + name);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Location: " + location);
        System.out.println("Registered: " + registered.size());
        System.out.println("Waitlist: " + waitlist.size());
    }

    // getter methods to return the value of each private variable

    public int getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public ArrayList<String> getRegistered() {
        return registered;
    }

    public Queue<String> getWaitlist() {
        return waitlist;
    }
}