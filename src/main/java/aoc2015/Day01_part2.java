// --- Part Two ---
// Now, given the same instructions, find the position of the first character that causes him to enter the basement (floor -1). 
// The first character in the instructions has position 1, the second character has position 2, and so on.
// 
// For example:
// 
// ) causes him to enter the basement at character position 1.
// ()()) causes him to enter the basement at character position 5.
//
// What is the position of the character that causes Santa to first enter the basement?
// 1783

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;

import utils.Utils;

public class Day01_part2 {
	private static final String INPUT_NAME = "aoc2015_day011.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		int floor = 0;
		int count = 0;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					switch (line.charAt(i)) {
					case '(':
						floor++;
						break;
					case ')':
						floor--;
						break;
					default:
						break;
					}
					if (floor == -1) {
						System.out.println(count + i + 1);
						break;
					}
				}
				count += line.length();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}