package com.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server.
 *
 * @author liguoyao
 */
public class Server {
    public static void main(String args[]) {
        UserHandler userHandler;
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(1099);
            userHandler = new UserHandlerImpl();
            registry.bind("user", userHandler);
            System.out.println(" rmi server is ready ...");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
