// --- Day 21: RPG Simulator 20XX ---
// Little Henry Case got a new video game for Christmas. It's an RPG, and he's stuck on a boss. He needs to know what equipment 
// to buy at the shop. He hands you the controller.
// 
// In this game, the player (you) and the enemy (the boss) take turns attacking. The player always goes first. Each attack reduces 
// the opponent's hit points by at least 1. The first character at or below 0 hit points loses.
// 
// Damage dealt by an attacker each turn is equal to the attacker's damage score minus the defender's armor score. An attacker 
// always does at least 1 damage. So, if the attacker has a damage score of 8, and the defender has an armor score of 3, 
// the defender loses 5 hit points. If the defender had an armor score of 300, the defender would still lose 1 hit point.
// 
// Your damage score and armor score both start at zero. They can be increased by buying items in exchange for gold. You start 
// with no items and have as much gold as you need. Your total damage or armor is equal to the sum of those stats from all 
//of your items. You have 100 hit points.
// 
// Here is what the item shop is selling:
// 
// Weapons:    Cost  Damage  Armor
// Dagger        8     4       0
// Shortsword   10     5       0
// Warhammer    25     6       0
// Longsword    40     7       0
// Greataxe     74     8       0
// 
// Armor:      Cost  Damage  Armor
// Leather      13     0       1
// Chainmail    31     0       2
// Splintmail   53     0       3
// Bandedmail   75     0       4
// Platemail   102     0       5
// 
// Rings:      Cost  Damage  Armor
// Damage +1    25     1       0
// Damage +2    50     2       0
// Damage +3   100     3       0
// Defense +1   20     0       1
// Defense +2   40     0       2
// Defense +3   80     0       3
// You must buy exactly one weapon; no dual-wielding. Armor is optional, but you can't use more than one. You can buy 0-2 rings 
// (at most one for each hand). You must use any items you buy. The shop only has one of each item, so you can't buy, for example, 
// two rings of Damage +3.
// 
// For example, suppose you have 8 hit points, 5 damage, and 5 armor, and that the boss has 12 hit points, 7 damage, and 2 armor:
// 
// The player deals 5-2 = 3 damage; the boss goes down to 9 hit points.
// The boss deals 7-5 = 2 damage; the player goes down to 6 hit points.
// The player deals 5-2 = 3 damage; the boss goes down to 6 hit points.
// The boss deals 7-5 = 2 damage; the player goes down to 4 hit points.
// The player deals 5-2 = 3 damage; the boss goes down to 3 hit points.
// The boss deals 7-5 = 2 damage; the player goes down to 2 hit points.
// The player deals 5-2 = 3 damage; the boss goes down to 0 hit points.
// In this scenario, the player wins! (Barely.)
// 
// You have 100 hit points. The boss's actual stats are in your puzzle input. What is the least amount of gold you can spend 
// and still win the fight?
// 91


// --- Part Two ---
// Turns out the shopkeeper is working with the boss, and can persuade you to buy whatever items he wants. The other rules still apply,
// and he still only has one of each item.
// 
// What is the most amount of gold you can spend and still lose the fight?
// 158


package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import day21.Armor;
import day21.Item;
import day21.Player;
import day21.Ring;
import day21.Weapon;
import utils.Utils;

