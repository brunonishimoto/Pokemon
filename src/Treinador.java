
public class Treinador {
	private String nome;
	private Pokemon[] pokemons = new Pokemon[6];
	private int indicePokemon = 0;
	private int pkmAtivo;
	
	public Treinador (String nome, Pokemon[] pokemons) {
		int i;
		this.nome = nome;
		for (i = 0; i < 5 && pokemons[i] != null; i++) {
			this.pokemons[i] = pokemons[i];
		}
		pkmAtivo = i;
	}
	
	public String getNome() {
		return nome;
	}
	
	public Pokemon getPokemon () {
		return pokemons[indicePokemon];
	}
	
	public void trocaPokemon (int index) {
		indicePokemon = index;
	}
	
	public int getAtivo (){
		return pkmAtivo;
	}
	
	public void pkmIncapacidado (){
		pkmAtivo--;
	}
	
	public void proxPkmAtivo (){
		for(int i = 0; i < 6; i++){
			if (pokemons[i].getHp() > 0){
				indicePokemon = i;
				return;
			}
		}
	}
}
