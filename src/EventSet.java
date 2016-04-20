

class EventSet {
	private Event[] events = new Event[100];
	private int index = 0;
	private int next = 0;
	
	public void add(Event e) {
		if (index >= events.length)
			return;
		//Verifica isso, eu buguei
		//Caso a prioridade do evento a ser adicionado seja meonor do que a do evento antes dele
		if (e.getPrioridade() < events[index - 1].getPrioridade()) {
			events[index] = events[index - 1]; //Coloca o evento anterior na ultima posicao
			events[index - 1] = e; //Coloca o evento a ser adicionado antes do evento anterior
			index++;
			return;
		}
		//Caso a prioridade seja maior ou igual
		events[index++] = e; //Só adiciona o evento
	}
	
	public Event getNext() {
		boolean looped = false;
		int start = next;
		do {
			next = (next + 1) % events.length;
			if (start == next) looped = true;
			if ((next == (start + 1) % events.length) && looped)
				return null;
		} while (events[next] == null);
		return events[next];
	}
	
	public void removeCurrent() {
		events[next] = null;
	}
}
