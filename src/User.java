public class User {
    protected String userId;
    protected String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    // Getter method so that other classes can read the user's ID without changing it
    public String getUserId() {
        return userId;
    }

    // Getter method so that other classes can read the user's name without changing it
    public String getName() {
        return name;
    }
}