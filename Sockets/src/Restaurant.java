public class Restaurant {
	private boolean hostess;
	private boolean waiter;
	private boolean runner;
	private boolean busser;
	Clients clients;
	
	public Restaurant(Clients clt) {
		clients = clt;
		hostess = false;
		waiter = false;
		runner = false;
		busser = false;
	}
	
	public synchronized void engageHostess() {
		while(hostess) {
			//clients.sendC("The hostess is busy");
			try{ wait(); }
            catch(InterruptedException e){ System.err.println(e); }
		}
		clients.sendC("Someone engaged the hostess");
		hostess = true;
	}
	public synchronized void releaseHostess() {
		hostess = false;
		clients.sendC("The hostess is free");
		notifyAll();
	}
	
	public synchronized void engageWaiter() {
		while(waiter) {
			//clients.sendC("The waiter is busy");
			try{ wait(); }
            catch(InterruptedException e){ System.err.println(e); }
		}
		clients.sendC("Someone engaged the waiter");
		waiter = true;
	}
	public synchronized void releaseWaiter() {
		waiter = false;
		clients.sendC("The waiter is free");
		notifyAll();
	}
	
	public synchronized void engageRunner() {
		while(runner) {
			//clients.sendC("The runner is busy");
			try{ wait(); }
            catch(InterruptedException e){ System.err.println(e); }
		}
		clients.sendC("Someone engaged the runner");
		runner = true;
	}
	public synchronized void releaseRunner() {
		runner = false;
		clients.sendC("The runner is free");
		notifyAll();
	}
	
	public synchronized void engageBusser() {
		while(busser) {
			//clients.sendC("The busser is busy");
			try{ wait(); }
            catch(InterruptedException e){ System.err.println(e); }
		}
		clients.sendC("Someone engaged the busser");
		busser = true;
	}
	public synchronized void releaseBusser() {
		busser = false;
		clients.sendC("The busser is free");
		notifyAll();
	}
	
}
