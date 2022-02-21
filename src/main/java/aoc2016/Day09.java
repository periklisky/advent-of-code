// --- Day 9: Explosives in Cyberspace ---
// Wandering around a secure area, you come across a datalink port to a new part of the network. After briefly scanning it for interesting files,
// you find one file in particular that catches your attention. It's compressed with an experimental format, but fortunately, the documentation 
// for the format is nearby.
// 
// The format compresses a sequence of characters. Whitespace is ignored. To indicate that some sequence should be repeated, a marker is added to 
// the file, like (10x2). To decompress this marker, take the subsequent 10 characters and repeat them 2 times. Then, continue reading the file 
// after the repeated data. The marker itself is not included in the decompressed output.
// 
// If parentheses or other characters appear within the data referenced by a marker, that's okay - treat it like normal data, not a marker, 
// and then resume looking for markers after the decompressed section.
// 
// For example:
// 
// ADVENT contains no markers and decompresses to itself with no changes, resulting in a decompressed length of 6.
// A(1x5)BC repeats only the B a total of 5 times, becoming ABBBBBC for a decompressed length of 7.
// (3x3)XYZ becomes XYZXYZXYZ for a decompressed length of 9.
// A(2x2)BCD(2x2)EFG doubles the BC and EF, becoming ABCBCDEFEFG for a decompressed length of 11.
// (6x1)(1x3)A simply becomes (1x3)A - the (1x3) looks like a marker, but because it's within a data section of another marker, it is not treated 
// any differently from the A that comes after it. It has a decompressed length of 6.
// X(8x2)(3x3)ABCY becomes X(3x3)ABC(3x3)ABCY (for a decompressed length of 18), because the decompressed data from the (8x2) marker (the (3x3)ABC) 
// is skipped and not processed further.
// What is the decompressed length of the file (your puzzle input)? Don't count whitespace.
// 99145

// --- Part Two ---
// Apparently, the file actually uses version two of the format.
// 
// In version two, the only difference is that markers within decompressed data are decompressed. This, the documentation explains, provides much 
// more substantial compression capabilities, allowing many-gigabyte files to be stored in only a few kilobytes.
// 
// For example:
// 
// (3x3)XYZ still becomes XYZXYZXYZ, as the decompressed section contains no markers.
// X(8x2)(3x3)ABCY becomes XABCABCABCABCABCABCY, because the decompressed data from the (8x2) marker is then further decompressed, thus triggering 
// the (3x3) marker twice for a total of six ABC sequences.
// (27x12)(20x12)(13x14)(7x10)(1x12)A decompresses into a string of A repeated 241920 times.
// (25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN becomes 445 characters long.
// Unfortunately, the computer you brought probably doesn't have enough memory to actually decompress the file; you'll have to come up with another way 
// to get its decompressed length.
// 
// What is the decompressed length of the file using this improved format?

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Utils;

public class Day09 {
	private static final String INPUT_NAME = "aoc2016_day09.txt";
	private static BigInteger decompressedLength = BigInteger.valueOf(0);
	private static StringBuilder builder = new StringBuilder();

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		String input = "";
		try {
			input = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		input.replaceAll(" ", "").length();

		StringBuilder output = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(') {
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(input.substring(i));
				matcher.find();
				int a = Integer.valueOf(matcher.group());
				matcher.find();
				int b = Integer.valueOf(matcher.group());

				StringBuilder markerSb = new StringBuilder();
				markerSb.append('(');
				markerSb.append(a);
				markerSb.append("x");
				markerSb.append(b);
				markerSb.append(')');
				String marker = markerSb.toString();

				if (marker.equals(input.substring(i, i + marker.length()))) {
					String sub = input.substring(i + marker.length(), i + marker.length() + a);
					for (int k = 0; k < b; k++) {
						output.append(sub);
					}
					i += marker.length() + a - 1;
				}
			} else {
				output.append(input.charAt(i));
			}
		}
		System.out.println(output.toString().length());

		countDecompressedLength(input);
		System.out.println(decompressedLength);
	}

	private static BigInteger countDecompressedLength(String sequence) {
//		if (!sequence.contains("(")) {
//			BigInteger bigValue = BigInteger.valueOf(sequence.length());
//			decompressedLength = decompressedLength.add(bigValue);
//			return bigValue;
//		}

		BigInteger subLength = BigInteger.valueOf(0);
		for (int i = 0; i < sequence.length(); i++) {
			if (sequence.charAt(i) == '(') {
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(sequence.substring(i));
				matcher.find();
				String a = matcher.group();
				matcher.find();
				String b = matcher.group();
				int markerLength = 1 + a.length() + 1 + b.length() + 1;

				subLength = countDecompressedLength(sequence.substring(i + markerLength, i + markerLength + Integer.valueOf(a)));
				decompressedLength = decompressedLength.add(subLength.multiply(BigInteger.valueOf(Integer.valueOf(b))));
				i += markerLength + Integer.valueOf(a) ;
				System.out.println(sequence.substring(i + markerLength, i + markerLength + Integer.valueOf(a)));
			} else {
				decompressedLength = decompressedLength.add(BigInteger.valueOf(1));
				builder.append(sequence.charAt(i));
			}
		}
		return subLength;
	}
}
