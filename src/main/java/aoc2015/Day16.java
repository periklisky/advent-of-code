// --- Day 16: Aunt Sue ---
// Your Aunt Sue has given you a wonderful gift, and you'd like to send her a thank you card. 
//  However, there's a small problem: she signed it "From, Aunt Sue".
// 
// You have 500 Aunts named "Sue".
// 
// So, to avoid sending the card to the wrong person, you need to figure out which Aunt Sue (which you conveniently number 1 to 500, for sanity)
// gave you the gift. You open the present and, as luck would have it, good ol' Aunt Sue got you a My First Crime Scene Analysis Machine! 
// Just what you wanted. Or needed, as the case may be.
// 
// The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a few specific compounds in a given sample, 
// as well as how many distinct kinds of those compounds there are. According to the instructions, these are what the MFCSAM can detect:
// 
// children, by human DNA age analysis.
// cats. It doesn't differentiate individual breeds.
// Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and vizslas.
// goldfish. No other kinds of fish.
// trees, all in one group.
// cars, presumably by exhaust or gasoline or something.
// perfumes, which is handy, since many of your Aunts Sue wear a few kinds.
// In fact, many of your Aunts Sue have many of these. You put the wrapping from the gift into the MFCSAM. It beeps inquisitively at you 
// a few times and then prints out a message on ticker tape:
// 
// children: 3
// cats: 7
// samoyeds: 2
// pomeranians: 3
// akitas: 0
// vizslas: 0
// goldfish: 5
// trees: 3
// cars: 2
// perfumes: 1
// You make a list of the things you can remember about each Aunt Sue. Things missing from your list aren't zero - you simply don't remember 
// the value.
// 
// What is the number of the Sue that got you the gift?
// 40

// --- Part Two ---
// As you're about to send the thank you note, something in the MFCSAM's instructions catches your eye. Apparently, 
// it has an outdated retroencabulator, and so the output from the machine isn't exact values - some of them indicate ranges.
// 
// In particular, the cats and trees readings indicates that there are greater than that many  (due to the unpredictable 
// nuclear decay of cat dander and tree pollen), while the pomeranians and goldfish readings indicate that there are fewer than 
// that many (due to the modial interaction of magnetoreluctance).
// 
// What is the number of the real Aunt Sue?
// 
// Your puzzle answer was 241.

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Day16 {
	private static final String INPUT_NAME = "input_day16.txt";

	private static final int NUM_CHILDREN = 3;
	private static final int NUM_CATS = 7;
	private static final int NUM_SAMOYEDS = 2;
	private static final int NUM_POMERANIANS = 3;
	private static final int NUM_AKITAS = 0;
	private static final int NUM_VIZSLAS = 0;
	private static final int NUM_GOLDFISH = 5;
	private static final int NUM_TREES = 3;
	private static final int NUM_CARS = 2;
	private static final int NUM_PERFUMES = 1;

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		Map<String, Map<String, Integer>> aunts = new HashMap<>();

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(", ");
				String name = tokens[0].split(": ")[0];

				Map<String, Integer> assets = new HashMap<>();
				assets.put(tokens[0].split(": ")[1], Integer.valueOf(tokens[0].split(": ")[2]));
				assets.put(tokens[1].split(": ")[0], Integer.valueOf(tokens[1].split(": ")[1]));
				assets.put(tokens[2].split(": ")[0], Integer.valueOf(tokens[2].split(": ")[1]));

				aunts.put(name, assets);
			}

			Iterator<Entry<String, Map<String, Integer>>> aunt_it = aunts.entrySet().iterator();
			while (aunt_it.hasNext()) {
				Iterator<Entry<String, Integer>> assets_it = aunt_it.next().getValue().entrySet().iterator();
				boolean found = false;
				while (assets_it.hasNext()) {
					Entry<String, Integer> asset_entry = assets_it.next();
					switch (asset_entry.getKey()) {
					case "cats":
						if (asset_entry.getValue() <= NUM_CATS) 
							found = true;
						break;
					case "trees":
						if (asset_entry.getValue() <= NUM_TREES)
							found = true;
						break;

					case "pomeranians":
						if (asset_entry.getValue() >= NUM_POMERANIANS)
							found = true;
						break;
					case "goldfish":
						if (asset_entry.getValue() >= NUM_GOLDFISH)
							found = true;
						break;

					case "children":
						if (asset_entry.getValue() != NUM_CHILDREN)
							found = true;
						break;
					case "samoyeds":
						if (asset_entry.getValue() != NUM_SAMOYEDS)
							found = true;
						break;
					case "akitas":
						if (asset_entry.getValue() != NUM_AKITAS)
							found = true;
						break;
					case "vizslas":
						if (asset_entry.getValue() != NUM_VIZSLAS)
							found = true;
						break;
					case "cars":
						if (asset_entry.getValue() != NUM_CARS)
							found = true;
						break;
					case "perfumes":
						if (asset_entry.getValue() != NUM_PERFUMES)
							found = true;
						break;
					default:
						break;
					}
					
					if (found) {
						aunt_it.remove();
						break;
					}
				}
			}

			for (Entry<String, Map<String, Integer>> aunt : aunts.entrySet()) {
				if (aunt.getValue() != null) {
					System.out.println(aunt.getKey() + " " + aunt.getValue());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
