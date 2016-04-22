

public class Batalha extends Controller {
	Treinador red;
	Treinador green;
	
	private void setarBatalha (Treinador treinador1, Treinador treinador2) {
		red = treinador1;
		green = treinador2;
	}
	
	/*Verifica se o pokemon atacado foi derrotado e o treinador ainda possui pokemons para continuar a batalha*/
	private String pkmDerrotado (Treinador atacado){
		String retornar = "";
		if (atacado.getPokemon().getHp() == 0){
			retornar = retornar.concat( "\n" + atacado.getPokemon().getNome() + " de " + atacado.getNome() + " foi derrotado!\n");
			if(atacado.getAtivo() == 1){
				/*Se não existirem mais pokemons disponíveis o treinador perde a batalha*/
				finalizeEvent();
				retornar = retornar.concat(atacado.getNome()  + " não possui mais pokemons disponíveis. " + atacado.getNome() +  " perdeu a Batalha!");
			}
			else{
				atacado.pkmIncapacidado();
				retornar = retornar.concat(atacado.getPokemon().getNome() + " de " + atacado.getNome() + " foi substituido por ");
				atacado.proxPkmAtivo();
				retornar = retornar.concat(atacado.getPokemon().getNome() + ".");
			}
			setSegundoEvt();
		}
		return retornar;
	}
	
	/*Pokemon de um treinador ataca o pokemon ativo do outro treinador*/
	/** EU IA FAZER EXATAMENTE ISSO, MAS FUI ASSISTIR O MASTERCHEF. E BOTA O QUE FOR PRA APAGAR EM CAPS PRA FACILITAR DEPOIS. DEIXA EM AZUL TBM **/
 	public class Atacar extends Event {
		private int atkIndex;
		private Treinador atacante;
		private Treinador defensor;
		
		public Atacar (int index, Treinador atacante, Treinador defensor) {
			super(4);
			atkIndex = index;
			this.atacante = atacante;
			this.defensor  = defensor;
		}
		
		public void action () {
			double multiplicador = atacante.getPokemon().getMultiplicador(defensor.getPokemon().getTipo());
			defensor.getPokemon().mudaHP(-(int) (atacante.getPokemon().atacar(atkIndex) * multiplicador));
		}
		
		public String description () {
			double multiplicador = atacante.getPokemon().getMultiplicador(defensor.getPokemon().getTipo());
			return (atacante.getPokemon().getNome() + " de "  + atacante.getNome() +  " utilizou " + 
					atacante.getPokemon().ataque(atkIndex) + " e causou " + (int)(atacante.getPokemon().atacar(atkIndex)*multiplicador)
					+ "de dano!" + "\n" + defensor.getPokemon().getNome() + " de " + defensor.getNome() + 
					" esta com " + defensor.getPokemon().getHp() + "HP." + pkmDerrotado(defensor));
		}
	}
	
	/*Treinador utiliza o item de cura em um pokemon */
	public class UsarItem extends Event {
		private Treinador curador;
		private int diferencaHp;
		
		public UsarItem (Treinador curador) {
			super(3);
			this.curador = curador;
		}
		
		public void action () {
			diferencaHp = curador.getPokemon().getHp();
			curador.getPokemon().mudaHP(20);
			diferencaHp = (curador.getPokemon().getHp() - diferencaHp);
		}
		
		public String description () {
			return (curador.getNome() + " utilizou uma poção. " + curador.getPokemon().getNome() +
					" foi curado em " + diferencaHp +  "HP. Novo HP: " + curador.getPokemon().getHp() + "HP.");
		}
	}
	
	/*Treinador troca seu pokemon por outro que possui*/
	public class TrocarPokemon extends Event {
		private int pkmIndex;
		private Treinador trocador;
		private String pkmAnterior;
		
		public TrocarPokemon (int index, Treinador trocador){
			super(2);
			pkmIndex = index;
			this.trocador = trocador;
		}
		
		public void action () {
			pkmAnterior = trocador.getPokemon().getNome();
			trocador.trocaPokemon(pkmIndex);
		}
		
		public String description () {
			return (trocador.getNome() + " substituiu " + pkmAnterior + " por " + trocador.getPokemon().getNome() + ".");
		}
	}
	
	/*Fugir da batalha*/
	public class Fugir extends Event {
		private Treinador desistente;
		
