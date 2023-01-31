package student;

import Server.paintpanel;
import whiteboard_remote.iwhiteboard;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClassRoom extends JFrame implements ActionListener{

    private JPanel contentPane;
    private JPanel fileUpload;
    private JTextField textField;
    private paintpanel panel;
    private JList list;
    private JTextArea textArea;
    private String username;
    private iwhiteboard wb;

    //get panel
    public paintpanel getpanel() {
        return panel;
    }
    //get list
    public JList getJlist() {
        return list;
    }
    //get textarea
    public JTextArea gettextArea() {
        return textArea;
    }
    //set username
    public void setUsername(String username) {
        this.username = username;
    }
    //set wb
    public void set_wb(iwhiteboard wb) {
        this.wb = wb;
    }
    //get fileUpload
    public JPanel getFileUpload(){ return fileUpload;}

    public ClassRoom() {
        panel = new paintpanel();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                try {
                    wb.exit(username);
//                    wb.updateUserList(username);
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 880, 520);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBounds(0, 0, 625, 41);
        contentPane.add(toolBar);

        JButton btnRectangle = new JButton("Rectangle");
        btnRectangle.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        toolBar.add(btnRectangle);

        JButton btnOval = new JButton("Oval");
        btnOval.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        toolBar.add(btnOval);

        JButton btnPencil = new JButton("Pencil");
        btnPencil.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        toolBar.add(btnPencil);

        JButton btnLine = new JButton("Line");
        btnLine.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        toolBar.add(btnLine);

        JButton btnCircle = new JButton("Circle");
        btnCircle.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        toolBar.add(btnCircle);

        JButton btnText = new JButton("Text");
        btnText.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        toolBar.add(btnText);

        JButton btnEraser = new JButton("Eraser");
        btnEraser.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        toolBar.add(btnEraser);

        //button_listener
        btnRectangle.addActionListener(this);
        btnOval.addActionListener(this);
        btnPencil.addActionListener(this);
        btnLine.addActionListener(this);
        btnCircle.addActionListener(this);
        btnText.addActionListener(this);
        btnEraser.addActionListener(this);

        //Select : large,medium, small
        JComboBox comboBox = new JComboBox();
        toolBar.add(comboBox);
        comboBox.setFont(new Font("Apple Chancery", Font.PLAIN, 13));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Small", "Medium", "Large"}));
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s=(String)comboBox.getSelectedItem();
                Stroke selectStroke = new BasicStroke(1.0f);
                if(s.contentEquals("Small")) {
                    selectStroke = new BasicStroke(1.0f);
                }
                else if(s.contentEquals("Medium")) {
                    selectStroke = new BasicStroke(2.0f);
                }
                else if(s.contentEquals("Large")) {
                    selectStroke = new BasicStroke(3.0f);
                }
                panel.setStroke(selectStroke);
            }
        });

        JButton btnSel = new JButton("");
        btnSel.setEnabled(false);
        btnSel.setBackground(Color.BLACK);
        btnSel.setBounds(142, 42, 22, 22);
        btnSel.setOpaque(true);
        btnSel.setBorderPainted(false);
        contentPane.add(btnSel);

        JLabel lblSelectedColor = new JLabel("Selected Color:");
        lblSelectedColor.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblSelectedColor.setBounds(10, 48, 120, 16);
        contentPane.add(lblSelectedColor);

        //Button Blue
        JButton btnBlue = new JButton("");
        btnBlue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.BLUE);
                panel.setColor(Color.BLUE);
            }
        });
        btnBlue.setBounds(243, 42, 22, 22);
        btnBlue.setBackground(Color.BLUE);
        btnBlue.setOpaque(true);
        btnBlue.setBorderPainted(false);
        contentPane.add(btnBlue);
        btnBlue.setOpaque(true);
        btnBlue.setBorderPainted(false);
        btnBlue.setOpaque(true);
        btnBlue.setBorderPainted(false);

        //button green
        JButton btnGree = new JButton("");
        btnGree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.GREEN);
                panel.setColor(Color.GREEN);
            }
        });
        btnGree.setBounds(355, 42, 22, 22);
        contentPane.add(btnGree);
        btnGree.setBackground(Color.GREEN);
        btnGree.setOpaque(true);
        btnGree.setBorderPainted(false);

        //button purple
        JButton btnPurp = new JButton("");
        btnPurp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.MAGENTA);
                panel.setColor(Color.MAGENTA);
            }
        });
        btnPurp.setBounds(299, 42, 22, 22);
        contentPane.add(btnPurp);
        btnPurp.setBackground(Color.MAGENTA);
        btnPurp.setOpaque(true);
        btnPurp.setBorderPainted(false);

        //button Others
        JButton btnOthers = new JButton("Others");
        btnOthers.setFont(new Font("Apple Color Emoji", Font.PLAIN, 14));
        btnOthers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color initialColor = null;
                Color selectedColor = JColorChooser.showDialog(null, "Choose a color you like", initialColor);
                System.out.println("");
                btnSel.setBackground(selectedColor);
                btnOthers.setBackground(selectedColor);
                panel.setColor(selectedColor);
            }
        });
        btnOthers.setBounds(467, 42, 92, 22);
        contentPane.add(btnOthers);
        btnOthers.setBackground(Color.LIGHT_GRAY);

        //Button yellow
        JButton btnYelo = new JButton("");
        btnYelo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.YELLOW);
                panel.setColor(Color.YELLOW);

            }
        });
        btnYelo.setBounds(327, 42, 22, 22);
        btnYelo.setBackground(Color.YELLOW);
        btnYelo.setOpaque(true);
        btnYelo.setBorderPainted(false);
        contentPane.add(btnYelo);

        //Button orange
        JButton btnOrag = new JButton("");
        btnOrag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.ORANGE);
                panel.setColor(Color.ORANGE);
            }
        });
        btnOrag.setBounds(271, 42, 22, 22);
        contentPane.add(btnOrag);
        btnOrag.setBackground(Color.ORANGE);
        btnOrag.setOpaque(true);
        btnOrag.setBorderPainted(false);

        //button red
        JButton btnRed = new JButton("");
        btnRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.RED);
                panel.setColor(Color.RED);
            }
        });
        btnRed.setBounds(383, 42, 22, 22);
        contentPane.add(btnRed);
        btnRed.setBackground(Color.RED);
        btnRed.setOpaque(true);
        btnRed.setBorderPainted(false);

        //button white
        JButton button = new JButton("");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.WHITE);
                panel.setColor(Color.WHITE);
            }
        });
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.setBounds(411, 42, 22, 22);
        contentPane.add(button);

        //button Black
        JButton btnBlack = new JButton("");
        btnBlack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("");
                btnSel.setBackground(Color.BLACK);
                panel.setColor(Color.BLACK);
            }
        });
        btnBlack.setBackground(Color.BLACK);
        btnBlack.setOpaque(true);
        btnBlack.setBorderPainted(false);
        btnBlack.setBounds(439, 42, 22, 22);
        contentPane.add(btnBlack);

        //button Palette
        JLabel lblPalette = new JLabel("Palette:");
        lblPalette.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblPalette.setBounds(176, 48, 73, 16);
        contentPane.add(lblPalette);

        //the white Board
        panel.setBounds(10, 74, 615, 550);
        panel.setBackground(Color.white);
        contentPane.add(panel);
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel_1.setBounds(8, 72, 617, 398);
        contentPane.add(panel_1);

        //Online's User
        JLabel lblUsers = new JLabel("Users");
        lblUsers.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblUsers.setBounds(637, 0, 66, 25);
        contentPane.add(lblUsers);

        list = new JList();
        list.setBounds(637, 29, 207, 131);
        list.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        contentPane.add(list);

        //Chatbox
        JLabel lblChatbox = new JLabel("ChatBox");
        lblChatbox.setFont(new Font("Monaco", Font.PLAIN, 14));
        lblChatbox.setBounds(637, 161, 66, 25);
        contentPane.add(lblChatbox);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBounds(611, 187, 300, 207);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(637, 184, 300, 207);
        contentPane.add(scroll);

        //Input to write the messages
        textField = new JTextField();
        textField.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.LIGHT_GRAY));
        textField.setBounds(637, 403, 170, 26);
        contentPane.add(textField);
        textField.setColumns(10);

        //button SEND
        JButton btnSend = new JButton("Send");
        btnSend.setBounds(864, 403, 74, 25);
        contentPane.add(btnSend);
        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String Msg = textField.getText();
                try {
                    if(!Msg.equals("")) {
                        wb.broadcast( username + " : " + Msg);
                    }
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(null, "the manager has left the room", "error", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                    System.exit(0);
                }
                textField.setText(null);;
            }
        });

        //button sendFile
        ImageIcon icon = new ImageIcon("C:\\Users\\dell\\IdeaProjects\\TEST3\\src\\main\\java\\images\\upload.png");
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        JButton btnFile = new JButton();
        btnFile.setIcon(icon);
        btnFile.setToolTipText("upload File");
        btnFile.setBorderPainted(false);
        btnFile.setContentAreaFilled(false);
        btnFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFile.setDefaultCapable(false);
        btnFile.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFile.setMargin(new java.awt.Insets(0, 0, 0, 0));
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt){
                uploadFile(evt);
            }
        });
        btnFile.setBounds(818, 400, 40, 40);
        contentPane.add(btnFile);

        //jpanel of files
        fileUpload = new JPanel();
        fileUpload.setBackground(Color.LIGHT_GRAY);
        fileUpload.setBounds(637, 440, 300, 100);
        contentPane.add(fileUpload);

        this.setTitle("User");
        setVisible(true);

    }

    private void uploadFile(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            String[] extension = file.getName().split("\\.");
            System.out.println(extension.length);
            if(extension[extension.length - 1].equals("txt")||
                    extension[extension.length - 1].equals("java")||
                    extension[extension.length - 1].equals("php")||
                    extension[extension.length - 1].equals("c")||
                    extension[extension.length - 1].equals("cpp")||
                    extension[extension.length - 1].equals("xml")||
                    extension[extension.length - 1].equals("exe")||
                    extension[extension.length - 1].equals("png")||
                    extension[extension.length - 1].equals("jpg")||
                    extension[extension.length - 1].equals("jpeg")||
                    extension[extension.length - 1].equals("pdf")||
                    extension[extension.length - 1].equals("jar")||
                    extension[extension.length - 1].equals("rar")||
                    extension[extension.length - 1].equals("zip")
            ){
                try {
                    ArrayList<Integer> inc;
                    try (FileInputStream in = new FileInputStream(file)) {
                        inc = new ArrayList<>();
                        int c=0;
                        while((c=in.read()) != -1) {
                            inc.add(c);
                        }
                        in.close();
                    }
                    wb.broadcastFile(inc,file.getName());
                } catch (FileNotFoundException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (RemoteException ex) {
                    System.out.println("Error: " + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }

//                JLabel jfile = new JLabel(file.getName() + " Uploaded ...");
//                fileUpload.add(jfile);
//                fileUpload.repaint();
//                fileUpload.revalidate();
                textArea.append(file.getName() + " Uploaded ...");
            }else{
                JOptionPane.showMessageDialog(this,"You can only upload file have an extension like: xml,exe,jpg,png,jpeg,pdf,c,cpp,jar,java,txt,php ","Alert",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
        @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        String str = e.getActionCommand();
        if(str.contentEquals("Line")) {
            panel.setType("line");
        }
        else if(str.contentEquals("Rectangle")) {
            panel.setType("rect");
        }
        else if(str.contentEquals("Circle")) {
            panel.setType("circle");
        }
        else if(str.contentEquals("Pencil")) {
            panel.setType("freedraw");
        }
        else if(str.contentEquals("Oval")) {
            panel.setType("oval");
        }
        else if(str.contentEquals("Eraser")) {
            panel.setType("erase");
        }
        else if(str.contentEquals("Text")) {
            panel.setType("text");
        }
        else if(str.contentEquals("Exit")) {
            System.exit(0);

        }
    }
}
