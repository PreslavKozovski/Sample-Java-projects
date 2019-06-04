import java.rmi.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client {    
    Gui g;
    
    Client(JFrame f, String url){
        this.g = new Gui(f, url);
    }
    
	class Gui{
        JTextArea serv;
        JButton buttonName;
        JButton buttonCount;
        JButton buttonGetList;
        Action action = new Action();
        Server remoteObject;
        String url;
                
		Gui (JFrame f, String url){
			this.url = url;
            f.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
		
            buttonName = new JButton("Get List Name");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            f.add(buttonName, c);
            
            buttonCount = new JButton("Get Items Count");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = 0;
            f.add(buttonCount, c);
            
            buttonGetList = new JButton("Get the List");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 2;
            c.gridy = 0;
            f.add(buttonGetList, c);
            
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
            
            buttonName.addActionListener(action);
            buttonCount.addActionListener(action);
            buttonGetList.addActionListener(action);
            
            try {
    			remoteObject = (Server)Naming.lookup(url);
    		}
    		catch (RemoteException exc) {
    			System.out.println("Error in lookup: " + exc.toString()); }
    		catch (java.net.MalformedURLException exc) {
    			System.out.println("Malformed URL: " + exc.toString());   }
    		catch (java.rmi.NotBoundException exc)  {
    			System.out.println("NotBound: " + exc.toString());        }
		}
		class Action implements ActionListener{
			public void actionPerformed(ActionEvent e){
                try{
                	if(e.getSource() == buttonName) {
                		serv.append("Name: " + remoteObject.getName()+"\n");
                	}else if(e.getSource() == buttonCount) {
                		serv.append("Count: " + remoteObject.getCount()+"\n");
                	}else if(e.getSource() == buttonGetList) {
            			ProductsList list = remoteObject.getList();
            			
            			serv.append("List: " + "\n");
            			for (int i = 0; i < list.productsArray.length; i++) {
            				serv.append(i + ". " +list.productsArray[i] + "\n");
            			}
                	}
                }
                catch (Exception ex){
                	System.out.println("exception: " + ex);
                }
            }
		}
	}
	
	public static void main(String[] args){	
		System.setProperty("java.security.policy", "client.policy");
		System.setSecurityManager(new SecurityManager());
		if (args.length != 1){
			System.out.println("usage: java SampleClient <IP address of host running RMI server>");
			System.exit(0);
		}
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		String url = "rmi://"+args[0] +"/SAMPLE-SERVER";
		new Client(frame, url);
        frame.setTitle("List of Products");
        frame.setSize(400,300);
        frame.setVisible(true);
	}
}