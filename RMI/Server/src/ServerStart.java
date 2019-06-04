import java.rmi.*;

class ServerStart{
        public static void main(String args[]) {
           try{
        	   
               //create a local instance of the object
               new ServerImpl("SAMPLE-SERVER");

               System.out.println("Server waiting.....");
           }
           catch (RemoteException re)  {
               System.out.println("Remote exception: " + re.toString());  }
        }
}