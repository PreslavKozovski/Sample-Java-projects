import java.rmi.*;

public interface Server extends Remote
{
  public String getName() throws RemoteException;
  public ProductsList getList() throws RemoteException;
  public int getCount() throws RemoteException;
}