// --- Part Two ---
// The next year, to speed up the process, Santa creates a robot version of himself, Robo-Santa, to deliver presents with him.
// 
// Santa and Robo-Santa start at the same location (delivering two presents to the same starting house), then 
// take turns moving based on instructions from the elf, who is eggnoggedly reading from the same script as the previous year.
// 
// For example:
// 
// ^v delivers presents to 3 houses, because Santa goes north, and then Robo-Santa goes south.
// ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end up back where they started.
// ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one direction and Robo-Santa going the other.
//
// This year, how many houses receive at least one present?
// 2631

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import aoc2015.Day03.Position;
import utils.Utils;

public class Day03_part2 {
	private static final String INPUT_NAME = "aoc2015_day03.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		Set<Position> set = new HashSet<>();
		String line;
		Position santaPos = new Position(0, 0);
		Position roboPos = new Position(0, 0);
		set.add(santaPos);
		try {
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					if (i % 2 == 1) {
						santaPos = Day03.findNewPosition(santaPos, line.charAt(i));
						set.add(santaPos);
					} else {
						roboPos = Day03.findNewPosition(roboPos, line.charAt(i));
						set.add(roboPos);
					}
				}
			}
			System.out.println(set.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
