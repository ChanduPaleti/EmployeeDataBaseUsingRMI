package com.classnames;

import java.rmi.Remote;

import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote {

 public default String[][] getEmployee() throws RemoteException {
  return new String[0][];
 }



 public String addEmployee(String[] userQuery) throws RemoteException;

public String updateEmployee (String[] userQuery) throws RemoteException; 

public String deleteEmployee(String[] userQuery) throws RemoteException;

 public default String[] Search(String[] userQuery) throws RemoteException {
  return new String[0];
 }

}