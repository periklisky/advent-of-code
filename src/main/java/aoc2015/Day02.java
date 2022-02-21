// --- Day 2: I Was Told There Would Be No Math ---
// The elves are running low on wrapping paper, and so they need to submit an order for more. 
// They have a list of the dimensions (length l, width w, and height h) of each present, and 
// only want to order exactly as much as they need.
// 
// Fortunately, every present is a box (a perfect right rectangular prism), which makes calculating the required wrapping paper 
// for each gift a little easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l. 
// The elves also need a little extra paper for each present: the area of the smallest side.
// 
// For example:
// 
// A present with dimensions 2x3x4 requires 2*6 + 2*12 + 2*8 = 52 square feet of wrapping paper plus 6 square feet of slack, 
// for a total of 58 square feet.
// A present with dimensions 1x1x10 requires 2*1 + 2*10 + 2*10 = 42 square feet of wrapping paper plus 1 square foot of slack, 
// for a total of 43 square feet.
//
// All numbers in the elves' list are in feet. How many total square feet of wrapping paper should they order?
// 1606483

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import utils.Utils;

public class Day02 {
	private static String INPUT_NAME = "aoc2015_day02.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		int totalArea = 0;
		int boxSurface;
		int ribbon = 0;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				boxSurface = 0;
				String dimensions[] = line.split("x");
				int l = Integer.valueOf(dimensions[0]);
				int w = Integer.valueOf(dimensions[1]);
				int h = Integer.valueOf(dimensions[2]);
				boxSurface = (2 * l * w) + (2 * w * h) + (2 * h * l) + calculateMinArea(l, w, h);
				totalArea += boxSurface;
				ribbon += boxSurface(l, w, h) + bow(l, w, h);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(totalArea);
		System.out.println(ribbon);
	}

	private static int calculateMinArea(int l, int w, int h) {
		int arr[] = { l, w, h };
		Arrays.sort(arr);
		return arr[0] * arr[1];
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
