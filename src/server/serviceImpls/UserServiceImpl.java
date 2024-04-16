package server.serviceImpls;

import server.enums.UserRole;
import server.models.User;
import server.repos.UserRepo;
import server.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepo = UserRepo.getUserRepoInstance();

    public UserServiceImpl() {
    }

    @Override
    public void registerNewCustomer(User user) {
        addNewCustomer(user);
    }

    @Override
    public UUID login(String email, String password) {
        Map<UUID, User> allUsers = userRepo.getAllUsers();
        for (Map.Entry<UUID, User> userEntry: allUsers.entrySet()) {
            User user = userEntry.getValue();
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user.getUserID();
            }
        }
        return null;
    }

    @Override
    public void addNewAdmin(User user) {
        user.setRole(UserRole.ADMIN);
        userRepo.addNewUser(user);
    }

    @Override
    public void addNewCustomer(User user) {
        user.setRole(UserRole.CUSTOMER);
        userRepo.addNewUser(user);
    }

    @Override
    public void removeCustomer(UUID userID) {
        userRepo.removeUserByID(userID);
    }

    @Override
    public boolean isUserAdmin(UUID userID) {
        return userRepo.getUserByID(userID).getRole() == UserRole.ADMIN;
    }

    @Override
    public List<User> getAllCustomers() {
        List<User> customerList = new ArrayList<>();
        Map<UUID, User> allUsers = userRepo.getAllUsers();
        for (Map.Entry<UUID, User> userEntry: allUsers.entrySet()) {
            User user = userEntry.getValue();
            if (user.getRole() == UserRole.CUSTOMER) {
                customerList.add(user);
            }
        }
        return customerList;
    }

    @Override
    public String getUserName(UUID userID) {
        return userRepo.getUserByID(userID).getName();
    }
}
