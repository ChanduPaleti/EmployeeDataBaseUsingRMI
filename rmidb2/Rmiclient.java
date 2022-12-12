package com.classnames;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class Rmiclient {
 static RmiServerIntf obj;

 public static void main(String args[]) throws Exception {
  obj = (RmiServerIntf) Naming.lookup("rmi://localhost:6666/classnames");
  new ClientGUI();
 }

  static class Search implements ActionListener {
  JFrame frame;
  JLabel id1,id,salary,department,email,name,address;
  JTextField tid1,temail,tdepartment,tid,tname,tsalary,taddress;
  JButton search;


  Search() {
   frame = new JFrame("SEARCH");
   frame.setLayout(null);
   frame.setSize(600, 600);
   frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
   id1 = new JLabel("ID ");
   id1.setBounds(50, 30, 100, 40);
   frame.add(id1);
   tid1 = new JTextField();
   tid1.setBounds(180, 30, 140, 35);
   frame.add(tid1);

   search = new JButton("search");
   search.setBounds(150, 100, 100, 40);
   search.addActionListener(this);
   frame.add(search);
   id = new JLabel("ID ");
   id.setBounds(50, 200, 100, 40);
   frame.add(id);
   tid = new JTextField();
   tid.setBounds(180, 200, 140, 35);
   frame.add(tid);
   name = new JLabel("NAME ");
   name.setBounds(50, 250, 100, 40);
   frame.add(name);
   tname = new JTextField();
   tname.setBounds(180, 250, 140, 35);
   frame.add(tname);
   salary = new JLabel("SALARY ");
   salary.setBounds(50, 300, 100, 40);
   frame.add(salary);
   tsalary = new JTextField();
   tsalary.setBounds(180, 300, 140, 35);
   frame.add(tsalary);
   department = new JLabel("Department");
   department.setBounds(50, 350, 100, 40);
   frame.add(department);
   tdepartment = new JTextField();
   tdepartment.setBounds(180, 350, 140, 35);
   frame.add(tdepartment);
   address = new JLabel("Address ");
   address.setBounds(50, 400, 100, 40);
   frame.add(address);
   taddress = new JTextField();
   taddress.setBounds(180, 400, 140, 35);
   frame.add(taddress);
   email = new JLabel("Email ");
   email.setBounds(50, 450, 100, 40);
   frame.add(email);
   temail = new JTextField();
   temail.setBounds(180, 450, 140, 35);
   frame.add(temail);
   taddress.setEditable(false);
   temail.setEditable(false);
   tid.setEditable(false);
   tsalary.setEditable(false);
   tdepartment.setEditable(false);
   tname.setEditable(false);

   frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
   String[] s1;
   if (e.getSource() == search) {
    String[] dlt;
    dlt = new String[1];
    dlt[0] = tid1.getText();
    try {
    s1=Rmiclient.obj.Search(dlt);
    tid.setText(s1[0]);
    tname.setText(s1[1]);
    tsalary.setText(s1[2]);
    tdepartment.setText(s1[3]);
    taddress.setText(s1[4]);
    temail.setText(s1[5]);


    } catch (RemoteException ex) {
     ex.printStackTrace();
    }

   }
  }
 }

 static class Add implements ActionListener {
  JFrame frame;
  JLabel id, name, salary,department,address,email,l3;
  JTextField tid, tname, tsalary,tdepartment,taddress,temail;
  JButton submit;

  Add() {
   frame = new JFrame("INSERT");
   frame.setLayout(null);
   frame.setSize(600, 600);
   frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
   id = new JLabel("ID ");
   id.setBounds(50, 30, 100, 40);
   frame.add(id);
   tid = new JTextField();
   tid.setBounds(180, 30, 140, 35);
   frame.add(tid);
   name = new JLabel("NAME ");
   name.setBounds(50, 80, 100, 40);
   frame.add(name);
   tname = new JTextField();
   tname.setBounds(180, 80, 140, 35);
   frame.add(tname);
   salary = new JLabel("SALARY ");
   salary.setBounds(50, 130, 100, 40);
   frame.add(salary);
   tsalary = new JTextField();
   tsalary.setBounds(180, 130, 140, 35);
   frame.add(tsalary);
   department = new JLabel("Department");
   department.setBounds(50, 180, 100, 40);
   frame.add(department);
   tdepartment = new JTextField();
   tdepartment.setBounds(180, 180, 140, 35);
   frame.add(tdepartment);
   address = new JLabel("Address ");
   address.setBounds(50, 230, 100, 40);
   frame.add(address);
   taddress = new JTextField();
   taddress.setBounds(180, 230, 140, 35);
   frame.add(taddress);
   email = new JLabel("Email ");
   email.setBounds(50, 280, 100, 40);
   frame.add(email);
   temail = new JTextField();
   temail.setBounds(180, 280, 140, 35);
   frame.add(temail);

   submit = new JButton("SUBMIT");
   submit.setBounds(360, 155, 100, 40);
   submit.addActionListener(this);
   frame.add(submit);
   l3=new JLabel("Waiting for Validation from Server");
   l3.setBounds(200,400,240,35);
   frame.add(l3);
   frame.setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
   if (e.getSource() == submit) {
    String[] addNew;
    addNew = new String[6];
    addNew[0] = tid.getText();
    addNew[1] = tname.getText();
    addNew[2] = tsalary.getText();
    addNew[3]=tdepartment.getText();
    addNew[4]=taddress.getText();
    addNew[5]=temail.getText();
    System.out.println(addNew[0]+" "+addNew[1]+" "+addNew[2]+" "+addNew[3]+" "+addNew[4]+" "+addNew[5]+" ");
    l3.setText("Waiting for validation from Server");
    try {
     l3.setText(Rmiclient.obj.addEmployee(addNew));
    } catch (RemoteException ex) {
     ex.printStackTrace();
    }

   }
  }
 }

 static class Delete implements ActionListener {
  JFrame frame;
  JLabel id,l3;
  JTextField tid;
  JButton delete;

  Delete() {
   frame = new JFrame("DELETE");
   frame.setLayout(null);
   frame.setSize(500, 500);
   frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
   id = new JLabel("ID ");
   id.setBounds(50, 30, 100, 40);
   frame.add(id);
   tid = new JTextField();
   tid.setBounds(180, 30, 140, 35);
   frame.add(tid);

   delete = new JButton("delete");
   delete.setBounds(200, 197, 100, 40);
   delete.addActionListener(this);
   frame.add(delete);
   l3=new JLabel("Waiting for validation from server");
   l3.setBounds(100,350,200,40);
   frame.add(l3);
   frame.setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
   if (e.getSource() == delete) {
    String[] dlt;

    dlt = new String[1];
    dlt[0] = tid.getText();
    l3.setText("Waiting for validation from server");
    try {
     l3.setText(Rmiclient.obj.deleteEmployee(dlt));
    } catch (RemoteException ex) {
     ex.printStackTrace();
    }

   }
  }
 }

 static class Update implements ActionListener {
  JFrame frame;
  JLabel id, name, salary,department,email,address;
  JTextField tid, tname, tsalary,tdepartment,taddress,temail;
  JButton update;

  Update() {
   frame = new JFrame("UPDATE");
   frame.setLayout(null);
   frame.setSize(600, 600);
   frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
   id = new JLabel("ID ");
   id.setBounds(50, 30, 100, 40);
   frame.add(id);
   tid = new JTextField();
   tid.setBounds(180, 30, 140, 35);
   frame.add(tid);
   name = new JLabel("NAME ");
   name.setBounds(50, 80, 100, 40);
   frame.add(name);
   tname = new JTextField();
   tname.setBounds(180, 80, 140, 35);
   frame.add(tname);
   salary = new JLabel("SALARY ");
   salary.setBounds(50, 135, 100, 40);
   frame.add(salary);
   tsalary = new JTextField();
   tsalary.setBounds(180, 135, 140, 35);
   frame.add(tsalary);
   department = new JLabel("Department");
   department.setBounds(50, 180, 100, 40);
   frame.add(department);
   tdepartment = new JTextField();
   tdepartment.setBounds(180, 180, 140, 35);
   frame.add(tdepartment);
   address = new JLabel("Address ");
   address.setBounds(50, 230, 100, 40);
   frame.add(address);
   taddress = new JTextField();
   taddress.setBounds(180, 230, 140, 35);
   frame.add(taddress);
   email = new JLabel("Email ");
   email.setBounds(50, 280, 100, 40);
   frame.add(email);
   temail = new JTextField();
   temail.setBounds(180, 280, 140, 35);
   frame.add(temail);


   update = new JButton("SUBMIT");
   update.setBounds(360, 197, 100, 40);
   update.addActionListener(this);
   frame.add(update);
   frame.setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
   if (e.getSource() == update) {
    String[] addNew;
    addNew = new String[6];
    addNew[0] = tid.getText();
    addNew[1] = tname.getText();
    addNew[2] = tsalary.getText();
    addNew[3] = tdepartment.getText();
    addNew[4] = taddress.getText();
    addNew[5] = temail.getText();
    System.out.println(addNew);
    try {
     Rmiclient.obj.updateEmployee(addNew);
    } catch (RemoteException ex) {
     ex.printStackTrace();
    }

   }
  }
 }

 static class Display {
  JFrame f;
  JTable j;
  Display() throws RemoteException {
   String[][] data = null;
   try {
    data = Rmiclient.obj.getEmployee();
   } catch (Exception e) {
   }

   f = new JFrame();
   f.setTitle("DISPLAY");
   String[] columnNames = { "ID", "Name", "Salary","Department","Address","Email"};

   j = new JTable(data, columnNames);
   j.setBounds(30, 40, 200, 300);
   JScrollPane sp = new JScrollPane(j);
   f.add(sp);
   f.setSize(900, 600);

   f.setVisible(true);

  }
 }


 static class ClientGUI<connection> implements ActionListener {

  JFrame frame;
  JButton add, delete, update, display,search;
  JLabel l1;
  JTextArea chatArea;
  JButton jButton1,jButton2;
  JTextField jTextField1;
  String message="";
  private Socket connection;
  private ObjectOutputStream output;
  private ObjectInputStream input;
  // for client using TCP communication
  private int port = 8888;


  ClientGUI() {
   frame = new JFrame("ClientGUI");
   frame.setLayout(null);
   frame.setSize(650, 650);
   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   l1=new JLabel("        WELCOME TO EMPLOYEE  DATABASE MANAGEMENT SYSTEM       ");
   l1.setFont(new Font("Arial", Font.PLAIN, 13));
   l1.setForeground(Color.BLUE);
   l1.setBounds(30,10,500,60);
   Border blackline = BorderFactory.createLineBorder(Color.black,3);
   l1.setBorder(blackline);
   frame.add(l1);
   add = new JButton("ADD");
   add.setBounds(140, 90, 100, 40);
   frame.add(add);
   add.addActionListener(this);
   delete = new JButton("DELETE");
   delete.setBounds(250, 90, 100, 40);
   delete.addActionListener(this);
   frame.add(delete);
   update = new JButton("UPDATE");
   update.setBounds(360, 90, 100, 40);
   update.addActionListener(this);
   frame.add(update);
   display = new JButton("DISPLAY");
   display.setBounds(200, 150, 100, 40);
   display.addActionListener(this);
   frame.add(display);
   search = new JButton("SEARCH");
   search.setBounds(310, 150, 100, 40);
   search.addActionListener(this);
   frame.add(search);
   chatArea =new JTextArea();
   chatArea.setBounds(100,250,400,300);
   frame.add(chatArea);
   Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
   chatArea.setBorder(border);
   chatArea.setEditable(false);
   try {
    connection = new Socket("localhost", port);
    output = new ObjectOutputStream(connection.getOutputStream());
    output.flush();
    input = new ObjectInputStream(connection.getInputStream());
   }catch (Exception e){};
   jTextField1 =new JTextField();
   jTextField1.setBounds(100,560,400,30);
   jTextField1.setEditable(true);
   frame.add(jTextField1);
   jButton1=new JButton("SEND");
   jButton1.setBounds(550,560,80,30);
   frame.add(jButton1);
   jButton1.addActionListener(this);
   jButton2=new JButton("CHAT");
   jButton2.setBounds(333,200,80,30);
   //frame.add(jButton2);
   jButton2.addActionListener(this);
   frame.setVisible(true);
   frame.setLocationRelativeTo(null);
   try {
    whileChatting();
   } catch (IOException e) {
    e.printStackTrace();
   }
  }

  private void whileChatting() throws IOException
  {
   int ctr=0;
   jTextField1.setEditable(true);
   do{
    ctr++;
    try
    {
     message = (String) input.readObject();
     chatArea.append("\n"+message);
    }
    catch(ClassNotFoundException classNotFoundException)
    {
    }
   }while(ctr<10);
   //}while(!message.equals("Bye"));
  }




  @Override
  public void actionPerformed(ActionEvent e) {
   if (e.getSource() == add) {
    new Add();
   }
   if (e.getSource() == delete) {
    new Delete();
   }
   if (e.getSource() == update) {
    new Update();
   }
   if (e.getSource() == display) {
    try {
     new Display();
    } catch (RemoteException ex) {
     ex.printStackTrace();
    }
   }
   if(e.getSource()==search){
    new Search();
   }
   if(e.getSource()==jButton1)
   {
    try
    {
     message=jTextField1.getText();
     chatArea.append("\nME(Client) - "+message);
     output.writeObject("                                                             Client - " + message);
     output.flush();
     jTextField1.setText("");
    }
    catch(IOException ioException)
    {
     chatArea.append("\n Unable to Send Message");
    }
   }
  }

 }


}
 class chat_client extends javax.swing.JFrame {

 private ObjectOutputStream output;
 private ObjectInputStream input;
 private String message="";
 private String serverIP;
 private Socket connection;
 private int port = 8888;

 public chat_client(String s) {

  initComponents();

  this.setTitle("Chat-Client");
  this.setVisible(true);
  status.setVisible(true);
  serverIP = s;
 }

 private void initComponents() {

  jTextField1 = new javax.swing.JTextField();
  jButton1 = new javax.swing.JButton();
  chatArea = new javax.swing.JTextArea();
  status = new javax.swing.JLabel();

  setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
  setResizable(false);
  jTextField1.setToolTipText("text\tType your message here...");
  jTextField1.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(java.awt.event.ActionEvent evt) {
    jTextField1ActionPerformed(evt);
   }
  });
  this.add(jTextField1);
  jTextField1.setBounds(10, 370, 410, 40);

  jButton1.setBackground(new java.awt.Color(222, 22, 222));
  jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
  jButton1.setText("Send");
  jButton1.addActionListener(new java.awt.event.ActionListener() {
   public void actionPerformed(java.awt.event.ActionEvent evt) {
    jButton1ActionPerformed(evt);
   }
  });
 this.add(jButton1);
  jButton1.setBounds(420, 370, 80, 40);

  chatArea.setColumns(20);
  chatArea.setRows(5);

  status.setForeground(new java.awt.Color(255, 255, 255));
  status.setText("...");
  status.setBounds(10, 50, 300, 40);

  setSize(new java.awt.Dimension(508, 441));
  setLocationRelativeTo(null);
 }// </editor-fold>//GEN-END:initComponents

 private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {

  sendMessage(jTextField1.getText());
  jTextField1.setText("");
 }//GEN-LAST:event_jTextField1ActionPerformed

 private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

  sendMessage(jTextField1.getText());
  jTextField1.setText("");
 }


 public void startRunning()
 {
  try
  {
   status.setText("Attempting Connection ...");
   try
   {
    connection = new Socket(InetAddress.getByName(serverIP),port);
   }catch(IOException ioEception)
   {
    JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
   }
   status.setText("Connected to: " + connection.getInetAddress().getHostName());


   output = new ObjectOutputStream(connection.getOutputStream());
   output.flush();
   input = new ObjectInputStream(connection.getInputStream());

   whileChatting();
  }
  catch(IOException ioException)
  {
   ioException.printStackTrace();
  }
 }

 private void whileChatting() throws IOException
 {
  int ctr=0;
  jTextField1.setEditable(true);
  do{
   ctr++;
   try
   {
    message = (String) input.readObject();
    chatArea.append("\n"+message);
   }
   catch(ClassNotFoundException classNotFoundException)
   {
   }
  }while(ctr<10);

 }


 private void sendMessage(String message)
 {
  try
  {

   chatArea.append("\nME(Client) - "+message);
   output.writeObject("                                                             Client - " + message);
   output.flush();
  }
  catch(IOException ioException)
  {
   chatArea.append("\n Unable to Send Message");
  }
 }
 private javax.swing.JTextArea chatArea;
 private javax.swing.JButton jButton1;
 private javax.swing.JTextField jTextField1;
 private javax.swing.JLabel status;
}
