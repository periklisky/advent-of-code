// --- Day 13: A Maze of Twisty Little Cubicles ---
// You arrive at the first floor of this new building to discover a much less welcoming environment than the shiny atrium of the last one. Instead,
// you are in a maze of twisty little cubicles, all alike.
// 
// Every location in this area is addressed by a pair of non-negative integers (x,y). Each such coordinate is either a wall or an open space. 
// You can't move diagonally. The cube maze starts at 0,0 and seems to extend infinitely toward positive x and y; negative values are invalid, 
// as they represent a location outside the building. You are in a small waiting area at 1,1.
// 
// While it seems chaotic, a nearby morale-boosting poster explains, the layout is actually quite logical. You can determine whether a given 
// x,y coordinate will be a wall or an open space using a simple system:
// 
// Find x*x + 3*x + 2*x*y + y + y*y.
// Add the office designer's favorite number (your puzzle input).
// Find the binary representation of that sum; count the number of bits that are 1.
// If the number of bits that are 1 is even, it's an open space.
// If the number of bits that are 1 is odd, it's a wall.
// For example, if the office designer's favorite number were 10, drawing walls as # and open spaces as ., the corner of the building containing 0,0 
// would look like this:
// 
//   0123456789
// 0 .#.####.##
// 1 ..#..#...#
// 2 #....##...
// 3 ###.#.###.
// 4 .##..#..#.
// 5 ..##....#.
// 6 #...##.###
// Now, suppose you wanted to reach 7,4. The shortest route you could take is marked as O:
// 
//   0123456789
// 0 .#.####.##
// 1 .O#..#...#
// 2 #OOO.##...
// 3 ###O#.###.
// 4 .##OO#OO#.
// 5 ..##OOO.#.
// 6 #...##.###
// Thus, reaching 7,4 would take a minimum of 11 steps (starting from your current location, 1,1).
// 
// What is the fewest number of steps required for you to reach 31,39?
// 
// Your puzzle input is 1352.
// 90

// --- Part Two ---
// How many locations (distinct x,y coordinates, including your starting location) can you reach in at most 50 steps?
// 135

package aoc2016;

public class Day13 {
	private static final int INPUT = 1352;
	
	public static void main(String[] args) {
//		int x = 60;
//		int y = 60;
//		int number = x*x + 3*x + 2*x*y + y + y*y + INPUT;

		System.out.print("  ");
		for (int x = 0; x < 60; x++) {
			System.out.print(x % 10);
		}
		System.out.println();
		
		for (int y = 0; y < 50; y++) {
			System.out.print(y % 10 + " ");
			for (int x = 0; x < 60; x++)
				System.out.print(isOpenSpace(x*x + 3*x + 2*x*y + y + y*y + INPUT));
			System.out.println();
		}
		
	}
	
	private static char isOpenSpace(int num) {
		if (Integer.toBinaryString(num).replaceAll("0", "").length() % 2 == 0)
			return '.';
		return '#';
	}
}



//    01234567890123456789012345678901234567890
// 0  .##.##..XXXXXXXXXX###.....#####.#####....
// 1  #X######.##X####XXX#.##....#..#.#..#.##.#
// 2  XXXX##.#..#X##.#X#X######..#.##..#.####..
// 3  ##XXXX###..X...#XX#..#XX####.###..#..#.#.
// 4  X####X####..###.#X##.#X#...#..#.#.##.##..
// 5  XXXXXXX######X###XX###XX##.#..###..##.#..
// 6  XX##X#XX##XX#XXXXXXX###X#..###......#.###
// 7  ##.#XXXXXXXX##X##X#XXXXX#....#.##.#.###..
// 8  .##.######X#.#X##XXX###X##...##.#..#..##.
// 9  #.##....##XX##X#.###..#X###.#.##.#..#..##
// 10 ##.#..#.###X#XX#...#.##XXX#..#.#..#......
// 11 .######..#XX#X######.#####.#..###..##.###
// 12 ...#.....##X#X##XX#...##.#.##.####..#.###
// 13 ##.#.##...#XXXXXXX#......#.....#.###...##
// 14 #..#.###.#######X########.##.#.###.##...#
// 15 #..#..##.#...###X##XXX#.##.#..#.....##...
// 16 ###.#.##.#.....#XXXX#XX#.##.#.####.#.#.##
// 17 #.###...###.##.#X###.#X##.###...##..##.#.
// 18 #.....#..##.#..##..##XXX#.....#..##.#..#.
// 19 #..#####....#...###.#XXX##.#####.#..#..##
// 20 ##....#.##.###.#..#.##X#.###oo#..##.#####
// 21 .###..####..##..#.#.##XX#XXXoo#...#.#..##
// 22 ##.#....#.#...#..##..##XXX###o##.##..#..#
// 23 ...###..##.##..#.###.#.####.#o##.###.#...
// 24 ####.##..#######..#..#.....##ooooo#..####
// 25 #.....###..#......#..###.#.#.###.o#.....#
// 26 #.##.#..##.#..########.###.###.##o##..#.#
// 27 #.##..#...####....#.......#...#.#o#####.#
// 28 #.#.#...#....#..#.#.##..#.###..##o##...##
// 29 ..##.############.#.#####....#.#.oooo#.##
// 30 .#.#.#..##..#....##..#....##.#.#..##o#...
// 31 ..##.#....#.##.#.#.#.#.###.#..######o#.##
// 32 #.#..##.#..#.#.#.##..##..##.#.....#oo###.
// 33 ..#.#.##.#...#.##.#...#.#.######..#o#..##
// 34 ..#..#.#..###...#.##.##.##..##.#..#oooo.#
// 35 #..#..###.#.#.#.####.##..#.....##..##.o.#
// 36 ##.##.###..##..#...#.....#..######..##o##
// 37 .#.....#.#.#.#..##.#.##.#####..#.##.##o##
// 38 #.####.##..#..#.#..##.#.##..#..####.##o..
// 39 ##....#.#.###...#...##......###oo##oooo#.
// 40 .#..#.#.#.####.###.#.#####.#...#oooo##.#.
// 41 #####.#....###..##......##..##...###.#.#.
// 42 .#...###.#..#.#...##..#.###.#.##.#.##..##
