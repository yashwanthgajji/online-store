package server.serviceImpls;

import common.enums.UserRole;
import common.models.User;
import server.repos.UserRepo;
import common.services.UserService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private final UserRepo userRepo = UserRepo.getUserRepoInstance();

    public UserServiceImpl() throws RemoteException {
    }

    @Override
    public void registerNewCustomer(User user) throws RemoteException {
        addNewCustomer(user);
    }

    @Override
    public UUID login(String email, String password) throws RemoteException {
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
    public void addNewAdmin(User user) throws RemoteException {
        user.setRole(UserRole.ADMIN);
        userRepo.addNewUser(user);
    }

    @Override
    public void addNewCustomer(User user) throws RemoteException {
        user.setRole(UserRole.CUSTOMER);
        userRepo.addNewUser(user);
    }

    @Override
    public void removeCustomer(UUID userID) throws RemoteException {
        userRepo.removeUserByID(userID);
    }

    @Override
    public boolean isUserAdmin(UUID userID) throws RemoteException {
        return userRepo.getUserByID(userID).getRole() == UserRole.ADMIN;
    }

    @Override
    public List<User> getAllCustomers() throws RemoteException {
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
    public String getUserName(UUID userID) throws RemoteException {
        return userRepo.getUserByID(userID).getName();
    }
}
