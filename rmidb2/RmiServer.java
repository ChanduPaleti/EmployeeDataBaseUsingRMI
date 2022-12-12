package com.classnames;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

import java.rmi.Naming;

import java.rmi.server.UnicastRemoteObject;


import java.sql.DriverManager;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.sql.Statement;

import java.sql.ResultSet;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf, ActionListener {
protected Connection conn = null;
String message="";
static JTextArea ar;
JOptionPane p1;
JFrame frame;
JTextArea chatArea;
JTextField jTextField1;
JButton jButton1;
    private Socket connection;
    private ServerSocket ss;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private int port = 8888;
public RmiServer() throws RemoteException {

    super(0);
   // ar.append("\nRMI server started");
    try {
        Naming.rebind("rmi://localhost:6666/classnames",this);
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    System.out.println("\nPeerServer bound in registry");
    try {
        ss=new ServerSocket(8888);
    } catch (IOException e) {
        e.printStackTrace();
    }
    frame = new JFrame("RMIServer");
    frame.setSize(600, 750);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(null);
    ar = new JTextArea();
    ar.setBounds(30, 40, 300, 300);
    Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
    ar.setBorder(border);
    ar.setEditable(false);
    ar.setLineWrap(true);
    frame.add(ar);
    chatArea = new JTextArea();
    chatArea.setEditable(false);
    chatArea.setBorder(border);
    chatArea.setBounds(30, 350, 400, 200);
    frame.add(chatArea);
    jTextField1 = new JTextField();
    jTextField1.setBounds(30, 570, 250, 40);
    frame.add(jTextField1);
    jButton1 = new JButton("SEND");
    jButton1.setBounds(300, 570, 80, 30);
    frame.add(jButton1);
    jButton1.addActionListener(this);
    frame.setVisible(true);


    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        ar.setText("Got mysql driver\n");

        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");

    } catch (Exception ex) {

        System.out.println("Error: " + ex.getMessage());

    }
    try {

        connection = ss.accept();
         chatArea.append(" Now Connected to "+connection.getInetAddress().getHostName());


        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());

        whileChatting();

    } catch (Exception eofException) {
    }

}
    private void whileChatting() throws IOException
    {
        String message="";
        int ctr=0;
        jTextField1.setEditable(true);
        do{
            ctr++;
            try
            {
                message = (String) input.readObject();
                chatArea.append("\n"+message);
            }catch(ClassNotFoundException classNotFoundException)
            {

            }
        }while(ctr<10);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButton1) {
            try
            {
                message=jTextField1.getText();
                chatArea.append("\nSERVER - "+message);
                output.writeObject("                              Server - " + message);
                output.flush();
                jTextField1.setText("");
            }
            catch(IOException ioException)
            {
                chatArea.append("\n Unable to Send Message");
            }
        }
    }
public String[] Search(String userQuery[])
{
    String[] response = new String[0];
    int a=JOptionPane.showConfirmDialog(frame,"Got an Search Employee Request of ID "+userQuery[0]);
    if(a==JOptionPane.YES_OPTION) {
        response = new String[6];
        int xs = 0;
        String s1[][] = getEmployeeDetails();
        for (int i = 0; i < s1.length; i++) {
            if (userQuery[0].equals(s1[i][0])) {
                response = s1[i];
                xs = 1;
                break;
            }
        }
        if (xs == 0) response[0] = "NOT FOUND";
        return response;
    }
    response[0]="UNABLE TO PROCESS";
    return response;
}

