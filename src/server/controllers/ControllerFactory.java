package server.controllers;

public class ControllerFactory {
    public static MainController createController(ControllerType controllerType) {
        switch (controllerType) {
            case AuthenticationController -> {
                return new AuthenticationController();
            }
            case UserController -> {
                return new UserController();
            }
        }
        return null;
    }
}
