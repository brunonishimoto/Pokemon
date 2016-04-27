import java.util.Random;

public class Batalha extends Controller {
	Treinador red;
	Treinador green;
	
	private void setarBatalha (Treinador treinador1, Treinador treinador2) {
		red = treinador1;
		green = treinador2;
	}
	
	/*Pokemon de um treinador ataca o pokemon ativo do outro treinador*/
 	public class Atacar extends Event {
		private int atkIndex;
		private Treinador atacante;
		private Treinador defensor;
		private double multiplicador;
		private String retornar = "";
		private boolean acabou = false;
		private boolean pkmDerrotado = false;
		private String nomePkmAtacado;
		
		public Atacar (int index, Treinador atacante, Treinador defensor) {
			super(4, 1);
			atkIndex = index;
			this.atacante = atacante;
			this.defensor  = defensor;
		}
		
		// Verifica se o pokemon atacado foi derrotado e o treinador ainda possui pokemons para continuar a batalha
		private void derrotouPkm (){
			if (defensor.getPokemon().getHp() == 0){
				pkmDerrotado = true;
				if(defensor.getAtivo() == 1){
					// Se não existirem mais pokemons disponíveis o treinador perde a batalha
					finalizeEvent();
					acabou = true;
				}
				else{
					// Se ainda existirem pokemons o defensor manda o proximo pokemon para a batalha
					defensor.pkmIncapacidado();
					defensor.proxPkmAtivo();
				}
				// Caso o pokemon atacado for derrotado não deve ser executado seu movimento, se sua prioridade for a menor
				setSegundoEvt();
			}
		}
		
		// Monta a mensagem de saida
		private String montarMsg (){
			// As linhas abaixo são necessárias pois quando o pokemon for selvagem não se tem nome de treinador
			String nomeAtacante = (" de " + atacante.getNome());
			String nomeDefensor = (" de " + defensor.getNome());
			if (atacante.trPokemon())
				nomeAtacante = " selvagem";
			if (defensor.trPokemon())
				nomeDefensor = " selvagem";
			retornar = retornar.concat(atacante.getPokemon().getNome() + nomeAtacante +  " utilizou " + atacante.getPokemon().ataque(atkIndex) +
					" e causou " + (int)(atacante.getPokemon().atacar(atkIndex)*multiplicador)+ " de dano!");
			// As condições abaixo são relacionados os multiplicadores de dano do exercício extra
			if(multiplicador == 2)
				retornar = retornar.concat("\nO ataque foi super efetivo!");
			else if(multiplicador == 0.5)
				retornar = retornar.concat("\nO ataque não foi muito efetivo!");
			else if(multiplicador == 0)
				retornar = retornar.concat("\n" + nomePkmAtacado + "não é afetado por ataques desse tipo!");
			retornar = retornar.concat("\n" + nomePkmAtacado + nomeDefensor + " esta com ");
			if(!pkmDerrotado)
				retornar = retornar.concat(defensor.getPokemon().getHp() + "HP.");
			else{
				retornar = retornar.concat("0HP e foi derrotado!\n");
				if(acabou){
					if(!defensor.trPokemon())
						retornar = retornar.concat(defensor.getNome()  + " não possui mais pokemons disponíveis. " + defensor.getNome() +  " perdeu a Batalha!");
				}
				else
					retornar = retornar.concat(nomePkmAtacado + nomeDefensor + " foi substituido por " + defensor.getPokemon().getNome());
			}
			return retornar;
		}
		
		public void action () {
			multiplicador = atacante.getPokemon().getMultiplicador(defensor.getPokemon().getTipo());
			defensor.getPokemon().mudaHP(-(int) (atacante.getPokemon().atacar(atkIndex) * multiplicador));
			nomePkmAtacado = defensor.getPokemon().getNome();
			derrotouPkm();
			
		}
		
		public String description () {
			return (montarMsg());
		}
	}
	
	/*Treinador utiliza o item de cura em um pokemon */
	public class UsarItem extends Event {
		private Treinador curador;
		private int diferencaHp;
		
