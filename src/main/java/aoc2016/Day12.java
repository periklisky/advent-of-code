// --- Day 12: Leonardo's Monorail ---
// You finally reach the top floor of this building: a garden with a slanted glass ceiling. Looks like there are no more stars to be had.
// 
// While sitting on a nearby bench amidst some tiger lilies, you manage to decrypt some of the files you extracted from the servers downstairs.
// 
// According to these documents, Easter Bunny HQ isn't just this building - it's a collection of buildings in the nearby area. They're all connected 
// by a local monorail, and there's another building not far from here! Unfortunately, being night, the monorail is currently not operating.
// 
// You remotely connect to the monorail control systems and discover that the boot sequence expects a password. The password-checking logic 
// (your puzzle input) is easy to extract, but the code it uses is strange: it's assembunny code designed for the new computer you just assembled. 
// You'll have to execute the code and get the password.
// 
// The assembunny code you've extracted operates on four registers (a, b, c, and d) that start at 0 and can hold any integer. However, it seems 
// to make use of only a few instructions:
// 
// cpy x y copies x (either an integer or the value of a register) into register y.
// inc x increases the value of register x by one.
// dec x decreases the value of register x by one.
// jnz x y jumps to an instruction y away (positive means forward; negative means backward), but only if x is not zero.
// The jnz instruction moves relative to itself: an offset of -1 would continue at the previous instruction, while an offset of 2 would 
// skip over the next instruction.
// 
// For example:
// 
// cpy 41 a
// inc a
// inc a
// dec a
// jnz a 2
// dec a
// The above code would set register a to 41, increase its value by 2, decrease its value by 1, and then skip the last dec a (because a is not zero, 
// so the jnz a 2 skips it), leaving register a at 42. When you move past the last instruction, the program halts.
// 
// After executing the assembunny code in your puzzle input, what value is left in register a?
// 318117


// --- Part Two ---
// As you head down the fire escape to the monorail, you notice it didn't start; register c needs to be initialized to the position of the ignition key.
// 
// If you instead initialize register c to be 1, what value is now left in register a?
// 9227771


package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;

public class Day12 {
	private static final String INPUT = "aoc2016_day12.txt";
	private static Map<String, BigInteger> registers = new HashMap<>();
	
	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT);
		
		List<String> instructions = new ArrayList<>();
		
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				instructions.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initializeRegisters();
		
		int i = 0;
		while (i >= 0 && i < instructions.size()) {
			String instruction = instructions.get(i);
			String tokens[] = instruction.split(" ");
			
			if (instruction.contains("inc")) {
				increase(tokens[1]);
				i++;
			} else if (instruction.contains("dec")) {
				decrease(tokens[1]);				
				i++;
			} else if (instruction.contains("cpy")) {
				if (registers.get(tokens[1]) != null)
					copyValueToRegister(tokens[1], tokens[2]);
				else
					copyValueToRegister(Integer.valueOf(tokens[1]), tokens[2]);
				
				i++;
			} else if (instruction.contains("jnz")) {
				if (registers.get(tokens[1]) != null)
					i += jump(tokens[1], Integer.valueOf(tokens[2]));
				else 
					i += jump(Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]));
			}
		}
		
		for (Entry<String, BigInteger> register : registers.entrySet()) {
			System.out.println(register.getKey() + ": " + register.getValue());
		}
	}

	private static void initializeRegisters() {
		registers.put("a", BigInteger.valueOf(0));
		registers.put("b", BigInteger.valueOf(0));
		registers.put("c", BigInteger.valueOf(1));
		registers.put("d", BigInteger.valueOf(0));
	}
	
	private static void copyValueToRegister(String register1, String register2) {
		registers.put(register2, registers.get(register1));
	}
	
	private static void copyValueToRegister(int value, String register) {
		registers.put(register, BigInteger.valueOf(value));
	}
	
	private static void increase(String register) {
		registers.put(register, registers.get(register).add(BigInteger.valueOf(1)));
	}
	
	private static void decrease(String register) {
		registers.put(register, registers.get(register).subtract(BigInteger.valueOf(1)));
	}

	private static int jump(int value, int offset) {
		if (value != 0)
			return offset;
		return 1;
	}
	
	private static int jump(String register, int offset) {
		if (registers.get(register).compareTo(BigInteger.valueOf(0)) != 0)
			return offset;
		return 1;
	}
}
