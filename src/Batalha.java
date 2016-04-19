

public class Batalha extends Controller {
	Treinador red;
	Treinador green;
	
	public void setarBatalha (Treinador treinador1, Treinador treinador2) {
		red = treinador1;
		green = treinador2;
	}
	
	/*Pokemon de um treinador ataca o pokemon ativo do outro treinador*/
	public class Atacar extends Event {
		private int atkIndex;
		private String atacante;
		
		public Atacar (int index, String atacante) {
			super(3);
			atkIndex = index;
			this.atacante = atacante;
		}
		
		public void action () {
			if (red.getNome().equals(atacante)) {
				green.getPokemon().mudaHP(-red.getPokemon().atacar(atkIndex));
				return;
			}
			red.getPokemon().mudaHP(-green.getPokemon().atacar(atkIndex));
		}
		
		public String description () {
			if (red.getNome().equals(atacante)) {
				return (red.getPokemon().getNome() + " de "  + red.getNome() +  " utilizou " + red.getPokemon().ataque(atkIndex)  + "!" +
						"\n" + green.getPokemon().getNome() + " de " + green.getNome() + " esta com " + green.getPokemon().getHp() + "HP.");
			}
			return (green.getPokemon().getNome() + " de "  + green.getNome() +  " utilizou " + green.getPokemon().ataque(atkIndex) +
					"\n" + red.getPokemon().getNome() + " de " + red.getNome() + " está com " + red.getPokemon().getHp() + "HP");
		}
	}
	
	/*Treinador troca seu pokemon por outro que possui*/
	public class TrocarPokemon extends Event {
		private int pkmIndex;
		private Treinador trocador;
		private String pkmAnterior;
		
		public TrocarPokemon (int index, Treinador trocador){   //  <--- DÁ PRA FAZER ASSIM
			super(1);											//	<--- OLHA ESSAS LINHAS
			pkmIndex = index;									//	<--- ACHO QUE FACILITA
			this.trocador = trocador;							//	<--- BASTANTE
		}
		
		public void action () {
			pkmAnterior = trocador.getPokemon().getNome();
			trocador.trocaPokemon(pkmIndex);
		}
		
		public String description () {
			return (pkmAnterior + " de " + trocador.getNome() + " foi substituido por " + trocador.getPokemon().getNome());
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
			pk1[0] = new Pokemon("Charizard", "Fogo", 78, "Lança Chamas", "Super Aquecimento", "Explosão de Fogo", "Brasa", 10, 15, 12, 4);
			pk1[1] = new Pokemon("Venosaur", "Grama", 80, "Esfera de Energia", "Folha Navalha", "Raio Solar", "Danca de Pétalas", 9, 4, 14, 12);
			pk1[2] = new Pokemon("Blastoise", "Agua", 79, "Jato de Água", "Rajada de Bolhas", "Surf", "Hidro Bomba", 7, 5, 10, 15);
			pk1[3] = new Pokemon("Pikachu", "Eletrico", 50, "Choque do Trovão", "Faísca", "Trovão", "Descarga", 13, 6, 15, 10);
			String nomeTreinador1 = "Red";
			Treinador tr1 = new Treinador(nomeTreinador1, pk1);
			Pokemon[] pk2 = new Pokemon[6];
			pk2[0] = new Pokemon("Charizard", "Fogo", 78, "Lança Chamas", "Super Aquecimento", "Explosao de Fogo", "Brasa", 10, 15, 12, 4);
			pk2[1] = new Pokemon("Venosaur", "Grama", 80, "Esfera de Energia", "Folha Navalha", "Raio Solar", "Danca de Petalas", 9, 4, 14, 12);
			pk2[2] = new Pokemon("Blastoise", "Agua", 79, "Jato de Agua", "Rajada de Bolhas", "Surf", "Hidro Bomba", 7, 5, 10, 15);
			pk2[3] = new Pokemon("Pikachu", "Eletrico", 50, "Choque do Trovao", "Faisca", "Trovao", "Descarga", 13, 6, 15, 10);
			String nomeTreinador2 = "Green";
			Treinador tr2 = new Treinador(nomeTreinador2, pk2);
			/*Coloca os dois treinadores e seus pokemons na batalha*/
			setarBatalha(tr1, tr2);
			
			/*Inicio dos eventos*/
			addEvent(new TrocarPokemon(1, tr2));
			addEvent(new Atacar(2, nomeTreinador1));
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
