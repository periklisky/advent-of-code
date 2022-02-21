package day22;

public class EffectiveSpell extends Spell {
	public final int effect;
	public int timer;

	public EffectiveSpell(String name, int mana, int damage, int heal, int armor, int effect) {
		super(name, mana, damage, heal, armor);
		this.effect = effect;
		this.timer = effect;
	}
	
	public int getEffect() {
		return effect;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public void decreaseTimer() {
		timer--;
	}
	
	public boolean isActive() {
		return timer > 0;
	}
}
