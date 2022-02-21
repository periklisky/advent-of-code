// --- Day 15: Science for Hungry People ---
// Today, you set out on the task of perfecting your milk-dunking cookie recipe. 
// All you have to do is find the right balance of ingredients.
// 
// Your recipe leaves room for exactly 100 teaspoons of ingredients. You make a list of the remaining ingredients you could use to finish 
// the recipe (your puzzle input) and their properties per teaspoon:
// 
// capacity (how well it helps the cookie absorb milk)
// durability (how well it keeps the cookie intact when full of milk)
// flavor (how tasty it makes the cookie)
// texture (how it improves the feel of the cookie)
// calories (how many calories it adds to the cookie)
// 
// You can only measure ingredients in whole-teaspoon amounts accurately, and you have to be accurate so you can reproduce your results 
// in the future. The total score of a cookie can be found by adding up each of the properties (negative totals become 0) and 
// then multiplying together everything except calories.
// 
// For instance, suppose you have these two ingredients:
// 
// Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
// Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
// Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons of cinnamon (because the amounts of each ingredient must add up to 100) 
// would result in a cookie with the following properties:
// 
// A capacity of 44*-1 + 56*2 = 68
// A durability of 44*-2 + 56*3 = 80
// A flavor of 44*6 + 56*-2 = 152
// A texture of 44*3 + 56*-1 = 76
// Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now) results in a total score of 62842880, 
// which happens to be the best score possible given these ingredients. 
// If any properties had produced a negative total, it would have instead become zero, causing the whole score to multiply to zero.
//
// Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you can make?
// 21367368

// --- Part Two ---
// Your cookie recipe becomes wildly popular! Someone asks if you can make another recipe that has exactly 500 calories per cookie 
// (so they can use it as a meal replacement). Keep the rest of your award-winning process the same 
// (100 teaspoons, same ingredients, same scoring system).
//
// For example, given the ingredients above, if you had instead selected 40 teaspoons of butterscotch and 60 teaspoons of cinnamon 
// (which still adds to 100), the total calorie count would be 40*8 + 60*3 = 500. The total score would go down, though: only 57600000, 
// the best you can do in such trying circumstances.
//
// Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you 
// can make with a calorie total of 500?

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class Day15 {
	private static final String INPUT_NAME = "aoc2015_day15.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);
		List<Ingredient> ingredients = new ArrayList<>();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(": ");
				String name = tokens[0];

				String properties[] = tokens[1].split(", ");

				Map<String, Integer> ingredient_prop = new HashMap<>();

				for (int i = 0; i < properties.length; i++) {
					String key = properties[i].split(" ")[0];
					String value = properties[i].split(" ")[1];
					ingredient_prop.put(key, Integer.valueOf(value));
				}

				ingredients.add(new Ingredient(name, ingredient_prop.get("capacity"), ingredient_prop.get("durability"),
						ingredient_prop.get("flavor"), ingredient_prop.get("texture"),
						ingredient_prop.get("calories")));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int max = 0;
		for (int i = 0; i <= 100; i++) {
			for (int j = 0; j <= 100 - i; j++) {
				for (int k = 0; k <= 100 - j - i; k++) {
					for (int l = 0; l <= 100 - k - j - i; l++) {
						int calories = (i * ingredients.get(0).getCalories()) + (j * ingredients.get(1).getCalories())
								+ (k * ingredients.get(2).getCalories()) + (l * ingredients.get(3).getCalories());

						if (calories == 500) {
							int capacity = (i * ingredients.get(0).getCapacity()) + (j * ingredients.get(1).getCapacity()) 
									+ (k * ingredients.get(2).getCapacity()) + (l * ingredients.get(3).getCapacity());
							capacity = (capacity > 0) ? capacity : 0;

							int durability = (i * ingredients.get(0).getDurability()) + (j * ingredients.get(1).getDurability())
									+ (k * ingredients.get(2).getDurability())+ (l * ingredients.get(3).getDurability());
							durability = (durability > 0) ? durability : 0;

							int flavor = (i * ingredients.get(0).getFlavor()) + (j * ingredients.get(1).getFlavor())
									+ (k * ingredients.get(2).getFlavor()) + (l * ingredients.get(3).getFlavor());
							flavor = (flavor > 0) ? flavor : 0;

							int texture = (i * ingredients.get(0).getTexture()) + (j * ingredients.get(1).getTexture())
									+ (k * ingredients.get(2).getTexture()) + (l * ingredients.get(3).getTexture());
							texture = (texture > 0) ? texture : 0;

							int totalScore = capacity * durability * flavor * texture;
							max = (totalScore > max) ? totalScore : max;
						}
					}
				}
			}
		}
		System.out.println(max);
	}

	public static class Ingredient {
		private String name;
		private int capacity;
		private int durability;
		private int flavor;
		private int texture;
		private int calories;

		public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
			this.name = name;
			this.capacity = capacity;
			this.durability = durability;
			this.flavor = flavor;
			this.texture = texture;
			this.calories = calories;
		}

		public String getName() {
			return name;
		}

		public int getCapacity() {
			return capacity;
		}

		public int getDurability() {
			return durability;
		}

		public int getFlavor() {
			return flavor;
		}

		public int getTexture() {
			return texture;
		}

		public int getCalories() {
			return calories;
		}

		@Override
		public String toString() {
			return name + ": capacity=" + capacity + ", durability=" + durability + ", flavor=" + flavor + ", texture="
					+ texture + ", calories=" + calories;
		}
	}
}
