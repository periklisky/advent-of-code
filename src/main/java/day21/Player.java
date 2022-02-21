package day21;

public class Player {
	private String name;
	private int hitPoints;
	private int damage;
	private int armor;
	
	public Player(String name, int hitPoints, int damage, int armor) {
		this.name = name;
		this.hitPoints = hitPoints;
		this.damage = damage;
		this.armor = armor;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}
}
