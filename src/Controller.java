

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
		Event atual, next, primeiro, segundo;
		while ( (atual = primeiro = es.getNext()) != null) {
			es.removeCurrent();
			/*Uma comparação para ver qual evento deverá ser rodado primeiro só é feita caso exista mais de um evento*/
			if( (next = segundo = es.getNext()) != null ){
				/*Se a prioridade do segundo for maior ele é executado primeiro*/
				if(next.getPrioridade() < atual.getPrioridade()){
					primeiro = next;
					segundo = atual;
				}
				primeiro.action();
				System.out.println(primeiro.description());
				/*Caso não exista mais o segundo evento, significa que o evento de menor prioridade foi cancelado, ou seja,*/
				/*a batalha foi encerrada*/
				if(es.getCurrent() != null){
					segundo.action();
					System.out.println(segundo.description());
					es.removeCurrent();
				}
			}
			else{
				atual.action();
				System.out.println(atual.description());
			}
			System.out.println("\n");
		}	
	}
}