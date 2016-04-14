

public class Batalha extends Controller {
	Treinador red;
	Treinador green;
	
	public Batalha (Treinador treinador1, Treinador treinador2) {
		red = treinador1;
		green = treinador2;
	}
	
	public class Atacar extends Event {
		private int atkIndex;
		private String atacante;
		public Atacar (int index, String atacante) {
			super(3);
			atkIndex = index;
			this.atacante = atacante;
		}
		
		public void action() {
			if (red.getNome().equals(atacante)) {
				green.getPokemon().mudaHP(-red.getPokemon().atacar(atkIndex));
				return;
			}
			red.getPokemon().mudaHP(-green.getPokemon().atacar(atkIndex));
		}
	}
	
	public class TrocarPokemon extends Event {
		
	}
}
