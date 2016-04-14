

public class Pokemon {
	private String nome;
	private String tipo;
	private int hp;
	private Ataque[] atk = new Ataque[4];
	
	public Pokemon (String nome, String tipo, int hp, String[] nomeAtk, int[] dano) {
		this.nome = nome;
		this.tipo = tipo;
		this.hp = hp;
		for (int i = 0; i < 4; i++) {
			atk[i] = new Ataque (nomeAtk[i], dano[i]);
		}
	}
	
	public void mudaHP (int hp) {
		this.hp += hp;
	}
	
	public int atacar (int i) {
		System.out.println(atk[i].getNome() + "!");
		return atk[i].getDano();
	}
}
