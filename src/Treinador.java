
public class Treinador {
	private String nome;
	private Pokemon[] pokemons = new Pokemon[6];
	private int indicePokemon = 0;
	
	public String getNome() {
		return nome;
	}
	
	public Pokemon getPokemon () {
		return pokemons[indicePokemon];
	}
	
	public Treinador (String nome, Pokemon[] pokemons) {
		this.nome = nome;
		for (int i = 0; i < 5; i++) {
			this.pokemons[i] = pokemons[i];
		}
	}
	
	public void trocaPokemon (int index) {
		indicePokemon = index;
	}
	
	
}
