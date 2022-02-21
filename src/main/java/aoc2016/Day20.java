// --- Day 20: Firewall Rules ---
// You'd like to set up a small hidden computer here so you can use it to get back into the network later. However, the corporate firewall only 
// allows communication with certain external IP addresses.
// 
// You've retrieved the list of blocked IPs from the firewall, but the list seems to be messy and poorly maintained, and it's not clear 
// which IPs are allowed. Also, rather than being written in dot-decimal notation, they are written as plain 32-bit integers, which 
// can have any value from 0 through 4294967295, inclusive.
// 
// For example, suppose only the values 0 through 9 were valid, and that you retrieved the following blacklist:
// 
// 5-8
// 0-2
// 4-7
// The blacklist specifies ranges of IPs (inclusive of both the start and end value) that are not allowed. Then, the only IPs that 
// this firewall allows are 3 and 9, since those are the only numbers not in any range.
// 
// Given the list of blocked IPs you retrieved from the firewall (your puzzle input), what is the lowest-valued IP that is not blocked?
// 19449262

// --- Part Two ---
// How many IPs are allowed by the blacklist?
//

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import utils.Utils;

public class Day20 {
	private static final String INPUT = "aoc2016_day20.txt";
	private static final long MIN_IP = 0L;
	private static final long MAX_IP = 37L;

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT);

		List<Range> blacklist = new ArrayList<>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split("-");
				blacklist.add(new Range(Long.valueOf(tokens[0]), Long.valueOf(tokens[1])));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		long lowestIP = MIN_IP;
//		lowestIP = findNextLowestIPallowed(lowestIP, blacklist);
//		System.out.println(lowestIP);

		long count = 0;
		long previousLowestIP = MIN_IP;
		lowestIP = MIN_IP;
		while (Long.compare((lowestIP = findNextLowestIPallowed(previousLowestIP, blacklist)), previousLowestIP) > 0) {
//			System.out.println(lowestIP);
			ListIterator<Range> it = blacklist.listIterator(findMinRangeIndex(blacklist, lowestIP));
			Range range = it.next();
			count += range.getMin() - previousLowestIP;
			previousLowestIP = (range.getMax() < MAX_IP) ? range.getMax() + 1 : MAX_IP;
		}
		count += MAX_IP - previousLowestIP;
		System.out.println(count);
	}

	private static long findNextLowestIPallowed(long lowestIP, List<Range> blacklist) {
		while (!isAllowed(lowestIP, blacklist)) {
			ListIterator<Range> it = blacklist.listIterator(findMinRangeIndex(blacklist, lowestIP));
			Range range = it.next();
			lowestIP = range.getMax() + 1;
			it.remove();
		}

		return lowestIP;
	}

	private static boolean isAllowed(long ip, List<Range> blacklist) {
		ListIterator<Range> it = blacklist.listIterator();
		while (it.hasNext())
			if (it.next().isInRange(ip))
				return false;

		return true;
	}

	private static int findMinRangeIndex(List<Range> blacklist, long lowestIP) {
		long min = Long.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < blacklist.size(); i++) {
			Range range = blacklist.get(i);
			long rangeMin = range.getMin();
			if (Long.compare(min, rangeMin) >= 0 && Long.compare(rangeMin, lowestIP) >= 0) {
				min = rangeMin;
				index = i;
			}
		}
		return index;
	}

	public static class Range {
		long min;
		long max;

		public Range(long min, long max) {
			this.min = min;
			this.max = max;
		}

		public void print() {
			System.out.println("[" + min + ", " + max + "]");
		}

		public long getMin() {
			return min;
		}

		public long getMax() {
			return max;
		}

		public boolean isInRange(long x) {
			return Long.compare(x, min) >= 0 && Long.compare(x, max) <= 0;
		}
	}
}