		public UsarItem (Treinador curador) {
			super(3, 1);
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
			super(2, 1);
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
			super(1, 1);
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
	
	public class CapturarPokemon extends Event {
		private int tipo;
		/* 0: Poké Ball
		 * 1: Great Ball */
		private Random gerador = new Random();
		private Pokemon pkmCapturado;
		private String retorno = "";
		private Treinador capturador;
		private String tipoPokeBall;
		
		public CapturarPokemon (int tipo,Treinador capturador, Pokemon pkmSelvagem) {
			super(1, 1);
			this.tipo = tipo;
			this.capturador = capturador;
			pkmCapturado = pkmSelvagem;
		}
		
		public void action() {
			int capturou = gerador.nextInt(256);
			int valorPokeBall;
			
			if (tipo == 1) {
				valorPokeBall = 8;
				tipoPokeBall = "Great Ball";
			}
			else {
				valorPokeBall = 12;
				tipoPokeBall = "Poke Ball";
			}
			if (capturou > (pkmCapturado.getHpMax()*255*4)/(pkmCapturado.getHp()*valorPokeBall)) {
				retorno = retorno.concat("Oh no!!! " + pkmCapturado.getNome() + " escapou da " + tipoPokeBall + "!");
			}
			else {
				retorno = retorno.concat(pkmCapturado.getNome() +  " foi capturado!");
				setSegundoEvt();
				finalizeEvent();
			}
		}
		
		public String description() {
			return (capturador.getNome() + " jogou uma " + tipoPokeBall + ".\n" + retorno);
		}
	}
	
	/*Inicia a batalha pokemon*/
	public class Versus extends Event{
		
		public Versus (){
			super(0, 2);
		}
		
		public void action (){
			/*Setando os dois treinadores com seus respectivos pokemons*/
			Pokemon[] pk1 = new Pokemon[6];
			pk1[0] = new Pokemon("Charizard", 9, 78, "Lança Chamas", "Super Aquecimento", "Explosão de Fogo", "Brasa", 20, 30, 24, 10);
			pk1[1] = new Pokemon("Venosaur", 11, 80, "Esfera de Energia", "Folha Navalha", "Raio Solar", "Danca de Pétalas", 18, 10, 30, 20);
			pk1[2] = new Pokemon("Blastoise", 10, 79, "Jato de Água", "Rajada de Bolhas", "Surf", "Hidro Bomba", 20, 10, 25, 30);
			pk1[3] = new Pokemon("Pikachu", 12, 50, "Choque do Trovão", "Faísca", "Trovão", "Descarga", 25, 15, 30, 20);
			pk1[4] = new Pokemon("Pidgeot", 2, 83, "Furacão", "Ataque de Asa", "Barra de Ar", "Ataque do Céu", 30, 15, 20, 25);
			pk1[5] = new Pokemon("Golem", 5, 80, "Ponta de Pedra", "Explosão", "Terremoto", "Chuva de Pedra", 10, 30, 24, 18);
			String nomeTreinador1 = "Red";
			Treinador tr1 = new Treinador(nomeTreinador1, 1, pk1);
			Pokemon[] pk2 = new Pokemon[6];
			pk2[0] = new Pokemon("Charizard", 9, 78, "Lança Chamas", "Super Aquecimento", "Explosao de Fogo", "Brasa", 20, 30, 24, 10);
			pk2[1] = new Pokemon("Venosaur", 11, 80, "Esfera de Energia", "Folha Navalha", "Raio Solar", "Danca de Petalas", 18, 10, 30, 20);
			pk2[2] = new Pokemon("Blastoise", 10, 79, "Jato de Agua", "Rajada de Bolhas", "Surf", "Hidro Bomba", 20, 10, 25, 30);
			pk2[3] = new Pokemon("Pikachu", 12, 50, "Choque do Trovão", "Faísca", "Trovão", "Descarga", 25, 15, 30, 20);
			pk2[4] = new Pokemon("Alakazan", 13, 55, "Visão Futura", "Psiquico", "Corte Psíco", "Psybeam", 30, 25, 20, 10);
			pk2[5] = new Pokemon("Dragonite", 15, 91, "Ultrage", "Rabo de Dragão", "Dança do Dragão", "Corrida do Dragão", 28, 10, 30, 16);
			String nomeTreinador2 = "Green";
			Treinador tr2 = new Treinador(nomeTreinador2, 1, pk2);
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
	
	// Andar pelo chao comum, onde não existem pokemons selvagens
	public class AndarChao extends Event{
		public AndarChao (){
			super(0, 2);
		}
		
		public void action (){
			return;
		}
		
		public String description (){
			return ("Andando pelo chão comum.");
		}
	}
	
	// Andar pelo gramado onde se tem uma chance de encontrar pokemons selvagens
	public class AndarGramado extends Event{
		private int achouPkm, qualPkm;
		private Random gerador = new Random();
		private Pokemon encontrado;
		
		public AndarGramado (){
			super(0, 2);
		}
		
		// Define o pokemon selvagem encontrado no gramado
		public Pokemon definePkm (){
			qualPkm = gerador.nextInt(100);
			if(qualPkm < 5)
				return (new Pokemon("Mewtwo", 13, 220, "Comedor de Sonhos", "Pancada Psíquica", "Corte Psíquico", "Confusão", 25, 35, 26, 15));
			if(qualPkm < 20)
				return (new Pokemon("Articuno", 14, 190, "Nevasca", "Raio de Gelo", "Sopro de Congelamento", "Caco de Gelo", 32, 25, 20, 15));
			if(qualPkm < 50)
				return (new Pokemon("Meowth", 0, 130, "Talhada", "Fachada", "Retalhar", "Dia do Pagamento", 20, 25, 22, 12));
			return (new Pokemon("Bellsprout", 11, 100, "Raio Solar", "Folha Navalha", "Chicote de Cipó", "Esfera de Energia", 23, 15, 10, 15));
		}
		
		public void action (){
			achouPkm = gerador.nextInt(100);
			if (achouPkm <= 80){
				// Define os pokemos do treinador e o pokemon selvagem
				Pokemon[] pk1 = new Pokemon[6];
				encontrado = definePkm();
				pk1[0] = new Pokemon("Charizard", 9, 78, "Lança Chamas", "Super Aquecimento", "Explosão de Fogo", "Brasa", 20, 30, 24, 10);
				pk1[1] = new Pokemon("Venosaur", 11, 80, "Esfera de Energia", "Folha Navalha", "Raio Solar", "Danca de Pétalas", 18, 10, 30, 20);
				pk1[2] = new Pokemon("Blastoise", 10, 79, "Jato de Água", "Rajada de Bolhas", "Surf", "Hidro Bomba", 20, 10, 25, 30);
				pk1[3] = new Pokemon("Pikachu", 12, 50, "Choque do Trovão", "Faísca", "Trovão", "Descarga", 25, 15, 30, 20);
				pk1[4] = new Pokemon("Pidgeot", 2, 83, "Furacão", "Ataque de Asa", "Barra de Ar", "Ataque do Céu", 30, 15, 20, 25);
				pk1[5] = new Pokemon("Golem", 5, 80, "Ponta de Pedra", "Explosão", "Terremoto", "Chuva de Pedra", 10, 30, 24, 18);
				String nomeTreinador1 = "Red";
				Treinador tr1 = new Treinador(nomeTreinador1, 1, pk1);
				Pokemon[] pk2 = new Pokemon[]{encontrado};
				Treinador pkmSelvagem = new Treinador(pk2[0].getNome(), 2, pk2);
				setarBatalha(tr1, pkmSelvagem);
				
				
				/*Inicio da batalha com os Pokemon selvagem*/
				addEvent(new CapturarPokemon(2, tr1, pk2[0]));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));
				
				addEvent(new TrocarPokemon(4, tr1));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));	//O Pokemon selvagem ataca de forma aleatoria
				
				addEvent(new Atacar (0, tr1, pkmSelvagem));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));
				
				addEvent(new CapturarPokemon(1, tr1, pk2[0]));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));
				
				addEvent(new UsarItem(tr1));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));
				
				addEvent(new Atacar (0, tr1, pkmSelvagem));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));
				
				addEvent(new Atacar (0, tr1, pkmSelvagem));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));
				
				addEvent(new Fugir(tr1));
				addEvent(new Atacar (gerador.nextInt(4), pkmSelvagem, tr1));
			}
		}
		
		public String description (){
			if (this.achouPkm <= 80) {
				return ("Pokemon encontrado! " + encontrado.getNome() + " selvagem está pronto para a batalha.");
			}
			return ("Andando pelo gramado.");
		}
		
	}
	
	public static void main (String []args){
		Batalha btl = new Batalha();
		btl.addEvent(btl.new Versus());
		btl.run();
		btl.addEvent(btl.new AndarChao());
		btl.addEvent(btl.new AndarGramado());
		btl.run();
	}
}
