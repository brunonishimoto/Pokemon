

abstract public class Event {
	private int prioridade;
	private int tipoEvento;
	
	// tipoEvento == 1: Evento de Batalha
	// tipoEvento == 2: Evento fora da Batalha
	
	public Event(int prioridade, int tipoEvento) {
		this.prioridade = prioridade;
		this.tipoEvento = tipoEvento;
	}
	
	public int getPrioridade () {
		return prioridade;
	}
	
	public boolean eventoBatalha (){
		if(tipoEvento == 1)
			return true;
		return false;
	}
	
	abstract public void action();
	abstract public String description();
}