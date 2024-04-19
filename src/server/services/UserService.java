package server.services;

import server.auth.UserRole;
import server.models.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public void registerNewCustomer(User user);
    public UUID login(String email, String password);
    public void addNewAdmin(User user);
    public void addNewCustomer(User user);
    public void removeCustomer(UUID userID);
    public UserRole getUserRole(UUID userID);
    public List<User> getAllCustomers();
    public String getUserName(UUID userID);
}
