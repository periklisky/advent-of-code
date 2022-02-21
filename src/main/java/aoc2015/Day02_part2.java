// --- Part Two ---
// The elves are also running low on ribbon. Ribbon is all the same width, so they only have to worry about the length they need to order,
// which they would again like to be exact.
// 
// The ribbon required to wrap a present is the shortest distance around its sides, or the smallest perimeter of any one face. 
// Each present also requires a bow made out of ribbon as well; the feet of ribbon required for the perfect bow is equal 
// to the cubic feet of volume of the present. Don't ask how they tie the bow, though; they'll never tell.
// 
// For example:
// 
// A present with dimensions 2x3x4 requires 2+2+3+3 = 10 feet of ribbon to wrap the present plus 2*3*4 = 24 feet of ribbon for the bow, 
// for a total of 34 feet.
// A present with dimensions 1x1x10 requires 1+1+1+1 = 4 feet of ribbon to wrap the present plus 1*1*10 = 10 feet of ribbon for the bow, 
// for a total of 14 feet.
// 
// How many total feet of ribbon should they order?
// 3842356

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import utils.Utils;


public class Day02_part2 {
	private static final String INPUT_NAME = "aoc2015_day02.txt";
	
	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		int ribbon = 0;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String dimensions[] = line.split("x");
				int l = Integer.valueOf(dimensions[0]);
				int w = Integer.valueOf(dimensions[1]);
				int h = Integer.valueOf(dimensions[2]);
				
				ribbon += boxSurface(l, w, h) + bow(l, w, h);
			}
			System.out.println(ribbon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int bow(int l, int w, int h) {
		int array[] = {l, w, h};
		Arrays.sort(array);
		return (2*array[0] + 2*array[1]);
	}

	private static int boxSurface(int l, int w, int h) {
		return l*w*h;
	}

}
