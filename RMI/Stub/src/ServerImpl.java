import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server{

	private static final long serialVersionUID = 1L;
	String[] products = new String[]{"Apples", "Milk", "Eggs"};
	private ProductsList list = new ProductsList("Products List", products);
	
	public ServerImpl(String name) throws RemoteException {
       super();
       try {
           Naming.rebind(name, this);
       }
       catch(Exception e){System.out.println("Exception occurred: " + e);}
    }
	
    public String getName() throws RemoteException{
        return list.productsListName;
    }
    public ProductsList getList() throws RemoteException{
        return list;
    }
    public int getCount() throws RemoteException{
        return list.productsCount;
    }
}