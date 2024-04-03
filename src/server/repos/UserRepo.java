package server.repos;

import common.enums.UserRole;
import common.models.User;

import java.util.ArrayList;
import java.util.UUID;

public class UserRepo {

    private ArrayList<User> users;

//    private static UserRepo userRepoInstance = null;
//
//    public static UserRepo getUserRepoInstance() {
//        if (userRepoInstance == null) {
//            userRepoInstance = new UserRepo();
//        }
//        return userRepoInstance;
//    }

    public UserRepo() {
        users = new ArrayList<>();
        User a1 = new User("admin1", "root", "root", UserRole.ADMIN);
        this.users.add(a1);
    }

    public void addNewUser(User user) {
        users.add(user);
    }

    public User getUserByID(UUID userID) {
        for (User user: users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    public void removeUserByID(UUID userID) {
        User user = getUserByID(userID);
        if (user != null) {
            users.remove(user);
        }
    }

    public ArrayList<User> getAllUsers() {
        return users;
    }

}
