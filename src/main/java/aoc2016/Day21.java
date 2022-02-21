// --- Day 21: Scrambled Letters and Hash ---
// The computer system you're breaking into uses a weird scrambling function to store its passwords. It shouldn't be much trouble to create 
// your own scrambled password so you can add it to the system; you just have to implement the scrambler.
// 
// The scrambling function is a series of operations (the exact list is provided in your puzzle input). Starting with the password to be scrambled, 
// apply each operation in succession to the string. The individual operations behave as follows:
// 
// swap position X with position Y means that the letters at indexes X and Y (counting from 0) should be swapped.
// swap letter X with letter Y means that the letters X and Y should be swapped (regardless of where they appear in the string).
// rotate left/right X steps means that the whole string should be rotated; for example, one right rotation would turn abcd into dabc.
// rotate based on position of letter X means that the whole string should be rotated to the right based on the index of letter X 
// (counting from 0) as determined before this instruction does any rotations. Once the index is determined, rotate the string to the right 
// one time, plus a number of times equal to that index, plus one additional time if the index was at least 4.
// reverse positions X through Y means that the span of letters at indexes X through Y (including the letters at X and Y) should be reversed 
// in order.
// move position X to position Y means that the letter which is at index X should be removed from the string, then inserted such that it ends up at index Y.
//
// For example, suppose you start with abcde and perform the following operations:
// 
// swap position 4 with position 0 swaps the first and last letters, producing the input for the next step, ebcda.
// swap letter d with letter b swaps the positions of d and b: edcba.
// reverse positions 0 through 4 causes the entire string to be reversed, producing abcde.
// rotate left 1 step shifts all letters left one position, causing the first letter to wrap to the end of the string: bcdea.
// move position 1 to position 4 removes the letter at position 1 (c), then inserts it at position 4 (the end of the string): bdeac.
// move position 3 to position 0 removes the letter at position 3 (a), then inserts it at position 0 (the front of the string): abdec.
// rotate based on position of letter b finds the index of letter b (1), then rotates the string right once plus a number of times equal to 
// that index (2): ecabd.
// rotate based on position of letter d finds the index of letter d (4), then rotates the string right once, plus a number of times equal to 
// that index, plus an additional time because the index was at least 4, for a total of 6 right rotations: decab.
// After these steps, the resulting scrambled password is decab.
// 
// Now, you just need to generate a new scrambled password and you can access the system. Given the list of scrambling operations in your puzzle input, what is the result of scrambling abcdefgh?
// bfheacgd


// --- Part Two ---
// You scrambled the password correctly, but you discover that you can't actually modify the password file on the system. You'll need to 
// un-scramble one of the existing passwords by reversing the scrambling process.
// 
// What is the un-scrambled version of the scrambled password fbgdceah?
//
package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import utils.Utils;

public class Day21 {
	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader("aoc2016_day21.txt");


