package repos;

import enums.UserRole;
import models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserRepo {

    private final Map<UUID, User> users;

    private static UserRepo userRepoInstance = null;

    public static UserRepo getUserRepoInstance() {
        if (userRepoInstance == null) {
            userRepoInstance = new UserRepo();
        }
        return userRepoInstance;
    }

    private UserRepo() {
        users = new HashMap<>();
        User a1 = new User("admin1", "root", "root", UserRole.ADMIN);
        this.users.put(a1.getUserID(), a1);
    }

    public void addNewUser(User user) {
        users.put(user.getUserID(), user);
    }

    public User getUserByID(UUID userID) {
        return users.get(userID);
    }

    public void removeUserByID(UUID userID) {
        users.remove(userID);
    }

    public Map<UUID, User> getAllUsers() {
        return users;
    }

}
