// --- Part Two ---
// Now, let's go the other way. In addition to finding the number of characters of code, you should now encode each code representation 
// as a new string and find the number of characters of the new encoded representation, including the surrounding double quotes.
// 
// For example:
// 
// "" encodes to "\"\"", an increase from 2 characters to 6.
// "abc" encodes to "\"abc\"", an increase from 5 characters to 9.
// "aaa\"aaa" encodes to "\"aaa\\\"aaa\"", an increase from 10 characters to 16.
// "\x27" encodes to "\"\\x27\"", an increase from 6 characters to 11.

// Your task is to find the total number of characters to represent the newly encoded strings minus the number of characters of code 
// in each original string literal. For example, for the strings above, the total encoded length (6 + 9 + 16 + 11 = 42) 
// minus the characters in the original code representation (23, just like in the first part of this puzzle) is 42 - 23 = 19.
// 2085.

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;

import utils.Utils;

public class Day08_part2 {
	private static final String INPUT_NAME = "input_day8.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		int countCharactersOfCode = 0;
		int countCharactersInMemory = 0;

		String literal;
		try {
			while ((literal = reader.readLine()) != null) {
				StringBuilder newLiteral = new StringBuilder("\"");

				for (int i = 0; i < literal.length(); i++) {
					switch (literal.charAt(i)) {
					case '\"':
						newLiteral.append("\\\"");
						break;
					case '\\':
						newLiteral.append("\\\\");
						break;
					default:
						newLiteral.append(literal.charAt(i));
						break;
					}
				}

				newLiteral.append("\"");

				countCharactersOfCode += newLiteral.length();
				for (int i = 0; i < newLiteral.length(); i++) {
					countCharactersInMemory++;
					if (newLiteral.charAt(i) != '\\')
						continue;
					else if (newLiteral.charAt(i + 1) == '\"' || newLiteral.charAt(i + 1) == '\\')
						i += 1;
					else if (newLiteral.charAt(i + 1) == 'x')
						i += 3;
				}
				countCharactersInMemory -= 2;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(countCharactersOfCode - countCharactersInMemory);
	}
}
