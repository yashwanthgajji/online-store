package server.controllers;

import common.Requests;
import server.models.User;
import server.serviceImpls.UserServiceImpl;
import server.services.UserService;

public class AuthenticationController implements MainController {
    private final UserService userService;

    public AuthenticationController() {
        userService = new UserServiceImpl();
    }
    @Override
    public Object handleRequest(Requests request, String[] args) {
        String res = "Unknown Request";
        switch (request) {
            case Register_New_Customer -> {
                User user = new User(args[0], args[1], args[2]);
                userService.registerNewCustomer(user);
                return user.getUserID().toString();
            }
            case Login -> {
                return userService.login(args[0], args[1]).toString();
            }
        }
        return res;
    }
}
