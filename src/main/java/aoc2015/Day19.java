// --- Day 19: Medicine for Rudolph ---
// Rudolph the Red-Nosed Reindeer is sick! His nose isn't shining very brightly, and he needs medicine.
// 
// Red-Nosed Reindeer biology isn't similar to regular reindeer biology; Rudolph is going to need custom-made medicine. Unfortunately,
// Red-Nosed Reindeer chemistry isn't similar to regular reindeer chemistry, either.
// 
// The North Pole is equipped with a Red-Nosed Reindeer nuclear fusion/fission plant, capable of constructing any Red-Nosed Reindeer 
// molecule you need. It works by starting with some input molecule and then doing a series of replacements, one per step, 
// until it has the right molecule.
// 
// However, the machine has to be calibrated before it can be used. Calibration involves determining the number of molecules 
// that can be generated in one step from a given starting point.
// 
// For example, imagine a simpler machine that supports only the following replacements:
// 
// H => HO
// H => OH
// O => HH
// Given the replacements above and starting with HOH, the following molecules could be generated:
// 
// HOOH (via H => HO on the first H).
// HOHO (via H => HO on the second H).
// OHOH (via H => OH on the first H).
// HOOH (via H => OH on the second H).
// HHHH (via O => HH).
// So, in the example above, there are 4 distinct molecules (not five, because HOOH appears twice) after one replacement from HOH. 
// Santa's favorite molecule, HOHOHO, can become 7 distinct molecules (over nine replacements: six from H, and three from O).
// 
// The machine replaces without regard for the surrounding characters. For example, given the string H2O, the transition H => OO would 
// result in OO2O.
// 
// Your puzzle input describes all of the possible replacements and, at the bottom, the medicine molecule for which you need to calibrate 
// the machine. How many distinct molecules can be created after all the different ways you can do one replacement on the medicine molecule?

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import utils.Utils;

public class Day19 {
	private static final String INPUT_NAME = "aoc2015_day19.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		String input = "";
		Map<String, List<String>> replacements = new HashMap<>();
		try {
			String line;
			List<String> list = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				if (line.contains("=>")) {
					String tokens[] = line.split(" => ");

					if (!replacements.containsKey(tokens[0])) {
						list = new ArrayList<>();
						list.add(tokens[1]);
					} else {
						replacements.get(tokens[0]).add(tokens[1]);
					}

					replacements.put(tokens[0], list);
				}
				input = line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Set<String> distinctMolecules = new HashSet<>();
		for (Entry<String, List<String>> replacement : replacements.entrySet()) {
			String key = replacement.getKey();
//			System.out.println(key + ": ");
			List<String> values = replacement.getValue();

			for (int i = 0; i < values.size(); i++) {
				String value = values.get(i);
//				System.out.println(" " + value);
				int index = -1;
				while ((index = input.indexOf(key, index + 1)) != -1) {
					StringBuilder sb = new StringBuilder();
					sb.append(input.substring(0, index));
					sb.append(value);
					sb.append(input.substring(index + 1, input.length()));
					distinctMolecules.add(sb.toString());
				}
			}
		}
		System.out.println(distinctMolecules.size());
	}
}
