package common.services;

import common.models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface UserService extends Remote {
    public void registerNewCustomer(User user) throws RemoteException;
    public UUID login(String email, String password) throws RemoteException;
    public void addNewAdmin(User user) throws RemoteException;
    public void addNewCustomer(User user) throws RemoteException;
    public void removeCustomer(UUID userID) throws RemoteException;
    public boolean isUserAdmin(UUID userID) throws RemoteException;
    public List<User> getAllCustomers() throws RemoteException;
    public String getUserName(UUID userID) throws RemoteException;
}
