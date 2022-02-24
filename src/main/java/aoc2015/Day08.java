// --- Day 8: Matchsticks ---
// Space on the sleigh is limited this year, and so Santa will be bringing his list as a digital copy. 
// He needs to know how much space it will take up when stored.
// 
// It is common in many programming languages to provide a way to escape special characters in strings. 
// For example, C, JavaScript, Perl, Python, and even PHP handle special characters in very similar ways.
// 
// However, it is important to realize the difference between the number of characters in the code representation of the string literal 
// and the number of characters in the in-memory string itself.
// 
// For example:
// "" is 2 characters of code (the two double quotes), but the string contains zero characters.
// "abc" is 5 characters of code, but 3 characters in the string data.
// "aaa\"aaa" is 10 characters of code, but the string itself contains six "a" characters and a single, escaped quote character, 
//  for a total of 7 characters in the string data.
// "\x27" is 6 characters of code, but the string itself contains just one - an apostrophe ('), escaped using hexadecimal notation.
// Santa's list is a file that contains many double-quoted string literals, one on each line. 
//  The only escape sequences used are \\ (which represents a single backslash), \" (which represents a lone double-quote character), and \x plus two hexadecimal characters (which represents a single character with that ASCII code).
// 
// Disregarding the whitespace in the file, what is the number of characters of code for string literals minus the number 
// of characters in memory for the values of the strings in total for the entire file?
// 
// For example, given the four strings above, the total number of characters of string code (2 + 5 + 10 + 6 = 23) minus the total number 
// of characters in memory for string values (0 + 3 + 7 + 1 = 11) is 23 - 11 = 12.
// 
// 1350

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;

import utils.Utils;

public class Day08 {
	private static final String INPUT_NAME = "aoc2015_day08.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		int charactersOfCode = 0;
		int charactersOfCode2 = 0;
		int charactersInMemory = 0;
		int charactersInMemory2 = 0;

		String literal = "";
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

				charactersOfCode += literal.length();
				charactersOfCode2 += newLiteral.length();

				charactersInMemory = countCharactersInMemory(literal, charactersInMemory) - 2;
				charactersInMemory2 = countCharactersInMemory(newLiteral.toString(), charactersInMemory2) - 2;
			}
			System.out.println(charactersOfCode - charactersInMemory);
			System.out.println(charactersOfCode2 - charactersInMemory2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int countCharactersInMemory(String input, int chars) {
		for (int i = 0; i < input.length(); i++) {
			chars++;
			if (input.charAt(i) != '\\')
				continue;
			else if (input.charAt(i + 1) == '\"' || input.charAt(i + 1) == '\\')
				i += 1;
			else if (input.charAt(i + 1) == 'x')
				i += 3;
		}
		return chars;
	}
}