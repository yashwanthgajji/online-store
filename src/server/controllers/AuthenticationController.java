package server.controllers;

import common.Requests;
import server.models.User;
import server.serviceImpls.UserServiceImpl;
import server.services.UserService;

import java.util.UUID;

public class AuthenticationController implements MainController {
    private final UserService userService;

    public AuthenticationController() {
        userService = new UserServiceImpl();
    }
    @Override
    public Object handleRequest(Requests request, String[] args) {
        String res = "Unknown Request";
        switch (request) {
            case Register_New_Customer: {
                User user = new User(args[0], args[1], args[2]);
                userService.registerNewCustomer(user);
                res = "Registration Successful...";
                break;
            }
            case Login: {
                UUID userID = userService.login(args[0], args[1]);
                if (userID ==  null) {
                    res = "Invalid Username/Password";
                } else {
                    res = userID.toString();
                }
                break;
            }
        }
        return res;
    }
}
