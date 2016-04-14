

abstract public class Event {
	private int prioridade;
	public Event(int prioridade) {
		this.prioridade = prioridade;
	}
	abstract public void action();
	abstract public String description();
}