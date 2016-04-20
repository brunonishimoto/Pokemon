

abstract public class Event {
	private int prioridade;
	
	public Event(int prioridade) {
		this.prioridade = prioridade;
	}
	
	//Veja se isso é realmente necessário
	public int getPrioridade () {
		return prioridade;
	}
	
	abstract public void action();
	abstract public String description();
}