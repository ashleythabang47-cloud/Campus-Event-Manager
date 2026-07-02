import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class EventManager {


    private ArrayList<Event> events;
    // Constructor that starts the events list
    public EventManager() {
        events = new ArrayList<>();
    }


    public void addEvent(Event event) {

        for (Event e : events) {
            if (e.getEventId() == event.getEventId()) {
                System.out.println("Event ID already exists.");
                return;
            }
        }

        events.add(event);
        System.out.println("Event added successfully.");
    }


    public Event findEvent(int id) {

        for (Event e : events) {
            if (e.getEventId() == id) {
                return e;
            }
        }

        return null;
    }


    public void viewAllEvents() {


        if (events.size() == 0) {
            System.out.println("No events available.");
            return;
        }


        for (Event e : events) {
            e.displayEvent();
        }
    }

    public void cancelEvent(int id) {


        Event event = findEvent(id);

        if (event != null) {
            events.remove(event);
            System.out.println("Event cancelled successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }


    public void saveToFile() {

        try {
            // Open the file for writing
            FileWriter writer = new FileWriter("events.txt");

            for (Event e : events) {

                String registeredData = "";
                for (int i = 0; i < e.getRegistered().size(); i++) {
                    registeredData += e.getRegistered().get(i);
                    if (i < e.getRegistered().size() - 1) {


                        registeredData += "|";
                    }
                }




                String waitlistData = "";

                Object[] waitlistArray = e.getWaitlist().toArray(); // convert queue to array to loop it
                for (int i = 0; i < waitlistArray.length; i++) {
                    waitlistData += waitlistArray[i];
                    if (i < waitlistArray.length - 1) {
                        waitlistData += "|";
                    }
                }

                writer.write(
                        e.getEventId() + "," +
                                e.getName() + "," +
                                e.getDate() + "," +
                                e.getTime() + "," +
                                e.getLocation() + "," +
                                e.getMaxParticipants() + "," +
                                registeredData + "," +
                                waitlistData + "\n"
                );
            }

            writer.close();
            System.out.println("Data saved.");

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }

    public void sortByName() {
        if (events.size() == 0) {
            System.out.println("No events to sort.");
            return;
        }
        // compare each event's name to sort them alphabetically
        Collections.sort(events, Comparator.comparing(e -> e.getName().toLowerCase()));
        System.out.println("Events sorted by name.");
    }


    public void sortByDate() {
        if (events.size() == 0) {
            System.out.println("No events to sort.");
            return;
        }
        Collections.sort(events, (a, b) -> {
            try {
                // split date into day, month, year parts to compare properly
                String[] partsA = a.getDate().split("/");
                String[] partsB = b.getDate().split("/");

                int yearA = Integer.parseInt(partsA[2]);
                int yearB = Integer.parseInt(partsB[2]);
                if (yearA != yearB) return yearA - yearB;

                int monthA = Integer.parseInt(partsA[1]);
                int monthB = Integer.parseInt(partsB[1]);
                if (monthA != monthB) return monthA - monthB;

                int dayA = Integer.parseInt(partsA[0]);
                int dayB = Integer.parseInt(partsB[0]);
                return dayA - dayB;

            } catch (Exception e) {
                return 0;
            }
        });
        System.out.println("Events sorted by date.");
    }


    public void searchEvents(String keyword, boolean searchByDate) {
        boolean found = false;

        for (Event e : events) {

            if (searchByDate) {
                if (e.getDate().equals(keyword)) {
                    e.displayEvent();
                    found = true;
                }
            } else {

                if (e.getName().toLowerCase().contains(keyword.toLowerCase())) {
                    e.displayEvent();
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No events found matching: " + keyword);
        }
    }


    public void loadFromFile() {

        try {
            File file = new File("events.txt");

            if (!file.exists()) return;

            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {

                String line = reader.nextLine();
                String[] data = line.split(",", -1);

                if (data.length < 6) continue;


                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String date = data[2];
                String time = data[3];
                String location = data[4];
                int max = Integer.parseInt(data[5]);

                // Recreate the Event object using the loaded data
                Event e = new Event(id, name, date, time, location, max);

                if (data.length >= 7 && !data[6].isEmpty()) {
                    String[] registeredIds = data[6].split("\\|"); // split by "|" to get individual IDs
                    for (String studentId : registeredIds) {
                        e.getRegistered().add(studentId);
                    }
                }

                if (data.length >= 8 && !data[7].isEmpty()) {
                    String[] waitlistIds = data[7].split("\\|");
                    for (String studentId : waitlistIds) {
                        e.getWaitlist().add(studentId);
                    }
                }


                events.add(e);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}