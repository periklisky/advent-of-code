// --- Part Two ---
// Seeing how reindeer move in bursts, Santa decides he's not pleased with the old scoring system.
// 
// Instead, at the end of each second, he awards one point to the reindeer currently in the lead. (If there are multiple reindeer tied for 
// the lead, they each get one point.) He keeps the traditional 2503 second time limit, of course, as doing otherwise would be entirely ridiculous.
// 
// Given the example reindeer from above, after the first second, Dancer is in the lead and gets one point. He stays in the lead 
// until several seconds into Comet's second burst: after the 140th second, Comet pulls into the lead and gets his first point. 
// Of course, since Dancer had been in the lead for the 139 seconds before that, he has accumulated 139 points by the 140th second.
// 
// After the 1000th second, Dancer has accumulated 689 points, while poor Comet, our old champion, only has 312. 
// So, with the new scoring system, Dancer would win (if the race ended at 1000 seconds).
// 
// Again given the descriptions of each reindeer (in your puzzle input), after exactly 2503 seconds, 
// how many points does the winning reindeer have?
// 1084

package aoc2015;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Day14_part2 {
	private static final String INPUT_NAME = "input_day14.txt";
	private static final int TIME = 2503;
	
	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);
		
		Map<String, List<Integer>> reindeers = Day14.loadDataToMap(reader);
		
		List<Integer> distances = new ArrayList<>(reindeers.size());
		for (int i=0; i<reindeers.size(); i++) 
			distances.add(0);
		List<Integer> scores = new ArrayList<>(distances);
		
		for (int currentTime = 1; currentTime <= TIME; currentTime++) {
			int i = 0;
			for (Entry<String, List<Integer>> reindeer : reindeers.entrySet()) {
				List<Integer> stats = reindeer.getValue();
				int speed = stats.get(0);
				int activeTime = stats.get(1);
				int pauseTime = stats.get(2);
				int distance = distances.get(i);
				
				int condition = currentTime % (activeTime + pauseTime);
				if (condition <= activeTime && condition > 0) {
					distances.set(i, distance + speed);
				}
				i++;
			}

			int max = distances.get(0);
			for (int k = 1; k < distances.size(); k++) {
				if (distances.get(k) > max) {
					max = distances.get(k);
				}
			}
			
			for (int k = 0; k < distances.size(); k++)
				if (distances.get(k) == max) {
					scores.set(k, scores.get(k) + 1);
				}
		}
		Collections.sort(scores);
		System.out.println(scores.get(scores.size()-1));
	}
}
