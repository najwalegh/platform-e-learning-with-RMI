package Server;

import whiteboard_remote.iClient;
import whiteboard_remote.iwhiteboard;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;

public class whiteboard extends UnicastRemoteObject implements iwhiteboard {
    private ArrayList<user> users;
    private byte[] b;

    protected whiteboard() throws RemoteException {
        super();
        users = new ArrayList<user>();
    }

    @Override
    public void draw(byte[] b) throws RemoteException {
        this.b=b;
        for (user u:users){
            try{
                u.getClient().load(b);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //Vérifiez si le tableau est vide
    @Override
    public boolean check() throws RemoteException {
        if(users.size()==0)
            return false;
        else
            return true;
    }

    @Override
    public synchronized void registerListener(String[] details) throws RemoteException {
        System.out.println(details[0] + " has joined the chat session");
        System.out.println(details[0] + "'s hostname : " + details[1]);
        System.out.println(details[0] + "'sRMI service : " + details[2]);
        try{
            iClient client = (iClient)Naming.lookup("rmi://" + details[1] + "/" + details[2]);
            users.add(new user(details[0], client));

            if (users.size()>1){
                //déterminer s'il faut laisser l'utilisateur rejoindre la pièce
//                if (judge(users.get(users.size()-1).getName())){
                if (judge(details[0])){
                    users.remove(users.size()-1);
                    //pour retirer l'utilisateur de la salle
                    client.reject("The professor does not accept your request\n");
                }
            }

            if (b!=null){
                //synchroniser le tableau with the new user
                client.load(b);
            }

            client.messageFromServer("[Server] : WELCOME "+details[0]+" .\n");
            broadcast("[Server] : "+details[0]+" has joined the course.");
            updateUserList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Juger si les nouveaux utilisateurs sont autorisés à se joindre
    @Override
    public boolean judge(String str) throws RemoteException {
        return users.get(0).getClient().judge(str);
    }

    @Override
    public void removeUser(String username) throws RemoteException {
        int index = 0;
        for(int i = 0;i < users.size();i++) {
            if(users.get(i).getName().equals(username)) {
                index = i;
            }
        }
        if(index == 0) {
            users.get(0).getClient().info("Do not have this username");
            //users.get(0).getClient().messageFromServer("Do not have this username");
        }else{
            iClient temp=users.get(index).getClient();
            String name=users.get(index).getName();
            users.remove(index);
            users.get(0).getClient().info("removed successfully !");
            broadcast("<Server> : "+name+"has been removed!\n");
            updateUserList();

            Thread thread=new Thread(()->{
                try{
                    temp.reject("Sorry, You have been removed by the Professor !\n");
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
             thread.start();
        }


    }

    //Diffusion de messages (send to all)
    @Override
    public void broadcast(String msg) throws RemoteException {
        for(user u : users){
            try {
               //recevoir des messages d'autres utilisateurs dans la salle de discussion.
                u.getClient().messageFromServer(msg + "\n");
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    //Diffusion de fichiers (send to all)

    public void broadcastFile(ArrayList<Integer> inc,String filename) throws RemoteException {
        try (FileInputStream in = new FileInputStream(filename)) {
            inc = new ArrayList<>();
            int c=0;
            while((c=in.read()) != -1) {
                inc.add(c);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(user u : users){
            try {
                //recevoir des messages d'autres utilisateurs dans la salle de discussion.
                u.getClient().fileFromServer(inc,filename);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void isEmpty(String[] details) throws RemoteException {
        if(users.size()>0){
            try {
                iClient client= (iClient) Naming.lookup("rmi://"+details[1]+"/"+details[2]);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //si quelqu'un veut entrer avec un username deja defini
    @Override
    public void isSameName(String[] details) throws RemoteException {
        try {
            iClient client= (iClient) Naming.lookup("rmi://"+details[1]+"/"+details[2]);
            for(user u : users){
                if(u.getName().equals(details[0])) {
                    client.reject("the username has been taken\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //user exit the board
    @Override
    public void exit(String username) throws RemoteException {
    int index = 0;
        for(user u : users){
            if (u.getName().equals(username))
                index= users.indexOf(u);
        }
        
        users.remove(index);
        broadcast("[Server] :" + username + " has exitd!\n");
        updateUserList();
    }

    private void updateUserList() {
        //get the list
        String[] currentUsers = new String[users.size()];
        for(int i = 0; i< currentUsers.length; i++){
            currentUsers[i] = users.get(i).getName();
        }
        //update the list
        for(user c : users){
            try {
                c.getClient().updateUserList(currentUsers);
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    //end the board
    @Override
    public void end() throws RemoteException {
        System.exit(0);
    }

    /////////
    //get the list of users connected except me
    //
//    @Override
//    public synchronized Vector<String> getListClientByName(String name) throws RemoteException {
//        Vector<String> list = new Vector<>();
//        for (user client : users) {
//            if(!client.getName().equals(name)){
//                list.add(client.getName());
//            }
//        }
//        return list;
//    }
}
