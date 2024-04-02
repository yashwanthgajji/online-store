package common.services;

import common.models.User;

import java.rmi.Remote;
import java.util.List;
import java.util.UUID;

public interface UserService extends Remote {
    public void registerNewCustomer(User user);
    public UUID login(String email, String password);
    public void addNewAdmin(User user);
    public void addNewCustomer(User user);
    public void removeCustomer(UUID userID);
    public boolean isUserAdmin(UUID userID);
    public List<User> getAllCustomers();
    public String getUserName(UUID userID);
}
