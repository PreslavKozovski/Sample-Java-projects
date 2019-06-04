import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServeOneClient extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    Clients clt;
    Restaurant restaurant;
    public ServeOneClient(Socket s,Clients clt, Restaurant res)  throws IOException {
        socket = s;
        this.clt =clt;
        this.restaurant = res;
        in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                    )
                );
        out = new PrintWriter( new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream()
                    )
                ),true);                
        clt.addC(out);
        start();
    }
    public void run() {
        try {
            while (true) {
                String str = in.readLine();
                if (str.equals("END")) break;
                
                if (str.equals("hostess")) {
                	out.println("You requested the hostess");
                	restaurant.engageHostess();
                    try {
                        out.println("You are following the hostess");
                        sleep((int)(Math.random()*5000));
                    } catch (InterruptedException e){}
                    out.println("You are at the table");
                    restaurant.releaseHostess();
                    
                }else if(str.equals("waiterOrder")){                	
                	out.println("You requested the waiter");
                	restaurant.engageWaiter();
                    try {
                    	out.println("The waiter is at your table");
                        sleep((int)(Math.random()*5000));
                    } catch (InterruptedException e){}
                    out.println("The waiter requsted the food");
                    restaurant.releaseWaiter();    
                    
                    try {
                        sleep((int)(Math.random()*5000));
                    } catch (InterruptedException e){}
                    
                    out.println("Your food is ready, requesting the runner");
                    restaurant.engageRunner();
                    try {
                    	out.println("The runner is bringing the food");
                        sleep((int)(Math.random()*5000));
                    } catch (InterruptedException e){}
                    out.println("The food is served");
                    restaurant.releaseRunner();
                    
                }else if(str.equals("waiterBill")){
                	out.println("You requested the bill");
                	restaurant.engageWaiter();
                    try {
                    	out.println("The waiter is coming with the bill");
                        sleep((int)(Math.random()*5000));
                    } catch (InterruptedException e){}
                    out.println("The waiter brought the bill");
                    restaurant.releaseWaiter(); 
                	
                }else if(str.equals("busser")){                	
                	out.println("You requested the busser");
                	restaurant.engageBusser();
                    try {
                    	out.println("The busser is cleaning your table");
                        sleep((int)(Math.random()*5000));
                    } catch (InterruptedException e){}
                    out.println("The busser cleaned your table");
                    restaurant.releaseBusser();                	
                }
            }
        } catch (IOException e) {  }
        finally {
            try {
                clt.rmvC(out);
                System.out.println("disconect a client. Total number "+clt.nCl());
                socket.close();
            } catch(IOException e) {}
        }
    }
}