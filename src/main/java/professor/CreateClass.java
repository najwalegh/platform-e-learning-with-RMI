package professor;

import whiteboard_remote.iClient;
import whiteboard_remote.iwhiteboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class CreateClass extends UnicastRemoteObject implements Serializable, iClient {

    private static final long serialVersionUID = 1L;
    private static final String Class=null ;
    protected String userName;
    protected String hostName;
    protected String serviceName;
    protected String clientServiceName;
    protected classView GUI;
    protected iwhiteboard wb;

    //constructors
    public CreateClass(String username,String IP,String port) throws RemoteException {
        this.userName = username.trim();
        this.hostName  = IP + ":" + port;
        this.serviceName = "whiteboard";
        this.clientServiceName = "Create";
        this.GUI = new classView();
    }

//    public CreateClass() throws RemoteException{
//        super();
//    }


    //receive messages
    @Override
    public void messageFromServer(String message) throws RemoteException {
        GUI.gettextArea().append(message);
    }

    //update jList
    @Override
    public void updateUserList(String[] currentUsers) throws RemoteException {
        GUI.getJlist().setListData(currentUsers);
    }

    @Override
    public boolean judge(String str) throws RemoteException {
        int flag = JOptionPane.showConfirmDialog(null,str + " want to join the classView\n" + "apporve or not?","Judge", JOptionPane.YES_NO_OPTION);
        if(flag == 1) {
            return true;
        }
        else {
            return false;
        }
    }

    //load the picture of whiteBoard
    @Override
    public void load(byte[] b) throws RemoteException {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(b);
            BufferedImage image = ImageIO.read(in);
            GUI.getpanel().load(image);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //reject the request
    @Override
    public void reject(String str) throws RemoteException {
        JOptionPane.showMessageDialog(null, str + "the request has been rejected", "error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    @Override
    public void info(String str) throws RemoteException {
        Thread t = new Thread(()->{
            JOptionPane.showMessageDialog(null, str, "Information", JOptionPane.INFORMATION_MESSAGE);
        });
        t.start();
    }

//    @Override
//    public String getName() {
//        return null;
//    }

    @Override
    public void fileFromServer(ArrayList<Integer> inc, String filename) throws RemoteException {
        {
            JLabel label = new JLabel("<HTML><U><font size=\"4\" color=\"#365899\">" + filename + "</font></U></HTML>");
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        FileOutputStream out;
                        String separator;
                        if (System.getProperty("os.name").startsWith("Linux") || System.getProperty("os.name").startsWith("MacOS"))
                            separator = "/";
                        else separator = "\\";
                        out = new FileOutputStream(System.getProperty("user.home") + separator + filename);
                        String[] extension = filename.split("\\.");
                        for (int i = 0; i < inc.size(); i++) {
                            int cc = inc.get(i);
                            if (extension[extension.length - 1].equals("txt") ||
                                    extension[extension.length - 1].equals("java") ||
                                    extension[extension.length - 1].equals("php") ||
                                    extension[extension.length - 1].equals("c") ||
                                    extension[extension.length - 1].equals("cpp") ||
                                    extension[extension.length - 1].equals("xml")
                            )
                                out.write((char) cc);
                            else {
                                out.write((byte) cc);
                            }
                        }
                        out.flush();
                        out.close();
                        JOptionPane.showMessageDialog(new JFrame(), "your file saved at " + System.getProperty("user.home") + separator + filename, "File Saved", JOptionPane.INFORMATION_MESSAGE);
                    } catch (FileNotFoundException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    } catch (IOException ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }

                /**
                 * Invoked when a mouse button has been pressed on a component.
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void mousePressed(MouseEvent e) {

                }

                /**
                 * Invoked when a mouse button has been released on a component.
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void mouseReleased(MouseEvent e) {

                }

                /**
                 * Invoked when the mouse enters a component.
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void mouseEntered(MouseEvent e) {

                }

                /**
                 * Invoked when the mouse exits a component.
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            GUI.getfileUpload().add(label);
        }
    }

    //cette fonction pour recupere le nom des clients connectes
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
    //function connexion
    public void connexion(){
        try{
            String details[]= {userName,hostName,clientServiceName};
            Naming.rebind("rmi://"+hostName+"/"+clientServiceName,this);
            wb =(iwhiteboard) Naming.lookup("rmi://"+hostName+"/"+serviceName);

            wb.isEmpty(details);
            //save user
            wb.registerListener(details);

            GUI.set_wb(wb);
            GUI.setUsername(userName);
            GUI.getpanel().setwb(wb);
        }catch (RemoteException | NotBoundException e1) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "connection failed", "error", JOptionPane.ERROR_MESSAGE);
            e1.printStackTrace();
            System.exit(0);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Main function

    public static void main(String[] args){
        CreateClass classRoom;
        try{
            classRoom = new CreateClass("nn","localhost","8000");
            classRoom.connexion();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
