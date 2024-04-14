package server.repos;

import common.enums.UserRole;
import server.models.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
        users = new ConcurrentHashMap<>();
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
