package com.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * UserHandlerImpl.
 *
 * @author liguoyao
 */
public class UserHandlerImpl extends UnicastRemoteObject implements UserHandler {
    // 该构造期必须存在，因为集继承了UnicastRemoteObject类，其构造器要抛出RemoteException
    public UserHandlerImpl() throws RemoteException {
        super();
    }

    @Override public String getUserName(int id) throws RemoteException {
        return "lmy86263";
    }

    @Override public int getUserCount() throws RemoteException {
        return 1;
    }

    @Override public User getUserByName(String name) throws RemoteException {
        return new User("lmy86263", 1);
    }
}
