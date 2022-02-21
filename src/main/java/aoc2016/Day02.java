//--- Day 2: Bathroom Security ---
// You arrive at Easter Bunny Headquarters under cover of darkness. However, you left in such a rush that you forgot to use the bathroom! 
// Fancy office buildings like this one usually have keypad locks on their bathrooms, so you search the front desk for the code.
// 
// "In order to improve security," the document you find says, "bathroom codes will no longer be written down. Instead, please memorize and 
// follow the procedure below to access the bathrooms."
// 
// The document goes on to explain that each button to be pressed can be found by starting on the previous button and moving to adjacent 
// buttons on the keypad: U moves up, D moves down, L moves left, and R moves right. Each line of instructions corresponds to one button, 
// starting at the previous button (or, for the first line, the "5" button); press whatever button you're on at the end of each line. 
// If a move doesn't lead to a button, ignore it.
// 
// You can't hold it much longer, so you decide to figure out the code as you walk to the bathroom. You picture a keypad like this:
// 
// 1 2 3
// 4 5 6
// 7 8 9
// Suppose your instructions are:
// 
// ULL
// RRDDD
// LURDL
// UUUUD
// You start at "5" and move up (to "2"), left (to "1"), and left (you can't, and stay on "1"), so the first button is 1.
// Starting from the previous button ("1"), you move right twice (to "3") and then down three times (stopping at "9" after two moves and 
// ignoring the third), ending up with 9.
// Continuing from "9", you move left, up, right, down, and left, ending with 8.
// Finally, you move up four times (stopping at "2"), then down once, ending with 5.
// So, in this example, the bathroom code is 1985.
// 
// Your puzzle input is the instructions from the document you found at the front desk. What is the bathroom code?
// 82958

// --- Part Two ---
// You finally arrive at the bathroom (it's a several minute walk from the lobby so visitors can behold the many fancy conference rooms and
// water coolers on this floor) and go to punch in the code. Much to your bladder's dismay, the keypad is not at all like you imagined it. Instead, 
// you are confronted with the result of hundreds of man-hours of bathroom-keypad-design meetings:
// 
//     1
//   2 3 4
// 5 6 7 8 9
//   A B C
//     D
// You still start at "5" and stop when you're at an edge, but given the same instructions as above, the outcome is very different:
// 
// You start at "5" and don't move at all (up and left are both edges), ending at 5.
// Continuing from "5", you move right twice and down three times (through "6", "7", "B", "D", "D"), ending at D.
// Then, from "D", you move five more times (through "D", "B", "C", "C", "B"), ending at B.
// Finally, after five more moves, you end at 3.
// So, given the actual keypad layout, the code would be 5DB3.
// 
// Using the same instructions in your puzzle input, what is the correct bathroom code?
// B3DB8

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;

import utils.Utils;

public class Day02 {
	private static final String INPUT_NAME = "aoc2016_day02.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		StringBuilder sb = new StringBuilder();
		String button = "5";

		StringBuilder bathroomSb = new StringBuilder();
		String bathroomButton = "5";

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				button = findNextButton(line, button);
				sb.append(button);

				bathroomButton = findNextBathroomButton(line, bathroomButton);
				bathroomSb.append(bathroomButton);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString());
		System.out.println(bathroomSb.toString());
	}

	private static String findNextButton(String line, String startingButton) {
		String keypad[][] = { { "1", "2", "3" }, { "4", "5", "6" }, { "7", "8", "9" } };

		int x = 0;
		int y = 0;
		boolean found = false;

		for (y = 0; y < keypad.length; y++) {
			for (x = 0; x < keypad[y].length; x++)
				if (keypad[y][x].equals(startingButton)) {
					found = true;
					break;
				}

			if (found)
				break;
		}

		for (int i = 0; i < line.length(); i++) {
			switch (line.charAt(i)) {
			case 'R':
				x = (x == 2) ? x : x + 1;
				break;
			case 'L':
				x = (x == 0) ? x : x - 1;
				break;
			case 'U':
				y = (y == 0) ? y : y - 1;
				break;
			case 'D':
				y = (y == 2) ? y : y + 1;
				break;
			default:
				break;
			}
		}

		return keypad[y][x];
	}

	private static String findNextBathroomButton(String line, String startingButton) {
		String keypad[][] = { { "1" }, { "2", "3", "4" }, { "5", "6", "7", "8", "9" }, { "A", "B", "C" }, { "D" } };

		int y = 0;
		int x = 0;
		boolean found = false;

		for (y = 0; y < keypad.length; y++) {
			for (x = 0; x < keypad[y].length; x++)
				if (keypad[y][x].equals(startingButton)) {
					found = true;
					break;
				}

			if (found)
				break;
		}

		for (int i = 0; i < line.length(); i++) {
			switch (line.charAt(i)) {
			case 'R':
				x = (x == keypad[y].length - 1) ? x : x + 1;
				break;
			case 'L':
				x = (x == 0) ? x : x - 1;
				break;
			case 'U':
				if ((y == 1 && x == 1) || (y == 2 && x > 0 && x < 4)) {
					x--;
					y--;
				} else if (y == 3 || y == 4) {
					x++;
					y--;
				}
				break;
			case 'D':
				if (y == 0 || y == 1) {
					x++;
					y++;
				} else if ((y == 2 && x > 0 && x < 4) || (y == 3 && x == 1)) {
					x--;
					y++;
				}
				break;
			default:
				break;
			}
		}
		System.out.println(y + ", " + x);

		return keypad[y][x];
	}

}
