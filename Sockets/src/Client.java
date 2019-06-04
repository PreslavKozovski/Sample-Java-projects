import java.net.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class Client {
    BufferedReader in;
    PrintWriter out;
    Socket socket;
    Gui g;
    Client(JFrame f){
        init();
        g = new Gui(f);
    }
    class Gui{
        JTextArea serv;
        JButton buttonHostess;
        JButton buttonWaiterOrder;
        JButton buttonWaiterBill;
        JButton buttonBusser;
        JButton buttonLeave;
        JButton buttonClear;
        Action action = new Action();
        
        Gui (JFrame f){
            f.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            
            buttonHostess = new JButton("Request a table");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            f.add(buttonHostess, c);
            
            buttonWaiterOrder = new JButton("Request food");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = 0;
            f.add(buttonWaiterOrder, c);
            
            buttonWaiterBill = new JButton("Request the bill");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 2;
            c.gridy = 0;
            f.add(buttonWaiterBill, c);
            
            buttonBusser = new JButton("Request table cleaning");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 3;
            c.gridy = 0;
            f.add(buttonBusser, c);
            
            buttonLeave = new JButton("Leave");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 4;
            c.gridy = 0;
            f.add(buttonLeave, c);
            
            buttonClear = new JButton("Clear text");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 5;
            c.gridy = 0;
            f.add(buttonClear, c);  

            serv = new JTextArea(50,10);
            serv.setEditable(false);
            serv.setFont(new Font("SANS_SERIF", Font.BOLD, 14));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 6;
            c.ipady = 200;
            c.weightx = 0.0;
            c.weighty = 1.0;
            c.gridx = 0;           
            c.gridy = 1;
            f.add(new JScrollPane(serv), c);
            
            buttonWaiterOrder.setEnabled(false);
            buttonWaiterBill.setEnabled(false);
            buttonBusser.setEnabled(false);
            
            buttonHostess.addActionListener(action);
            buttonWaiterOrder.addActionListener(action);
            buttonWaiterBill.addActionListener(action);
            buttonBusser.addActionListener(action);
            buttonLeave.addActionListener(action);
            buttonClear.addActionListener(action);
            (new Rcv()).start();
        }
        class Action implements ActionListener{
            public void actionPerformed(ActionEvent e){
                try{
                	if(e.getSource() == buttonHostess) {
                		send("hostess");
                		buttonHostess.setEnabled(false);
                	}else if(e.getSource() == buttonWaiterOrder) {
                		send("waiterOrder");
                		buttonWaiterBill.setEnabled(false);
                		buttonBusser.setEnabled(false);
                		buttonLeave.setEnabled(false);
                	}else if(e.getSource() == buttonWaiterBill) {
                		send("waiterBill");
                		buttonWaiterOrder.setEnabled(false);
                		buttonWaiterBill.setEnabled(false);
                		buttonBusser.setEnabled(false);
                	}else if(e.getSource() == buttonBusser) {
                		send("busser");
                		buttonBusser.setEnabled(false);
                	}else if(e.getSource() == buttonLeave) {
                		send("");
                	}else if(e.getSource() == buttonClear) {
                		serv.setText(null);
                	}
                }
                catch (Exception ex){
                    System.out.println("exception: "+ex);
                    System.out.println("closing...");
                    try{
                        socket.close();
                    }
                    catch (Exception expt){
                        System.out.println(expt);
                    }
                }
            }
        }
        class Rcv extends Thread{
            public void run(){
                for(;;){
                    try {         
                        sleep(400);
                    } catch (InterruptedException e){}
                    try{
                    	String line = in.readLine();
                        serv.append(line+"\n");
                        
                		if(line.equals("You are at the table")) {
                			buttonWaiterOrder.setEnabled(true);
                        }else if(line.equals("The food is served")) {
                        	buttonBusser.setEnabled(true);
                        }else if(line.equals("The busser cleaned your table")) {
                        	buttonWaiterBill.setEnabled(true);
                        }else if(line.equals("The waiter brought the bill")) {
                        	buttonLeave.setEnabled(true);
                        }
                        
                    } catch (IOException e1){break;}
                }
                System.out.println(" closing reading thread...");
                try{
                    socket.close();
                }
                catch (Exception expt){
                    System.out.println(expt);
                }
                System.exit(0);
            }
        }
    }
    public void init(){
        try{
            String server = null;
            InetAddress addr = InetAddress.getByName(server);
            System.out.println("addr = " + addr);
            socket = new Socket(addr, Server.PORT);
            System.out.println("socket = " + socket);
            in =  new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),true);            
        }catch (Exception e){
            System.out.println("exception: "+e);
            System.out.println("closing...");
            try{
                socket.close();
            }catch (Exception e2){
                System.out.println("no server running");
                System.exit(5);
            }
        }
    }
    void send(String s){
        if(s.length()==0){
            int quit = JOptionPane.showConfirmDialog(null, "Leave Restaurant?");
            if(quit == 0) {
                out.println("END");
                System.out.println("closing...");
                try{
                    socket.close();
                }
                catch (Exception expt){
                    System.out.println(expt);
                }
                System.exit(0);
            }
        }
        else out.println(s);
    }
    public static void main(String[] args )throws IOException{
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        new Client(frame);
        frame.setTitle("Hello");
        frame.setSize(700,300);
        frame.setVisible(true);
    }
}