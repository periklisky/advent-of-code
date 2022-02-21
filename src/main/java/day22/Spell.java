package day22;

public class Spell {
	private final String name;
	private final int mana;
	private final int damage;
	private final int heal;
	private final int armor;
	
	public Spell(String name, int mana, int damage, int heal, int armor) {
		this.name = name;
		this.mana = mana;
		this.damage = damage;
		this.heal = heal;
		this.armor = armor;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getHeal() {
		return heal;
	}
	
	public int getArmor() {
		return armor;
	}

	public void print() {
		System.out.println("spellName: " + name + ", mana: " + mana);
	}
}
