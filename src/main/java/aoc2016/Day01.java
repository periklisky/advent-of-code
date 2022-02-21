// --- Day 1: No Time for a Taxicab ---
// Santa's sleigh uses a very high-precision clock to guide its movements, and the clock's oscillator is regulated by stars. Unfortunately, the stars 
// have been stolen... by the Easter Bunny. To save Christmas, Santa needs you to retrieve all fifty stars by December 25th.
// 
// Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when 
// you complete the first. Each puzzle grants one star. Good luck!
// 
// You're airdropped near Easter Bunny Headquarters in a city somewhere. "Near", unfortunately, is as close as you can get - 
// the instructions on the Easter Bunny Recruiting Document the Elves intercepted start here, and nobody had time to work them out further.
// 
// The Document indicates that you should start at the given coordinates (where you just landed) and face North. Then, follow the provided sequence: 
// either turn left (L) or right (R) 90 degrees, then walk forward the given number of blocks, ending at a new intersection.
// 
// There's no time to follow such ridiculous instructions on foot, though, so you take a moment and work out the destination. 
// Given that you can only walk on the street grid of the city, how far is the shortest path to the destination?
// 
// For example:
// 
// Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5 blocks away.
// R2, R2, R2 leaves you 2 blocks due South of your starting position, which is 2 blocks away.
// R5, L5, R5, R3 leaves you 12 blocks away.
// How many blocks away is Easter Bunny HQ?
// 298

// --- Part Two ---
// Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the first location you visit twice.
// 
// For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.
// 
// How many blocks away is the first location you visit twice?
// 158

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.Utils;

public class Day01 {
	private static final String INPUT_NAME = "aoc2016_day01.txt";

	enum Direction {
		NORTH, EAST, SOUTH, WEST
	}

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		List<String> instructions = new ArrayList<>();

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(", ");

				for (int i = 0; i < tokens.length; i++)
					instructions.add(tokens[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Position position = new Position(0, 0);
		Direction direction = Direction.NORTH;

		for (int i = 0; i < instructions.size(); i++) {
			direction = getDirection(direction, instructions.get(i));
			position = getPosition(position, direction, instructions.get(i));
		}

		System.out.println(Math.abs(position.getX()) + Math.abs(position.getY()));

		position.setX(0);
		position.setY(0);
		direction = Direction.NORTH;
		Set<Position> positions = new HashSet<>();

		for (int i = 0; i < instructions.size(); i++) {
			int x = position.getX();
			int y = position.getY();
			direction = getDirection(direction, instructions.get(i));
			position = getPosition(position, direction, instructions.get(i));

			int x_start = 0, x_end = 0; 
			int y_start = 0, y_end = 0;
			switch (direction) {
			case NORTH:
				x_start = x_end = x;
				y_start = y + 1;
				y_end = position.getY();
				break;
			case EAST:
				x_start = x + 1;
				x_end = position.getX();
				y_start = y_end = y;
				break;
			case SOUTH:
				x_start = x_end = x;
				y_start = position.getY();
				y_end = y - 1;
				break;
			case WEST:
				x_start = position.getX();
				x_end = x - 1;
				y_start = y_end = y;
				break;
			default:
				break;
			}

			for (int m = x_start; m <= x_end; m++) {
				for (int k = y_start; k <= y_end; k++) {					
					if (!positions.add(new Position(m, k))) {
						System.out.println(Math.abs(m) + Math.abs(k));
//						System.out.println("(" + m + ", " + k + ")");
						return;
					}
				}
			}
		}
	}

	private static Position getPosition(Position previousPosition, Direction direction, String instruction) {
		int x = previousPosition.getX();
		int y = previousPosition.getY();
		int offset = Integer.parseInt(instruction.replaceAll("[^0-9]", ""));

		switch (direction) {
		case NORTH:
			previousPosition.setX(x);
			previousPosition.setY(y + offset);
			break;
		case EAST:
			previousPosition.setX(x + offset);
			previousPosition.setY(y);
			break;
		case SOUTH:
			previousPosition.setX(x);
			previousPosition.setY(y - offset);
			break;
		case WEST:
			previousPosition.setX(x - offset);
			previousPosition.setY(y);
			break;
		default:
			throw new IllegalArgumentException();
		}
		
		return previousPosition;
	}

	private static Direction getDirection(Direction previousDirection, String input) {
		if (input.contains("R")) {
			switch (previousDirection) {
			case EAST:
				return Direction.SOUTH;
			case SOUTH:
				return Direction.WEST;
			case WEST:
				return Direction.NORTH;
			case NORTH:
			default:
				return Direction.EAST;
			}
		}

		switch (previousDirection) {
		case WEST:
			return Direction.SOUTH;
		case SOUTH:
			return Direction.EAST;
		case EAST:
			return Direction.NORTH;
		case NORTH:
		default:
			return Direction.WEST;
		}
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

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
		
		public void print() {
			System.out.println("(" + x + ", " + y + ")");
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
			if (obj == null || getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
//			System.out.println("(" + this.x + ", " + this.y + ")  //  (" + other.x + ", " + other.y + ")  " + (x == other.x && y == other.y));
			return x == other.x && y == other.y;
		}
	}
}
