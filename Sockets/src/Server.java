import java.io.*;
import java.net.*;

public class Server {
    static final int PORT = 9090;
    public static void main(String[] args) throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Server Started");
        Clients clt = new Clients();
        Restaurant restaurant = new Restaurant(clt);
        try {
            while(true) {
                // Blocks until a connection occurs:
                Socket socket = s.accept();
                try {
                    new ServeOneClient(socket, clt, restaurant);
                    System.out.println("join a new client - total number "+ clt.nCl());
                } catch(IOException e) {
                    // If it fails, close the socket,
                    // otherwise the thread will close it:
                    socket.close();
                }
            }
        } finally {
            s.close();
        }
    }
}