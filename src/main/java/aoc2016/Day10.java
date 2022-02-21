// --- Day 10: Balance Bots ---
// You come upon a factory in which many robots are zooming around handing small microchips to each other.
// 
// Upon closer examination, you notice that each bot only proceeds when it has two microchips, and once it does, 
// it gives each one to a different bot or puts it in a marked "output" bin. Sometimes, bots take microchips 
// from "input" bins, too.
// 
// Inspecting one of the microchips, it seems like they each contain a single number; the bots must use some logic 
// to decide what to do with each chip. You access the local control computer and download the bots' instructions 
// (your puzzle input).
// 
// Some of the instructions specify that a specific-valued microchip should be given to a specific bot; 
// the rest of the instructions indicate what a given bot should do with its lower-value or higher-value chip.
// 
// For example, consider the following instructions:
// 
// value 5 goes to bot 2
// bot 2 gives low to bot 1 and high to bot 0
// value 3 goes to bot 1
// bot 1 gives low to output 1 and high to bot 0
// bot 0 gives low to output 2 and high to output 0
// value 2 goes to bot 2
// Initially, bot 1 starts with a value-3 chip, and bot 2 starts with a value-2 chip and a value-5 chip.
// Because bot 2 has two microchips, it gives its lower one (2) to bot 1 and its higher one (5) to bot 0.
// Then, bot 1 has two microchips; it puts the value-2 chip in output 1 and gives the value-3 chip to bot 0.
// Finally, bot 0 has two microchips; it puts the 3 in output 2 and the 5 in output 0.
// In the end, output bin 0 contains a value-5 microchip, output bin 1 contains a value-2 microchip, and output 
// bin 2 contains a value-3 microchip. In this configuration, bot number 2 is responsible for comparing value-5 
// microchips with value-2 microchips.
// 
// Based on your instructions, what is the number of the bot that is responsible for comparing value-61 microchips 
// with value-17 microchips?
// 141


// --- Part Two ---
// What do you get if you multiply together the values of one chip in each of outputs 0, 1, and 2?
// 1209

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Utils;

public class Day10 {
	private static final String INPUT_NAME = "aoc2016_day10.txt";
	private static Map<Integer, List<Integer>> bots = new HashMap<>();
	private static Map<Integer, Integer> outputs = new HashMap<>();

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		String line;
		List<String> instructions = new ArrayList<>();
		try {
			while ((line = reader.readLine()) != null) {
				instructions.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		ListIterator<String> it = instructions.listIterator();
		while (it.hasNext()) {
			String instruction = it.next();
			if (!parseInstruction(instruction)) {
				continue;
			}
			
			it.remove();
			
			if (existsBotWithTwoMicrochips() || !it.hasNext())
				it = instructions.listIterator();
		}
		System.out.println(outputs.get(0) * outputs.get(1) * outputs.get(2));
	}
	
	private static boolean parseInstruction(String instruction) {
		if (instruction.contains("value"))
			return parseValueGoesToInstruction(instruction);

		return parseBotGivesInstruction(instruction);
	}

	private static boolean existsBotWithTwoMicrochips() {
		for (Entry<Integer, List<Integer>> bot : bots.entrySet()) {
			if (bot.getValue().size() == 2)
				return true;
		}
		return false;
	}
	
	private static boolean isBotResponsible(List<Integer> chips) {
		return chips.contains(61) && chips.contains(17);
	}
	
	private static void addChipToBot(int chip, int bot) {
		if (bots.get(bot) == null) {
			List<Integer> chips = new ArrayList<>();
			chips.add(chip);
			bots.put(bot, chips);
		} else {
			bots.get(bot).add(chip);
		}
	}

	private static boolean parseBotGivesInstruction(String instruction) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(instruction);
				
		matcher.find();
		int bot = Integer.valueOf(matcher.group());

		if (bots.get(bot) == null || bots.get(bot).size() < 2)
			return false;
		
		matcher.find();
		int x = Integer.valueOf(matcher.group());
		
		List<Integer> chips = bots.get(bot);
		if (isBotResponsible(chips))
			System.out.println("Responsible bot: " + bot);
		int low = chips.get(0) < chips.get(1) ? chips.get(0) : chips.get(1);
		int high = chips.get(0) > chips.get(1) ? chips.get(0) : chips.get(1);
		
		if (instruction.contains("low to output " + x)) {
			outputs.put(x, low);
		} else {
			addChipToBot(low, x);
		}
		
		matcher.find();
		int y = Integer.valueOf(matcher.group());
		
		if (instruction.contains("high to output " + y)) {
			outputs.put(y, high);
		} else {
			addChipToBot(high, y);
		}
		
		bots.put(bot, new ArrayList<>());
		
		return true;
	}

	private static boolean parseValueGoesToInstruction(String instruction) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(instruction);

		matcher.find();
		int chip = Integer.valueOf(matcher.group());
		matcher.find();
		int bot = Integer.valueOf(matcher.group());

		addChipToBot(chip, bot);
		return true;
	}
}
