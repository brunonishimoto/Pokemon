

public class Controller {
	private EventSet es = new EventSet();
	
	public void addEvent(Event c) { 
		es.add(c);
	}
	
	//Dê uma olhada nisso
	public void finalizeEvent () {
		while(es.getNext()!= null) {
			es.removeCurrent();
		}
	}
	
	public void run() { 
		Event e;
		while ( (e = es.getNext()) != null) {
			e.action();
			System.out.println(e.description());
			es.removeCurrent();
		}	
	}
}