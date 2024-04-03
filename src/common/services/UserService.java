package common.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {
    public String registerNewCustomer(String name, String email, String password) throws RemoteException;
    public String login(String email, String password) throws RemoteException;
    public void addNewAdmin(String name, String email, String password) throws RemoteException;
    public void addNewCustomer(String name, String email, String password) throws RemoteException;
    public void removeCustomer(String userID) throws RemoteException;
    public boolean isUserAdmin(String userID) throws RemoteException;
    public String getAllCustomers() throws RemoteException;
    public String getUserName(String userID) throws RemoteException;
}
