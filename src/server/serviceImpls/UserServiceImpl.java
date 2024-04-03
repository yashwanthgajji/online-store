package server.serviceImpls;

import common.enums.UserRole;
import common.models.User;
import server.repos.UserRepo;
import common.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private UserRepo userRepo = new UserRepo();

    public UserServiceImpl() throws RemoteException {
    }

    @Override
    public String registerNewCustomer(String name, String email, String password) throws RemoteException {
        User user = new User(name, email, password);
        user.setRole(UserRole.CUSTOMER);
        userRepo.addNewUser(user);
        return user.getUserID().toString();
    }

    @Override
    public String login(String email, String password) throws RemoteException {
        ArrayList<User> allUsers = userRepo.getAllUsers();
        for (User user: allUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user.getUserID().toString();
            }
        }
        return null;
    }

    @Override
    public void addNewAdmin(String name, String email, String password) throws RemoteException {
        User user = new User(name, email, password);
        user.setRole(UserRole.ADMIN);
        userRepo.addNewUser(user);
    }

    @Override
    public void addNewCustomer(String name, String email, String password) throws RemoteException {
        User user = new User(name, email, password);
        user.setRole(UserRole.CUSTOMER);
        userRepo.addNewUser(user);
    }

    @Override
    public void removeCustomer(String userID) throws RemoteException{
        userRepo.removeUserByID(UUID.fromString(userID));
    }

    @Override
    public boolean isUserAdmin(String userID) throws RemoteException {
        return userRepo.getUserByID(UUID.fromString(userID)).getRole() == UserRole.ADMIN;
    }

    @Override
    public String getAllCustomers() throws RemoteException {
        ArrayList<User> customerList = new ArrayList<>();
        ArrayList<User> allUsers = userRepo.getAllUsers();
        for (User user: allUsers) {
            if (user.getRole() == UserRole.CUSTOMER) {
                customerList.add(user);
            }
        }
        System.out.println("********* CUSTOMERS *********");
        StringBuilder sb = new StringBuilder();
        sb.append("S.NO")
                .append("\t").append("Customer ID")
                .append("\t").append("Customer Name")
                .append("\t").append("Email")
                .append("\n");
        int i=1;
        for (User customer : customerList) {
            sb.append(i)
                    .append("\t").append(customer.getUserID())
                    .append("\t").append(customer.getName())
                    .append("\t").append(customer.getEmail())
                    .append("\n");
            i++;
        }
        return sb.toString();
    }

    @Override
    public String getUserName(String userID) {
        return userRepo.getUserByID(UUID.fromString(userID)).getName();
    }
}
