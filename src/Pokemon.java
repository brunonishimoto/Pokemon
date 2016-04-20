

public class Pokemon {
	private String nome;
	private String tipo;
	private int hp;
	private Ataque[] atk = new Ataque[4];
	
	// 19-04: alteracao dos vetores que eram recebidos como parametros por variaveis normais
	public Pokemon (String nome, String tipo, int hp, String nomeAtk1, String nomeAtk2,
					String nomeAtk3, String nomeAtk4, int dano1, int dano2, int dano3, int dano4) {
		this.nome = nome;
		this.tipo = tipo;
		this.hp = hp;
		atk[0] = new Ataque(nomeAtk1, dano1);
		atk[1] = new Ataque(nomeAtk2, dano2);
		atk[2] = new Ataque(nomeAtk3, dano3);
		atk[3] = new Ataque(nomeAtk4, dano4);
	}
	
	public void mudaHP (int hp) {
		this.hp += hp;
		if (this.hp < 0)
			this.hp = 0;
	}
	
	public int atacar (int i) {
		return atk[i].getDano();
	}
	
	public String ataque (int i){
		return (atk[i].getNome());
	}
	
	/*retorna hp atual dos pokemons*/ // necessário para mostrar na descricao dos ataques / cura(potion) 
	public int getHp (){
		return hp;
	}
	
	/*retorna nome do pokemon*/
	public String getNome (){
		return nome;
	}
	
}