public class Day21 {
	public static final String INPUT_NAME = "aoc2015_day21.txt";
	public static final int HIT_POINTS = 100;
	private static List<List<Item>> ringsList = new ArrayList<>();

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);
		
		int bossHitPoints = 0;
		int bossDamage = 0;
		int bossArmor = 0;
		List<Integer> bossStats = new ArrayList<>();
		
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				bossStats.add(Integer.valueOf(line.split(": ")[1]));
			}
			bossHitPoints = bossStats.get(0);
			bossDamage = bossStats.get(1);
			bossArmor = bossStats.get(2);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		List<Item> weapons = new ArrayList<>();
		weapons.add(new Weapon("Dagger", 8, 4, 0));
		weapons.add(new Weapon("Shortsword", 10, 5, 0));
		weapons.add(new Weapon("Warhammer", 25, 6, 0));
		weapons.add(new Weapon("Longsword", 40, 7, 0));
		weapons.add(new Weapon("Greataxe", 74, 8, 0));
		
		List<Item> armors = new ArrayList<>();
		armors.add(new Armor("Leather", 13, 0, 1));
		armors.add(new Armor("Chainmail", 31, 0, 2));
		armors.add(new Armor("Splintmail", 53, 0, 3));
		armors.add(new Armor("Bandedmail", 75, 0, 4));
		armors.add(new Armor("Platemail", 102, 0, 5));
		
		List<Item> rings = new ArrayList<>();
		rings.add(new Ring("Damage + 1", 25, 1, 0));
		rings.add(new Ring("Damage + 2", 50, 2, 0));
		rings.add(new Ring("Damage + 3", 100, 3, 0));
		rings.add(new Ring("Defense + 1", 20, 0, 1));
		rings.add(new Ring("Defense + 2", 40, 0, 2));
		rings.add(new Ring("Defense + 3", 80, 0, 3));
				
		Player player = new Player("player", HIT_POINTS, 0, 0);
		Player boss = new Player("boss", bossHitPoints, bossDamage, bossArmor);
		
		
		List<Item> armorsList = chooseOne(armors); 
		populateCombinations(rings); 
		
		int minCost = 999;
		int maxCost = 0;
		for (int i = 0; i < weapons.size(); i++) {
			for (int j = 0; j < armorsList.size(); j++) {
				for (int k = 0; k < ringsList.size(); k++) {
					int cost = calculateCost(player, boss, weapons.get(i), armorsList.get(j), ringsList.get(k));
					
					if (canPlayerWin(player, boss)) {
						minCost = (minCost > cost) ? cost : minCost; 
					} else {
						maxCost = (maxCost > cost) ? maxCost : cost;
					}
				}
			}
		}
		System.out.println("Least amount of gold and still win: " + minCost);
		System.out.println("Most amount of gold and still lose: " + maxCost);
	}
	
	public static boolean canPlayerWin(Player player, Player boss) {
		int playerDmg = (player.getDamage() > boss.getArmor()) ? player.getDamage() - boss.getArmor() : 1;
		int bossDmg = (boss.getDamage() > player.getArmor()) ? boss.getDamage() - player.getArmor() : 1;

		int playerTurns = boss.getHitPoints() / playerDmg;
		playerTurns = (boss.getHitPoints() % playerDmg != 0) ? playerTurns + 1 : playerTurns;
		
		int bossTurns = player.getHitPoints() / bossDmg;
		bossTurns = (player.getHitPoints() % bossDmg != 0) ? bossTurns + 1 : bossTurns;
		
		return playerTurns <= bossTurns;
	}
	
	public static int calculateCost(Player player, Player boss, Item weapon, Item armor, List<Item> rings) {
		int playerDamage = weapon.getDamage();
		int playerArmor = weapon.getArmor();
		int cost = weapon.getCost();
		
		if (armor != null) {
			playerDamage += armor.getDamage();
			playerArmor += armor.getArmor();
			cost += armor.getCost();
		}
		
		for (int i = 0; i < rings.size(); i++) {
			playerDamage += rings.get(i).getDamage();
			playerArmor += rings.get(i).getArmor();
			cost += rings.get(i).getCost();
		}
		
		player.setDamage(playerDamage);
		player.setArmor(playerArmor);
		
		return cost;
	}
	
	public static void populateCombinations(List<Item> list) {
		List<Item> copy = new ArrayList<>(list);
		ListIterator<Item> it = copy.listIterator();
		Item x = it.next();
		List<Item> singleItem = new ArrayList<>();
		singleItem.add(x);
		ringsList.add(singleItem);
		
		if (copy.size() > 1) {
			it.remove();
			populateCombinations(copy);
			for (int i = 0; i < copy.size(); i++) {
				List<Item> combination = new ArrayList<>();
				combination.add(x);
				combination.add(copy.get(i));
				ringsList.add(combination);
			}
		} else {
			List<Item> emptyList = new ArrayList<>();
			ringsList.add(emptyList);
		}
	}
	
	public static List<Item> chooseOne(List<Item> list) {
		List<Item> expandedList = new ArrayList<>(list);
		expandedList.add(null);		
		return expandedList;
	}
}