public String[][] getEmployee() { 
    ar.append("\n Display Request Initiated");
    String q1="select count(*) from employee";
    Statement st1=null;
    int length=0;
    try{
        st1=conn.createStatement();
        ResultSet rs1= st1.executeQuery(q1);
        while(rs1.next())
        {
            length= Integer.parseInt(rs1.getString(1));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    String[][] response=new String[length][];
  
        

String query= "select * from employee"; 

Statement st = null;



try
{

st = conn.createStatement(); 
ResultSet rs = st.executeQuery(query);
rs.next();
 for(int i=0;i<length;i++)
{
    response[i]=new String[6];
response[i][0]=rs.getString(1);
response[i][1]=rs.getString(2);
response[i][2]=rs.getString(3);
response[i][3]=rs.getString(4);
response[i][4]=rs.getString(5);
response[i][5]=rs.getString(6);
rs.next();
} }catch (Exception ex)

{

response[0][0] = ex.getMessage(); System.out.println("Error: "+ex.getMessage());
}

return response;
}
     String[][] getEmployeeDetails() {
        String q1="select count(*) from employee";
        Statement st1=null;
        int length=0;
        try{
            st1=conn.createStatement();
            ResultSet rs1= st1.executeQuery(q1);
            while(rs1.next())
            {
                length= Integer.parseInt(rs1.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[][] response=new String[length][];



        String query= "select * from employee";

        Statement st = null;



        try
        {

            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            for(int i=0;i<length;i++)
            {
                response[i]=new String[6];
                response[i][0]=rs.getString(1);
                response[i][1]=rs.getString(2);
                response[i][2]=rs.getString(3);
                response[i][3]=rs.getString(4);
                response[i][4]=rs.getString(5);
                response[i][5]=rs.getString(6);
                rs.next();
            } }catch (Exception ex)

        {

            response[0][0] = ex.getMessage(); System.out.println("Error: "+ex.getMessage());
        }

        return response;
    }


public String addEmployee(String[] userQuery) {

    int a=JOptionPane.showConfirmDialog(frame,"Got an Employee Request of details "+userQuery[0]+" "+userQuery[1]+" "+userQuery[2]+" "+userQuery[3]+" "+userQuery[4]+" "+userQuery[5]);
    if(a==JOptionPane.YES_OPTION){
String response = "";
String query = "insert into employee(id,name,salary,department,address,email) values(?,?,?,?,?,?)";
PreparedStatement pst = null;
ar.append("\nadd employee request initiated successful");
try
{
pst = conn.prepareStatement(query);
for (int i = 0; i < 6; i++) {
pst.setString(i+1,userQuery[i]);
System.out.print(userQuery[i]+" ");
}

System.out.println(pst);
if (pst.executeUpdate()==1){
//response = getEmployee();
}

}
catch (Exception ex)
{
response = ex.getMessage();
System.out.println("Error: "+ex.getMessage());
}


return ("Details succesfully added!");
    }
    else
    {
        ar.append("\n add employee request rejected");
        return ("Not added Succesfully");
    }
// return employee;
}

public String updateEmployee(String[] userQuery) {
    int x1=0;
    String s1[][]=getEmployeeDetails();
    for(int i=0;i< s1.length;i++)
    {
        if(s1[i][0].equals(userQuery[0]))
        {
            x1=1;
        }
    }
    if(x1==0)
        return "ID NOT FOUND";
    int a=JOptionPane.showConfirmDialog(frame,"Got an UPDATE Employee Request of ID "+userQuery[0]);
    if(a==JOptionPane.YES_OPTION) {
        String response = "";
        String query = "update employee set name=?, salary=?,department=?,address=?,email=? where id=?";
        PreparedStatement pst = null;

        ar.append("\nGot an 'update employee' request");

        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, userQuery[1]);
            pst.setString(2, userQuery[2]);
            pst.setString(3, userQuery[3]);
            pst.setString(4, userQuery[4]);
            pst.setString(5, userQuery[5]);
            pst.setString(6, userQuery[0]);

            if (pst.executeUpdate() == 1) {
            }

        } catch (Exception ex) {
            response = ex.getMessage();
            System.out.println("Error: " + ex.getMessage());
        }


        return "UPDATED SUCCESSFULLY";
    }
    else
    {
        return "UNABLE TO UPDATE";
    }
    
    
}

public String deleteEmployee(String[] userQuery) {

    int a=JOptionPane.showConfirmDialog(frame,"Got an DELETE Employee Request of ID "+userQuery[0]);
    if(a==JOptionPane.YES_OPTION) {

        String response = "";
        String query = "delete from employee where id=?";
        PreparedStatement pst = null;

        ar.append("\nGot a 'delete employee' request");

        try {
            pst = conn.prepareStatement(query);
            for (int i = 0; i < userQuery.length; i++) {
                pst.setString(i + 1, userQuery[i]);
            }
            if (pst.executeUpdate() == 1) {
            }
        } catch (Exception ex) {
            response = ex.getMessage();
            System.out.println("Error: " + ex.getMessage());
        }


        return ("Employee details successfully deleted!");
    }
    else
    {
        return ("UNABLE TO DELETE");
    }
}
public static void main(String args[]) throws Exception{
RmiServer obj = new RmiServer();
}
}


