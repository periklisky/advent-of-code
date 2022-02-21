// --- Day 14: Reindeer Olympics ---
// This year is the Reindeer Olympics! Reindeer can fly at high speeds, but must rest occasionally to recover their energy. 
// Santa would like to know which of his reindeer is fastest, and so he has them race.
// 
// Reindeer can only either be flying (always at their top speed) or resting (not moving at all), and always spend whole seconds in either state.
// 
// For example, suppose you have the following Reindeer:
// 
// Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
// Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
// After one second, Comet has gone 14 km, while Dancer has gone 16 km. After ten seconds, Comet has gone 140 km, while Dancer has gone 160 km. 
// On the eleventh second, Comet begins resting (staying at 140 km), and Dancer continues on for a total distance of 176 km. 
// On the 12th second, both reindeer are resting. They continue to rest until the 138th second, when Comet flies for another ten seconds. 
// On the 174th second, Dancer flies for another 11 seconds.
// 
// In this example, after the 1000th second, both reindeer are resting, and Comet is in the lead at 1120 km 
// (poor Dancer has only gotten 1056 km by that point). So, in this situation, Comet would win (if the race ended at 1000 seconds).
// 
// Given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, what distance has the winning reindeer traveled?
// 2696.

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Day14 {
	private static final String INPUT_NAME = "input_day14.txt";
	private static final int TIME = 2503;
	
	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);
		Map<String, List<Integer>> reindeers = loadDataToMap(reader);
		
		int maxDistance = 0;
		for (Entry<String, List<Integer>> entry : reindeers.entrySet()) {
			int speed = entry.getValue().get(0);			
			int activeTime = entry.getValue().get(1);
			int pauseTime = entry.getValue().get(2);

			int time = TIME / (activeTime + pauseTime);			
			int remainder = TIME % (activeTime + pauseTime);

			int distance = time * (speed * activeTime);

			if (remainder >= activeTime) 
				distance += activeTime*speed;
			else
				distance += remainder*speed;
						
			maxDistance = (distance > maxDistance) ? distance : maxDistance;
		}
		System.out.println(maxDistance);
	}

	public static Map<String, List<Integer>> loadDataToMap(BufferedReader reader) {
		Map<String, List<Integer>> reindeers = new HashMap<>();
		
		String line;
		try {
			while((line = reader.readLine()) != null) {
				String tokens[] = line.split(" ");
				String name = tokens[0];
				
				String temp = line.replaceAll("[^0-9]+", ":");
				String numbers[] = temp.split(":");
				
				List<Integer> nums = new ArrayList<>();
				nums.add(Integer.valueOf(numbers[1]));
				nums.add(Integer.valueOf(numbers[2]));
				nums.add(Integer.valueOf(numbers[3]));

				reindeers.put(name, nums);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return reindeers;
	}
}
