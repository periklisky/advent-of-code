// --- Day 17: No Such Thing as Too Much ---
// The elves bought too much eggnog again - 150 liters this time. To fit it all into your refrigerator, you'll need to move it into smaller containers. You take an inventory
// of the capacities of the available containers.
// 
// For example, suppose you have containers of size 20, 15, 10, 5, and 5 liters. If you need to store 25 liters, there are four ways to do it:
// 
// 15 and 10
// 20 and 5 (the first 5)
// 20 and 5 (the second 5)
// 15, 5, and 5
// Filling all containers entirely, how many different combinations of containers can exactly fit all 150 liters of eggnog?
// 

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import utils.Utils;

public class Day17 {
	private static final String INPUT_NAME = "aoc2015_day17.txt";
	private static final int SUM = 25;

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		String line;
		List<Integer> containers = new ArrayList<>();
		try {
			while ((line = reader.readLine()) != null) {
				containers.add(Integer.valueOf(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int start = 0;
		int solutions = 0;
		LinkedList<Integer> combinations = new LinkedList<>();
		LinkedList<Integer> indeces = new LinkedList<>();		

		for (int i = start; i < containers.size(); i++) {
			int next = containers.get(i);

			if (getListSum(combinations) + next < SUM) {
				combinations.addLast(next);
				indeces.add(i);
			} else if (getListSum(combinations) + next == SUM) {
				solutions++;
			}

			if (i == containers.size() - 1) {
				if (combinations.size() > 1) {
					if (indeces.getLast() == containers.size() - 1) {
						i = start++;
						for (int index = 0; index < indeces.size(); index++) {
							combinations.removeLast();
							indeces.removeLast();
						}
					} else {
						i = indeces.getLast();
						indeces.removeLast();
					}
				} else {
					combinations.remove();
					indeces.remove();
					i = start++;
				}
			}
		}

		System.out.println(solutions);
	}

	private static int getListSum(List<Integer> list) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i);
		}
		return sum;
	}
}
