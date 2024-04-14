package server;

import common.FrontController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FrontControllerImpl extends UnicastRemoteObject implements FrontController {
    protected FrontControllerImpl() throws RemoteException {
        super();
    }

    @Override
    public String handleRequest(String request) throws RemoteException {
        return null;
    }
}
