// --- Day 3: Squares With Three Sides ---
// Now that you can think clearly, you move deeper into the labyrinth of hallways and office furniture that makes up this part of Easter Bunny HQ. 
// This must be a graphic design department; the walls are covered in specifications for triangles.
// 
// Or are they?
// 
// The design document gives the side lengths of each triangle it describes, but... 5 10 25? Some of these aren't triangles. 
// You can't help but mark the impossible ones.
// 
// In a valid triangle, the sum of any two sides must be larger than the remaining side. For example, the "triangle" given above is impossible, 
// because 5 + 10 is not larger than 25.
// 
// In your puzzle input, how many of the listed triangles are possible?
// 983

// --- Part Two ---
// Now that you've helpfully marked up their design documents, it occurs to you that triangles are specified in groups of three vertically. 
// Each set of three numbers in a column specifies a triangle. Rows are unrelated.
// 
// For example, given the following specification, numbers with the same hundreds digit would be part of the same triangle:
// 
// 101 301 501
// 102 302 502
// 103 303 503
// 201 401 601
// 202 402 602
// 203 403 603
// In your puzzle input, and instead reading by columns, how many of the listed triangles are possible?
// 1836

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Day03 {
	private static final String INPUT_NAME = "aoc2016_day03.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		String line;
		List<List<Integer>> triangles = new ArrayList<>();
		try {
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.trim().replaceAll(" +", " ").split(" ");
				List<Integer> triangle = new ArrayList<>();
				for (int i = 0; i < 3; i++)
					triangle.add(Integer.valueOf(tokens[i]));
				triangles.add(triangle);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int count = 0;
		for (int i = 0; i < triangles.size(); i++) {
			count = (isTriangleValid(triangles.get(i))) ? count + 1 : count;
		}

		System.out.println(count);

		count = 0;
		for (int i = 0; i < triangles.size(); i = i + 3) {
			for (int x = 0; x < 3; x++) {
				List<Integer> triangle = new ArrayList<>();
				for (int y = i; y < i + 3; y++) {
					triangle.add(triangles.get(y).get(x));
				}
				count = (isTriangleValid(triangle)) ? count + 1 : count;
			}
		}

		System.out.println(count);

	}

	private static boolean isTriangleValid(List<Integer> list) {
		int a = list.get(0);
		int b = list.get(1);
		int c = list.get(2);
		return (a + b > c) && (a + c > b) && (b + c > a);
	}
}
