

public class Pokemon {
	private String nome;
	private String tipo;
	private int hpAtual;
	private int maxHp;
	private Ataque[] atk = new Ataque[4];
	private static final double[][] MultiplicadorDano = { {1, 1, 1, 1, 1, 0.5, 1, 0, 0.5, 1, 1, 1, 1, 1, 1, 1, 1, 1},
														  {2, 1, 0.5, 0.5, 1, 2, 0.5, 0, 2, 1, 1, 1, 1, 0.5, 2, 1, 2, 0.5},
														  {1, 2, 1, 1, 1, 0.5, 2, 1, 0.5, 1, 1, 2, 0.5, 1, 1, 1, 1, 1},
														  {1, 1, 1, 0.5, 0.5, 0.5, 1, 0.5, 1, 1, 2, 0.5, 1, 1, 1, 1, 1}
														};		/**FALTA TERMINAR HEHE **/						
	
	public Pokemon (String nome, String tipo, int hp, String nomeAtk1, String nomeAtk2,
					String nomeAtk3, String nomeAtk4, int dano1, int dano2, int dano3, int dano4) {
		this.nome = nome;
		this.tipo = tipo;
		hpAtual = maxHp = hp;
		atk[0] = new Ataque(nomeAtk1, dano1);
		atk[1] = new Ataque(nomeAtk2, dano2);
		atk[2] = new Ataque(nomeAtk3, dano3);
		atk[3] = new Ataque(nomeAtk4, dano4);
	}
	
	//Recebe o tipo do Pokemon a ser atacado, e retorna o multiplicador que ele recebe do Pokémon
	public double getMultiplicador (String tipo2) {
		return (MultiplicadorDano[pegaTipo(tipo)][pegaTipo(tipo2)]);
	}
	
	//Associa o tipo do Pokemon com a linha/coluna da matriz MultiplicadorDano
	private int pegaTipo (String tipo) {
		if (tipo.equals("Normal")) {
			return 0;
		}
		if (tipo.equals("Lutador")) {
			return 1;
		}
		if (tipo.equals("Voador")) {
			return 2;
		}
		return 0;	/** ESSE AQUI É SÓ PARA NÃO FICAR COM ERRO, DEPOIS QUE FIZER DE TODOS EU TIRO */
	}
	
	public String getTipo () {
		return tipo;
	}
	
	public void mudaHP (int hp) {
		hpAtual += hp;
		if (hpAtual < 0)
			hpAtual = 0;
		if (hpAtual > maxHp)
			hpAtual = maxHp;
	}
	
	public int atacar (int i) {
		return atk[i].getDano();
	}
	
	public String ataque (int i){
		return (atk[i].getNome());
	}
	
	/*retorna hp atual dos pokemons*/ // necessário para mostrar na descricao dos ataques / cura(potion) 
	public int getHp (){
		return hpAtual;
	}
	
	/*retorna nome do pokemon*/
	public String getNome (){
		return nome;
	}
	
}
