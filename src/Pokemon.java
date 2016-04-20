

public class Pokemon {
	private String nome;
	private String tipo;
	private int hpAtual;
	private int maxHp;
	private Ataque[] atk = new Ataque[4];
	
	// 19-04: alteracao dos vetores que eram recebidos como parametros por variaveis normais
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
