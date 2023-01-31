package Server;

import whiteboard_remote.iwhiteboard;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class server {
    public static void main(String args[]) {
        try {
            iwhiteboard wb = new whiteboard();
            int port = 8000;
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("whiteboard", wb);
            System.out.println("the port: "+ port + " \nserver ready");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
