package lk.sliit.qreporter;

public class User {
    String name;
    String Email;
    String UserId;

    public User(String name, String email, String userId) {
        this.name = name;
        Email = email;
        UserId = userId;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
