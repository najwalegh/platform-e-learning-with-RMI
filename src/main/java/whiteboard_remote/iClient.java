package whiteboard_remote;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

public interface iClient extends Remote{
    public void messageFromServer(String message) throws RemoteException;
    public void updateUserList(String[] currentUsers) throws RemoteException;
    public boolean judge(String str) throws RemoteException;

    //pour lire une image
    public void load(byte[] b) throws RemoteException;
    public void reject(String str)throws RemoteException;
    public void info(String str) throws RemoteException;

    ////////////////
//    String getName();
    public void fileFromServer(ArrayList<Integer> inc, String message) throws RemoteException;
//    Vector<String> getListClientByName(String name) throws RemoteException;


}
