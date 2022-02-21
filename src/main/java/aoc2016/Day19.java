// --- Day 19: An Elephant Named Joseph ---
// The Elves contact you over a highly secure emergency channel. Back at the North Pole, the Elves are busy misunderstanding White 
// Elephant parties.
// 
// Each Elf brings a present. They all sit in a circle, numbered starting with position 1. Then, starting with the first Elf, 
// they take turns stealing all the presents from the Elf to their left. An Elf with no presents is removed from the circle and 
// does not take turns.
// 
// For example, with five Elves (numbered 1 to 5):
// 
//   1
// 5   2
//  4 3
// Elf 1 takes Elf 2's present.
// Elf 2 has no presents and is skipped.
// Elf 3 takes Elf 4's present.
// Elf 4 has no presents and is also skipped.
// Elf 5 takes Elf 1's two presents.
// Neither Elf 1 nor Elf 2 have any presents, so both are skipped.
// Elf 3 takes Elf 5's three presents.
// So, with five Elves, the Elf that sits starting in position 3 gets all the presents.
// 
// With the number of Elves given in your puzzle input, which Elf gets all the presents?
// 
// Your puzzle input is 3014387.
// 1834471

// --- Part Two ---
// Realizing the folly of their present-exchange rules, the Elves agree to instead steal presents from the Elf directly across the circle. 
// If two Elves are across the circle, the one on the left (from the perspective of the stealer) is stolen from. The other rules remain 
// unchanged: Elves with no presents are removed from the circle entirely, and the other elves move in slightly to keep the circle 
// evenly spaced.
// 
// For example, with five Elves (again numbered 1 to 5):
// 
// The Elves sit in a circle; Elf 1 goes first:
//   1
// 5   2
//  4 3
// Elves 3 and 4 are across the circle; Elf 3's present is stolen, being the one to the left. Elf 3 leaves the circle, and the rest 
// of the Elves move in:
//   1           1
// 5   2  -->  5   2
//  4 -          4
// Elf 2 steals from the Elf directly across the circle, Elf 5:
//   1         1 
// -   2  -->     2
//   4         4 
// Next is Elf 4 who, choosing between Elves 1 and 2, steals from Elf 1:
//  -          2  
//     2  -->
//  4          4
// Finally, Elf 2 steals from Elf 4:
//  2
//     -->  2  
//  -
// So, with five Elves, the Elf that sits starting in position 2 gets all the presents.
// 
// With the number of Elves given in your puzzle input, which Elf now gets all the presents?
// 1420064 (45 minutes to calculate!)

package aoc2016;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Day19 {
	private static final int INPUT = 3014387;

	public static void main(String[] args) {
		LinkedList<Integer> elves = new LinkedList<>();
		for (int i = 1; i <= INPUT; i++)
			elves.add(i);

		ListIterator<Integer> it = elves.listIterator();
		while (elves.size() > 1) {
			if (it.hasNext()) {
				it.next();
				if (it.hasNext()) {
					it.next();
					it.remove();
				} else if (elves.size() > 1) {
					it = elves.listIterator();
					it.next();
					it.remove();
				}
			} else {
				it = elves.listIterator();
			}
		}
		System.out.println(elves.get(0));

		List<Integer> elves2 = new ArrayList<>();
		for (int i = 1; i <= INPUT; i++)
			elves2.add(i);

		ListIterator<Integer> it2 = elves2.listIterator();
		while (elves2.size() > 1) {
			if (it2.hasNext()) {
				int size = elves2.size();
				int index = it2.nextIndex();
				int oppositeIndex = (index + (size / 2)) % size;
				it2 = elves2.listIterator(oppositeIndex);
				it2.next();
				it2.remove();
				
				if (index < oppositeIndex)
					it2 = (index + 1 < elves2.size()) ? elves2.listIterator(index + 1) : elves2.listIterator();
				else
					it2 = (index < elves2.size()) ? elves2.listIterator(index) : elves2.listIterator();
			} else {
				it2 = elves2.listIterator();
			}
		}
		System.out.println(elves2.get(0));
	}
}
