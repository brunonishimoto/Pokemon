

public class Controller {
	private EventSet es = new EventSet();
	// O boolean abaixo verifica se o evento de menor prioridade deve ser executado ou não
	private boolean segundoEvt;
	
	public void addEvent(Event c) { 
		es.add(c);
	}
	
	// Remove todos os eventos, pois a batalha foi encerrada
	public void finalizeEvent () {
		while(es.getNext()!= null) {
			es.removeCurrent();
		}
	}
	
	// Determina que não se deve executar o evento de menor prioridade
	public void setSegundoEvt() {
		segundoEvt = false;
	}
	
	public void run() { 
		Event atual, next, primeiro, segundo;
		while ( (atual = primeiro = es.getNext()) != null) {
			es.removeCurrent();
			segundoEvt = true;
			// Uma comparação para ver qual evento deverá ser rodado primeiro só é feita caso exista mais de um evento
			if( atual.eventoBatalha() && (next = segundo = es.getNext()) != null ){
				// Se a prioridade do segundo for maior ele é executado primeiro
				if(next.getPrioridade() < atual.getPrioridade()){
					primeiro = next;
					segundo = atual;
				}
				primeiro.action();
				System.out.println(primeiro.description());
				// Caso o evento de menor prioridade tenha sido cancelado, ou seja, a batalha foi encerrada ou o pokemon foi derrotado
				if(segundoEvt){
					segundo.action();
					System.out.println(segundo.description());
					es.removeCurrent();
				}
			}
			// Caso exista apenas um evento, apenas ele será executado
			else{
				atual.action();
				System.out.println(atual.description());
			}
			System.out.println("\n");
		}	
	}
}