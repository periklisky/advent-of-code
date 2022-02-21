// --- Day 6: Signals and Noise ---
// Something is jamming your communications with Santa. Fortunately, your signal is only partially jammed, and protocol in situations like this 
// is to switch to a simple repetition code to get the message through.
// 
// In this model, the same message is sent repeatedly. You've recorded the repeating message signal (your puzzle input), but the data seems 
// quite corrupted - almost too badly to recover. Almost.
// 
// All you need to do is figure out which character is most frequent for each position. For example, suppose you had recorded 
// the following messages:
// 
// eedadn
// drvtee
// eandsr
// raavrd
// atevrs
// tsrnev
// sdttsa
// rasrtv
// nssdts
// ntnada
// svetve
// tesnvt
// vntsnd
// vrdear
// dvrsen
// enarar
// The most common character in the first column is e; in the second, a; in the third, s, and so on. Combining these characters returns 
// the error-corrected message, easter.
// 
// Given the recording in your puzzle inp
// xdkzukcf

// --- Part Two ---
// Of course, that would be the message - if you hadn't agreed to use a modified repetition code instead.
// 
// In this modified code, the sender instead transmits what looks like random data, but for each character, the character they actually want
// to send is slightly less likely than the others. Even after signal-jamming noise, you can look at the letter distributions in each column
// and choose the least common letter to reconstruct the original message.
// 
// In the above example, the least common character in the first column is a; in the second, d, and so on. Repeating this process for the
// remaining characters produces the original message, advent.
// 
// Given the recording in your puzzle input and this new decoding methodology, what is the original message that Santa is trying to send?
// cevsgyvd

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Day06 {
	private static final String INPUT_NAME = "aoc2016_day06.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		List<String> input = new ArrayList<>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				input.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int size = input.get(0).length();
		StringBuilder sbMostCommon = new StringBuilder();
		StringBuilder sbLeastCommon = new StringBuilder();

		initializeFrequencies();

		for (int i = 0; i < size; i++) {
			Map<Character, Integer> frequencies = initializeFrequencies();

			for (int j = 0; j < input.size(); j++) {
				char c = input.get(j).charAt(i);
				frequencies.put(c, frequencies.get(c) + 1);
			}
			sbLeastCommon.append(getLeastCommonCharacter(frequencies));
			sbMostCommon.append(getMostCommonCharacter(frequencies));
		}
		System.out.println(sbMostCommon.toString());
		System.out.println(sbLeastCommon.toString());
	}

	private static char getMostCommonCharacter(Map<Character, Integer> frequencies) {
		char mostCommon = 'a';
		int max = frequencies.get('a');
		for (Entry<Character, Integer> frequence : frequencies.entrySet()) {
			if (frequence.getValue() > max) {
				max = frequence.getValue();
				mostCommon = frequence.getKey();
			}
		}

		return mostCommon;
	}

	private static char getLeastCommonCharacter(Map<Character, Integer> frequencies) {
		char leastCommon = '\0';
		int min = 1000;
		for (Entry<Character, Integer> frequence : frequencies.entrySet()) {
			if (frequence.getValue() > 0 && frequence.getValue() < min) {
				min = frequence.getValue();
				leastCommon = frequence.getKey();
			}
		}

		return leastCommon;
	}

	private static Map<Character, Integer> initializeFrequencies() {
		Map<Character, Integer> frequencies = new HashMap<>();

		for (char c = 'a'; c < 'a' + 26; c++)
			frequencies.put(c, 0);

		return frequencies;
	}
}
