//--- Day 23: Opening the Turing Lock ---
// Little Jane Marie just got her very first computer for Christmas from some unknown benefactor. It comes with instructions and an example program, 
// but the computer itself seems to be malfunctioning. She's curious what the program does, and would like you to help her run it.
// 
// The manual explains that the computer supports two registers and six instructions (truly, it goes on to remind the reader, 
// a state-of-the-art technology). 
// The registers are named a and b, can hold any non-negative integer, and begin with a value of 0. The instructions are as follows:
// 
// hlf r sets register r to half its current value, then continues with the next instruction.
// tpl r sets register r to triple its current value, then continues with the next instruction.
// inc r increments register r, adding 1 to it, then continues with the next instruction.
// jmp offset is a jump; it continues with the instruction offset away relative to itself.
// jie r, offset is like jmp, but only jumps if register r is even ("jump if even").
// jio r, offset is like jmp, but only jumps if register r is 1 ("jump if one", not odd).
// All three jump instructions work with an offset relative to that instruction. The offset is always written with a prefix + or - to indicate 
// the direction of the jump (forward or backward, respectively). 
// For example, jmp +1 would simply continue with the next instruction, while jmp +0 would continuously jump back to itself forever.
// 
// The program exits when it tries to run an instruction beyond the ones defined.
// 
// For example, this program sets a to 2, because the jio instruction causes it to skip the tpl instruction:
// 
// inc a
// jio a, +2
// tpl a
// inc a
// What is the value in register b when the program in your puzzle input is finished executing?

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class Day23 {
	private static final String INPUT_NAME = "aoc2015_day23.txt";
	private static Map<String, Register> registers = new HashMap<>();

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		Register a = new Register("a");
		Register b = new Register("b");
		registers.put("a", a);
		registers.put("b", b);
		
		List<String> instructions = new ArrayList<>();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				instructions.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int currentInstruction = 0;
		while (currentInstruction >= 0 && currentInstruction < instructions.size()) {
			String instruction = instructions.get(currentInstruction);
//			System.out.println("current instruction: " + instruction);
			String tokens[] = parse(instruction);
			currentInstruction += execute(tokens);
		}
		System.out.println(b.getValue());
	}

	public static String[] parse(String instruction) {
		String tokens[] = instruction.split(" ");
		if (tokens.length == 3)
			tokens[1] = tokens[1].substring(0, 1);
		return tokens;
	}

	public static int execute(String tokens[]) {
		String command = tokens[0];
		int count = 1;
		
		switch (command) {
		case "hlf":
			registers.put(tokens[1], registers.get(tokens[1]).half());
			break;
		case "tpl":
			registers.put(tokens[1], registers.get(tokens[1]).triple());
			break;
		case "inc":
			registers.put(tokens[1], registers.get(tokens[1]).increase());
			break;
		case "jmp":
			count = Integer.valueOf(tokens[1]);
			break;
		case "jie":
			if (registers.get(tokens[1]).getValue() / 2 == 0)
				count = Integer.valueOf(tokens[2]);
			break;
		case "jio":
			if (registers.get(tokens[1]).getValue() / 1 == 0)
				count = Integer.valueOf(tokens[2]);
			break;
		default:
			break;
		}
		return count;
	}

	public static class Register {
		private final String name;
		private long value = 0;
		
		public Register(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public Register half() {
			value /= 2;
			return this;
		}
		
		public Register triple() {
			value *= 3;
			if (value < 0) value = 0;
			return this;
		}
		
		public Register increase() {
			value++;
			if (value < 0) value = 0;
			return this;
		}
		
		public long getValue() {
			return value;
		}
	}
}