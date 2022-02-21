// --- Day 3: Perfectly Spherical Houses in a Vacuum ---
// Santa is delivering presents to an infinite two-dimensional grid of houses.
// 
// He begins by delivering a present to the house at his starting location, and then 
// an elf at the North Pole calls him via radio and tells him where to move next. 
// Moves are always exactly one house to the north (^), south (v), east (>), or west (<). 
// After each move, he delivers another present to the house at his new location.
// 
// However, the elf back at the north pole has had a little too much eggnog, and so his directions are a little off, 
// and Santa ends up visiting some houses more than once.
// 
// For example:
// 
// > delivers presents to 2 houses: one at the starting location, and one to the east.
// ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
// ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
//
// How many houses receive at least one present?


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

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.Utils;

public class Day03 {
	private static String INPUT_NAME = "aoc2015_day03.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		Position currentPosition = new Position(0, 0);
		Map<Position, Integer> visited = new HashMap<>();
		visited.put(currentPosition, 1);

		Set<Position> set = new HashSet<>();
		Position santaPos = new Position(0, 0);
		Position roboPos = new Position(0, 0);
		set.add(santaPos);
		
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					currentPosition = findNewPosition(currentPosition, line.charAt(i));
					if (visited.get(currentPosition) == null) {
						visited.put(currentPosition, 1);
					} else {
						visited.put(currentPosition, visited.get(currentPosition) + 1);
					}
					
					if (i % 2 == 1) {
						santaPos = Day03.findNewPosition(santaPos, line.charAt(i));
						set.add(santaPos);
					} else {
						roboPos = Day03.findNewPosition(roboPos, line.charAt(i));
						set.add(roboPos);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Part 1: " + visited.size());
		System.out.println("Part 2: " + set.size());
	}

	public static Position findNewPosition(Position p, char c) {
		int x = p.x;
		int y = p.y;

		switch (c) {
		case '^':
			y++;
			break;
		case 'v':
			y--;
			break;
		case '<':
			x--;
			break;
		case '>':
			x++;
			break;
		default:
			break;
		}
		return new Position(x, y);
	}

	public static class Position {
		private int x;
		private int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

	}
}
