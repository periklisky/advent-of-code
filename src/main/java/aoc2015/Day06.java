// --- Day 6: Probably a Fire Hazard ---
// Because your neighbors keep defeating you in the holiday house decorating contest year after year, you've decided to deploy 
// one million lights in a 1000x1000 grid.
// 
// Furthermore, because you've been especially nice this year, Santa has mailed you instructions on how to display 
// the ideal lighting configuration.
// 
// Lights in your grid are numbered from 0 to 999 in each direction; the lights at each corner are at 0,0, 0,999, 999,999, and 999,0. 
// The instructions include whether to turn on, turn off, or toggle various inclusive ranges given as coordinate pairs. 
// Each coordinate pair represents opposite corners of a rectangle, inclusive; a coordinate pair like 0,0 through 2,2 therefore 
// refers to 9 lights in a 3x3 square. The lights all start turned off.
// 
// To defeat your neighbors this year, all you have to do is set up your lights by doing the instructions Santa sent you in order.
// 
// For example:
// 
// turn on 0,0 through 999,999 would turn on (or leave on) every light.
// toggle 0,0 through 999,0 would toggle the first line of 1000 lights, turning off the ones that were on, and 
// turning on the ones that were off.
// turn off 499,499 through 500,500 would turn off (or leave off) the middle four lights.
// After following the instructions, how many lights are lit?
// 
// 543903

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utils.Utils;

public class Day06 {
	private static String INPUT_NAME = "aoc2015_day06.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		final int gridSize = 1000;
		List<List<Boolean>> grid = new ArrayList<>(gridSize);
		for (int i = 0; i < gridSize; i++) {
			List<Boolean> tmp = new ArrayList<>(Arrays.asList(new Boolean[gridSize]));
			Collections.fill(tmp, false);
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
		for (int i=0; i<gridSize; i++) {
			for (int j=0; j<gridSize; j++) {
				if (grid.get(i).get(j)) count++;
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

	private static void toggle(Point p1, Point p2, List<List<Boolean>> grid) {
		int start1 = p1.getX() > p2.getX() ? p2.getX() : p1.getX();
		int end1 = p1.getX() > p2.getX() ? p1.getX() : p2.getX();
		int start2 = p1.getY() > p2.getY() ? p2.getY() : p1.getY();
		int end2 = p1.getY() > p2.getY() ? p1.getY() : p2.getY();
		
		for (int i = start1; i <= end1; i++) {
			List<Boolean> list = grid.get(i);
			for (int j = start2; j <= end2; j++) {
				list.set(j, !list.get(j));
			}
			grid.set(i, list);
		}
	}

	private static void turnOn(Point p1, Point p2, List<List<Boolean>> grid) {
		int start1 = p1.getX() > p2.getX() ? p2.getX() : p1.getX();
		int end1 = p1.getX() > p2.getX() ? p1.getX() : p2.getX();
		int start2 = p1.getY() > p2.getY() ? p2.getY() : p1.getY();
		int end2 = p1.getY() > p2.getY() ? p1.getY() : p2.getY();
		
		for (int i = start1; i <= end1; i++) {
			List<Boolean> list = grid.get(i);
			for (int j = start2; j <= end2; j++) {
				list.set(j, true);
			}
			grid.set(i, list);
		}

	}

	private static void turnOff(Point p1, Point p2, List<List<Boolean>> grid) {
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
			List<Boolean> list = grid.get(i);
			for (int j = start2; j <= end2; j++) {
				list.set(j, false);
			}
			grid.set(i, list);
		}
	}
}