		public Fugir (Treinador desistente) {
			super(1);
			this.desistente = desistente;
		}
		
		public void action() {
			finalizeEvent();
			setSegundoEvt();
		}
		
		public String description() { 
			return (desistente.getNome() + " fugiu da batalha Pokemon!");
		}
	}
	

	/*Inicia a batalha pokemon*/
	public class Restart extends Event{
		
		public Restart (){
			super(0);
		}
		
		public void action (){
			/*Setando os dois treinadores com seus respectivos pokemons*/
			Pokemon[] pk1 = new Pokemon[6];
			pk1[0] = new Pokemon("Charizard", "Fogo", 78, "Lança Chamas", "Super Aquecimento", "Explosão de Fogo", "Brasa", 20, 30, 24, 10);
			pk1[1] = new Pokemon("Venosaur", "Grama", 80, "Esfera de Energia", "Folha Navalha", "Raio Solar", "Danca de Pétalas", 18, 10, 30, 20);
			pk1[2] = new Pokemon("Blastoise", "Agua", 79, "Jato de Água", "Rajada de Bolhas", "Surf", "Hidro Bomba", 20, 10, 25, 30);
			pk1[3] = new Pokemon("Pikachu", "Eletrico", 50, "Choque do Trovão", "Faísca", "Trovão", "Descarga", 25, 15, 30, 20);
			pk1[4] = new Pokemon("Pidgeot", "Voador", 83, "Furacão", "Ataque de Asa", "Barra de Ar", "Ataque do Céu", 30, 15, 20, 25);
			pk1[5] = new Pokemon("Golem", "Pedra", 80, "Ponta de Pedra", "Explosão", "Terremoto", "Chuva de Pedra", 10, 30, 24, 18);
			String nomeTreinador1 = "Red";
			Treinador tr1 = new Treinador(nomeTreinador1, pk1);
			Pokemon[] pk2 = new Pokemon[6];
			pk2[0] = new Pokemon("Charizard", "Fogo", 78, "Lança Chamas", "Super Aquecimento", "Explosao de Fogo", "Brasa", 20, 30, 24, 10);
			pk2[1] = new Pokemon("Venosaur", "Grama", 80, "Esfera de Energia", "Folha Navalha", "Raio Solar", "Danca de Petalas", 18, 10, 30, 20);
			pk2[2] = new Pokemon("Blastoise", "Agua", 79, "Jato de Agua", "Rajada de Bolhas", "Surf", "Hidro Bomba", 20, 10, 25, 30);
			pk2[3] = new Pokemon("Pikachu", "Eletrico", 50, "Choque do Trovão", "Faísca", "Trovão", "Descarga", 25, 15, 30, 20);
			pk2[4] = new Pokemon("Alakazan", "Psiquico", 55, "Visão Futura", "Psiquico", "Corte Psíco", "Psybeam", 30, 25, 20, 10);
			pk2[5] = new Pokemon("Dragonite", "Dragao", 91, "Ultrage", "Rabo de Dragão", "Dança do Dragão", "Corrida do Dragão", 28, 10, 30, 16);
			String nomeTreinador2 = "Green";
			Treinador tr2 = new Treinador(nomeTreinador2, pk2);
			/*Coloca os dois treinadores e seus pokemons na batalha*/
			setarBatalha(tr1, tr2);
			
			/*Inicio dos eventos*/
			addEvent(new Atacar(2, tr1, tr2));
			addEvent(new TrocarPokemon(1, tr2));
			
			addEvent(new Atacar(0, tr1, tr2));
			addEvent(new Atacar(2, tr2, tr1));
			
			addEvent(new Atacar(2, tr1, tr2));
			addEvent(new UsarItem(tr2));
			
			addEvent(new Atacar(1, tr1, tr2));
			addEvent(new Atacar(3, tr2, tr1));
			
			addEvent(new Atacar(2, tr1, tr2));
			addEvent(new Atacar(2, tr2, tr1));
			
			addEvent(new Atacar(1, tr1, tr2));
			addEvent(new Atacar(1, tr2, tr1));
			
			addEvent(new Atacar(3, tr1, tr2));
			addEvent(new Fugir(tr2));
		}
		
		public String description (){
			return ("Comecou a batalha Pokemon!");
		}
	}
	
	public static void main (String []args){
		Batalha btl = new Batalha();
		btl.addEvent(btl.new Restart());
		btl.run();
	}
}
