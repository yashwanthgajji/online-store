package server.controllers;

import common.Requests;
import server.models.User;
import server.serviceImpls.UserServiceImpl;
import server.services.UserService;

import java.util.List;
import java.util.UUID;

public class UserController implements MainController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserServiceImpl();
    }

    @Override
    public Object handleRequest(Requests request, String[] args) {
        switch (request) {
            case Get_UserName: {
                return userService.getUserName(UUID.fromString(args[0]));
            }
            case Get_User_Role: {
                return userService.getUserRole(UUID.fromString(args[0]));
            }
            case Add_New_Admin: {
                User admin = new User(args[0], args[1], args[2]);
                userService.addNewAdmin(admin);
                break;
            }
            case View_All_Customers: {
                List<User> customers = userService.getAllCustomers();
                StringBuilder sb = new StringBuilder();
                sb.append("********* CUSTOMERS *********\n");
                sb.append("S.NO")
                        .append("\t").append("Customer ID")
                        .append("\t").append("Customer Name")
                        .append("\t").append("Email")
                        .append("\n");
                int i=1;
                for (User customer : customers) {
                    sb.append(i)
                            .append("\t").append(customer.getUserID())
                            .append("\t").append(customer.getName())
                            .append("\t").append(customer.getEmail())
                            .append("\n");
                    i++;
                }
                return sb.toString();
            }
            case Add_New_Customer: {
                User customer = new User(args[0], args[1], args[2]);
                userService.addNewCustomer(customer);
                break;
            }
            case Remove_Customer: {
                userService.removeCustomer(UUID.fromString(args[0]));
                break;
            }
        }
        return null;
    }
}
