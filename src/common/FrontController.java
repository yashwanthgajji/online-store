package common;

import common.enums.Requests;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrontController extends Remote {

    public String handleRequest(Requests request, String[] args) throws RemoteException;
}
