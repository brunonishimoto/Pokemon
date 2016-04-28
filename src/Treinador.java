
public class Treinador {
	private String nome;
	private Pokemon[] pokemons = new Pokemon[6];
	private int indicePokemon = 0;
	private int pkmAtivo;	//Conta quantos Pokemons ativos o treinador possui
	private int tipoTreinador;
	
	// tipoTreinador == 1: Treinador normal
	// tipoTreinador == 2: Pokemon selvagem
	
	public Treinador (String nome, int tipoTreinador, Pokemon[] pokemons) {
		int i;
		this.tipoTreinador = tipoTreinador;
		this.nome = nome;
		for (i = 0; i < pokemons.length; i++) {
			this.pokemons[i] = pokemons[i];
		}
		pkmAtivo = i;
	}
	
	public String getNome () {
		return nome;
	}
	
	// Retorna true caso o treinador seja um pokemon selvagem
	public boolean trPokemon (){
		if(tipoTreinador == 2)
			return true;
		return false;
	}
	
	//Retorna o Pokemon atual
	public Pokemon getPokemon () {
		return pokemons[indicePokemon];
	}
	
	public void trocaPokemon (int index) {
		indicePokemon = index;
	}
	
	//Retorna quantos Pokemons do treinador estão vivos
	public int getAtivo (){
		return pkmAtivo;
	}
	
	public void pkmIncapacidado (){
		pkmAtivo--;
	}
	
	//Caso o Pokemon atual seja morto, pega o próximo Pokemon vivo
	public void proxPkmAtivo (){
		for(int i = 0; i < 6; i++){
			if (pokemons[i].getHp() > 0){
				indicePokemon = i;
				return;
			}
		}
	}
}
