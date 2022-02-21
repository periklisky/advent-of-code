// --- Part Two ---
// You just finish implementing your winning light pattern when you realize you mistranslated Santa's message from Ancient Nordic Elvish.
// 
// The light grid you bought actually has individual brightness controls; each light can have a brightness of zero or more. 
// The lights all start at zero.
// 
// The phrase turn on actually means that you should increase the brightness of those lights by 1.
// The phrase turn off actually means that you should decrease the brightness of those lights by 1, to a minimum of zero.
// The phrase toggle actually means that you should increase the brightness of those lights by 2.
// 
// For example:
// turn on 0,0 through 0,0 would increase the total brightness by 1.
// toggle 0,0 through 999,999 would increase the total brightness by 2000000.
// 
// What is the total brightness of all lights combined after following Santa's instructions?
// 14687245

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utils.Utils;

public class Day06_part2 {
	private static String INPUT_NAME = "aoc2015_day06.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		final int gridSize = 1000;
		List<List<Integer>> grid = new ArrayList<>(gridSize);
		for (int i = 0; i < gridSize; i++) {
			List<Integer> tmp = new ArrayList<>(Arrays.asList(new Integer[gridSize]));
			Collections.fill(tmp, 0);
			grid.add(tmp);
		}

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(" ");
				int c1 = 0, c2 = 2;
				switch (tokens.length) {
				case 4:
					c1 = 1;
					c2 = 3;
					break;
				case 5:
					c1 = 2;
					c2 = 4;
					break;
				default:
					throw new IllegalArgumentException("Wrong input: " + line);
				}

				String coordinates[] = tokens[c1].split(",");
				Point p1 = new Point(Integer.valueOf(coordinates[0]), Integer.valueOf(coordinates[1]));
				coordinates = tokens[c2].split(",");
				Point p2 = new Point(Integer.valueOf(coordinates[0]), Integer.valueOf(coordinates[1]));

				switch (tokens.length) {
				case 4:
					toggle(p1, p2, grid);
					break;
				case 5:
					if (tokens[1].equals("on"))
						turnOn(p1, p2, grid);
					else if (tokens[1].equals("off"))
						turnOff(p1, p2, grid);
					break;
				default:
					break;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int count = 0;
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				count += grid.get(i).get(j);
			}
		}
		System.out.println(count);

	}

	public static class Point {
		private int x;
		private int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
	}

	private static void toggle(Point p1, Point p2, List<List<Integer>> grid) {
		int start1 = p1.getX() > p2.getX() ? p2.getX() : p1.getX();
		int end1 = p1.getX() > p2.getX() ? p1.getX() : p2.getX();
		int start2 = p1.getY() > p2.getY() ? p2.getY() : p1.getY();
		int end2 = p1.getY() > p2.getY() ? p1.getY() : p2.getY();

		for (int i = start1; i <= end1; i++) {
			List<Integer> list = grid.get(i);
			for (int j = start2; j <= end2; j++) {
				list.set(j, list.get(j) + 2);
			}
			grid.set(i, list);
		}
	}

	private static void turnOn(Point p1, Point p2, List<List<Integer>> grid) {
		int start1 = p1.getX() > p2.getX() ? p2.getX() : p1.getX();
		int end1 = p1.getX() > p2.getX() ? p1.getX() : p2.getX();
		int start2 = p1.getY() > p2.getY() ? p2.getY() : p1.getY();
		int end2 = p1.getY() > p2.getY() ? p1.getY() : p2.getY();

		for (int i = start1; i <= end1; i++) {
			List<Integer> list = grid.get(i);
			for (int j = start2; j <= end2; j++) {
				list.set(j, list.get(j) + 1);
			}
			grid.set(i, list);
		}

	}

	private static void turnOff(Point p1, Point p2, List<List<Integer>> grid) {
		int start1, end1, start2, end2;

		if (p1.getX() >= p2.getX()) {
			start1 = p2.getX();
			end1 = p1.getX();
		} else {
			start1 = p1.getX();
			end1 = p2.getX();
		}

		if (p1.getY() >= p2.getY()) {
			start2 = p2.getY();
			end2 = p1.getY();
		} else {
			start2 = p1.getY();
			end2 = p2.getY();
		}

		for (int i = start1; i <= end1; i++) {
			List<Integer> list = grid.get(i);
			for (int j = start2; j <= end2; j++) {
				list.set(j, list.get(j) - 1 < 0 ? 0 : list.get(j) - 1);
			}
			grid.set(i, list);
		}
	}
}
