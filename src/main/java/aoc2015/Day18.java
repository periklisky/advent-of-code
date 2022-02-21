// --- Day 18: Like a GIF For Your Yard ---
// After the million lights incident, the fire code has gotten stricter: now, at most ten thousand lights are allowed. You arrange them 
// in a 100x100 grid.
// 
// Never one to let you down, Santa again mails you instructions on the ideal lighting configuration. With so few lights, he says, 
// you'll have to resort to animation.
// 
// Start by setting your lights to the included initial configuration (your puzzle input). A # means "on", and a . means "off".
// 
// Then, animate your grid in steps, where each step decides the next configuration based on the current one. Each light's next state 
// (either on or off) depends on its current state and the current states of the eight lights adjacent to it (including diagonals). 
// Lights on the edge of the grid might have fewer than eight neighbors; the missing ones always count as "off".
// 
// For example, in a simplified 6x6 grid, the light marked A has the neighbors numbered 1 through 8, and the light marked B, 
// which is on an edge, only has the neighbors marked 1 through 5:
// 
// 1B5...
// 234...
// ......
// ..123.
// ..8A4.
// ..765.
// The state a light should have next is based on its current state (on or off) plus the number of neighbors that are on:
// 
// A light which is on stays on when 2 or 3 neighbors are on, and turns off otherwise.
// A light which is off turns on if exactly 3 neighbors are on, and stays off otherwise.
// All of the lights update simultaneously; they all consider the same current state before moving to the next.
// 
// Here's a few steps from an example configuration of another 6x6 grid:
// 
// Initial state:
// .#.#.#
// ...##.
// #....#
// ..#...
// #.#..#
// ####..
// 
// After 1 step:
// ..##..
// ..##.#
// ...##.
// ......
// #.....
// #.##..
// 
// After 2 steps:
// ..###.
// ......
// ..###.
// ......
// .#....
// .#....
// 
// After 3 steps:
// ...#..
// ......
// ...#..
// ..##..
// ......
// ......
// 
// After 4 steps:
// ......
// ......
// ..##..
// ..##..
// ......
// ......
// After 4 steps, this example has four lights on.
// 
// In your grid of 100x100 lights, given your initial configuration, how many lights are on after 100 steps?
// 821


// --- Part Two ---
// You flip the instructions over; Santa goes on to point out that this is all just an implementation of Conway's Game of Life. 
// At least, it was, until you notice that something's wrong with the grid of lights you bought: four lights, one in each corner, 
// are stuck on and can't be turned off. The example above will actually run like this:
// 
// Initial state:
// ##.#.#
// ...##.
// #....#
// ..#...
// #.#..#
// ####.#
// 
// After 1 step:
// #.##.#
// ####.#
// ...##.
// ......
// #...#.
// #.####
// 
// After 2 steps:
// #..#.#
// #....#
// .#.##.
// ...##.
// .#..##
// ##.###
// 
// After 3 steps:
// #...##
// ####.#
// ..##.#
// ......
// ##....
// ####.#
// 
// After 4 steps:
// #.####
// #....#
// ...#..
// .##...
// #.....
// #.#..#
// 
// After 5 steps:
// ##.###
// .##..#
// .##...
// .##...
// #.#...
// ##...#
// After 5 steps, this example now has 17 lights on.
// 
// In your grid of 100x100 lights, given your initial configuration, but with the four corners always in the on state, 
// how many lights are on after 100 steps?
// 886

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Day18 {
	private static final String INPUT_NAME = "aoc2015_day18.txt";
	private static final int GRID_SIZE = 100;

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		List<List<Integer>> grid = new ArrayList<>(GRID_SIZE);
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				List<Integer> row = new ArrayList<>(GRID_SIZE);
				for (int i = 0; i < GRID_SIZE; i++) {
					if (line.charAt(i) == '#')
						row.add(1);
					else if (line.charAt(i) == '.')
						row.add(0);
				}
				grid.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int offsetY[] = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int offsetX[] = { -1, 0, 1, -1, 1, -1, 0, 1 };

//		grid.get(GRID_SIZE-1).set(0, 1); uncomment for part 2
		
		List<Integer> temp1 = new ArrayList<>(GRID_SIZE);
		List<Integer> temp2 = new ArrayList<>(GRID_SIZE);
		for (int steps = 0; steps < 100; steps++) {
			for (int y = 0; y < GRID_SIZE; y++) {
				List<Integer> currentRow = grid.get(y);
				temp2 = new ArrayList<>(temp1);
				temp1 = new ArrayList<>(currentRow);
				for (int x = 0; x < GRID_SIZE; x++) {
					int count = 0;
					for (int i = 0; i < 8; i++) {
						int nx = x + offsetX[i];
						int ny = y + offsetY[i];
						if (nx < 0 || nx > GRID_SIZE - 1 || ny < 0 || ny > GRID_SIZE - 1) {
							continue;
						}
						count += grid.get(ny).get(nx);
					}
					
					if (currentRow.get(x) == 1) {
						if (count != 2 && count !=3)
							temp1.set(x, 0);
					} else if (currentRow.get(x) == 0) {
						if (count == 3)
							temp1.set(x, 1);
					}
					
//					if ((x == 0 && y == 0) 
//							|| (x == 0 && y == GRID_SIZE - 1)
//							|| (x == GRID_SIZE - 1 && y == 0)
//							|| (x == GRID_SIZE - 1&& y == GRID_SIZE - 1))
//						temp1.set(x, 1);
					
				}
				
				if (y > 0) {
					grid.set(y-1, temp2);
				}
			}
			grid.set(GRID_SIZE-1, temp1);
		}
		
		int result = 0;
		for (int y = 0; y < GRID_SIZE; y++) {
			for (int x = 0; x < GRID_SIZE; x++) {
				result += grid.get(y).get(x);
			}
		}
		
		System.out.println(result);
	}
}
