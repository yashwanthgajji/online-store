package server.controllers;

import common.Requests;

public interface MainController {
    public Object handleRequest(Requests request, String[] args);
}
