import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class Clients{
    private ArrayList<PrintWriter>  pW;
    public Clients(){
        pW = new ArrayList<PrintWriter>(10);
    }
    public synchronized void addC(PrintWriter p){
        pW.add(p);
    }
    public synchronized void rmvC(PrintWriter p){
        pW.remove(p);
    }
    public synchronized void sendC(String s){
        Iterator<PrintWriter> itr = pW.iterator();
        while(itr.hasNext()) {
            PrintWriter p=(PrintWriter)itr.next();
            p.println(s);
        }       
    }
    public synchronized int nCl(){
        return pW.size();
    }

}