		List<String> operations = new ArrayList<>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				operations.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(scramble("abcdefgh", operations));
//		Collections.reverse(operations);
		System.out.println(unscramble("fbgdceah", operations));
	}
	
	private static String unscramble(String password, List<String> operations) {
		ListIterator<String> it = operations.listIterator();
		while (it.hasNext()) {
			String operation = it.next();
			String tokens[] = operation.split(" ");
			String op = tokens[0];
			if (op.equals("move")) {
				int x = Integer.valueOf(tokens[2]);
				int y = Integer.valueOf(tokens[5]);
				password = move(password, x, y);
			} else if (op.equals("reverse")) {
				int x = Integer.valueOf(tokens[2]);
				int y = Integer.valueOf(tokens[4]);
				password = reverse(password, x, y);
			} else if (operation.contains("swap position")) {
				int x = Integer.valueOf(tokens[2]);
				int y = Integer.valueOf(tokens[5]);
				password = swapPosition(password, x, y);
			} else if (operation.contains("swap letter")) {
				char x = tokens[2].charAt(0);
				char y = tokens[5].charAt(0);
				password = swapLetter(password, x, y);
			} else if (tokens[1].equals("based")) {
				char x = tokens[6].charAt(0);
				password = rotateLeftBasedOnPositionOfLetter(password, x);
			} else if (op.equals("rotate")) {
				int x = Integer.valueOf(tokens[2]);
				if (tokens[1].equals("left"))
					password = rotateRight(password, x);
				else
					password = rotateLeft(password, x);
			}
		}
		return password;
	}

	private static String rotateLeftBasedOnPositionOfLetter(String input, char x) {
		input = rotateLeft(input, 1);
		int index = input.indexOf(x);
		if (index > 3)
			index -= 1;
			input = rotateLeft(input, index);
		return input;
	}
	
	private static String scramble(String password, List<String> operations) {
		ListIterator<String> it = operations.listIterator();
		while (it.hasNext()) {
			String operation = it.next();
			String tokens[] = operation.split(" ");
			String op = tokens[0];
			if (op.equals("move")) {
				int x = Integer.valueOf(tokens[2]);
				int y = Integer.valueOf(tokens[5]);
				password = move(password, x, y);
			} else if (op.equals("reverse")) {
				int x = Integer.valueOf(tokens[2]);
				int y = Integer.valueOf(tokens[4]);
				password = reverse(password, x, y);
			} else if (operation.contains("swap position")) {
				int x = Integer.valueOf(tokens[2]);
				int y = Integer.valueOf(tokens[5]);
				password = swapPosition(password, x, y);
			} else if (operation.contains("swap letter")) {
				char x = tokens[2].charAt(0);
				char y = tokens[5].charAt(0);
				password = swapLetter(password, x, y);
			} else if (tokens[1].equals("based")) {
				char x = tokens[6].charAt(0);
				password = rotateBasedOnPositionOfLetter(password, x);
			} else if (op.equals("rotate")) {
				int x = Integer.valueOf(tokens[2]);
				if (tokens[1].equals("left"))
					password = rotateLeft(password, x);
				else
					password = rotateRight(password, x);
			}
		}
		return password;
	}

	private static String swapPosition(String input, int x, int y) {
		int min = (x < y) ? x : y;
		int max = (x > y) ? x : y;
		
		String v = input.substring(0, min) + input.charAt(max) + input.substring(min + 1, max) + input.charAt(min)
		+ input.substring(max + 1, input.length());
		return v;
	}

	private static String swapLetter(String input, char x, char y) {
		int index_x = input.indexOf(x);
		int index_y = input.indexOf(y);

		return swapPosition(input, index_x, index_y);
	}

	private static String rotateLeft(String input, int x) {
		x = x % input.length();
		return input.substring(x, input.length()) + input.substring(0, x);
	}

	private static String rotateRight(String input, int x) {
		x = x % input.length();
		return input.substring(input.length() - x, input.length()) + input.substring(0, input.length() - x);
	}

	private static String rotateBasedOnPositionOfLetter(String input, char x) {
		int index = input.indexOf(x);
		index = (index > 3) ? index + 2 : index + 1;
		input = rotateRight(input, index);
		return input;
	}

	private static String reverse(String input, int x, int y) {
		StringBuilder sb = new StringBuilder();
		StringBuilder reversed = new StringBuilder(input.substring(x, y + 1));
		reversed.reverse();

		sb.append(input.substring(0, x));
		sb.append(reversed.toString());
		sb.append(input.substring(y + 1, input.length()));

		return sb.toString();
	}

	private static String move(String input, int x, int y) {
		char[] chars = input.toCharArray();
		char temp = chars[x];

		if (x > y) {
			for (int i = x; i > y; i--) {
				chars[i] = chars[i - 1];
			}
		} else {
			for (int i = x; i < y; i++) {
				chars[i] = chars[i + 1];
			}
		}

		chars[y] = temp;

		return String.valueOf(chars);
	}
}
