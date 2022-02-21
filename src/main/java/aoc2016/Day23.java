// --- Day 23: Safe Cracking ---
// This is one of the top floors of the nicest tower in EBHQ. The Easter Bunny's private office is here, complete with a safe hidden behind a
// painting, and who wouldn't hide a star in a safe behind a painting?
// 
// The safe has a digital screen and keypad for code entry. A sticky note attached to the safe has a password hint on it: "eggs". The painting 
// is of a large rabbit coloring some eggs. You see 7.
// 
// When you go to type the code, though, nothing appears on the display; instead, the keypad comes apart in your hands, apparently having been 
// smashed. Behind it is some kind of socket - one that matches a connector in your prototype computer! You pull apart the smashed keypad and 
// extract the logic circuit, plug it into your computer, and plug your computer into the safe.
// 
// Now, you just need to figure out what output the keypad would have sent to the safe. You extract the assembunny code from the logic chip 
// (your puzzle input).
// The code looks like it uses almost the same architecture and instruction set that the monorail computer used! You should be able to use the 
// same assembunny interpreter for this as you did there, but with one new instruction:
// 
// tgl x toggles the instruction x away (pointing at instructions like jnz does: positive means forward; negative means backward):
// 
// For one-argument instructions, inc becomes dec, and all other one-argument instructions become inc.
// For two-argument instructions, jnz becomes cpy, and all other two-instructions become jnz.
// The arguments of a toggled instruction are not affected.
// If an attempt is made to toggle an instruction outside the program, nothing happens.
// If toggling produces an invalid instruction (like cpy 1 2) and an attempt is later made to execute that instruction, skip it instead.
// If tgl toggles itself (for example, if a is 0, tgl a would target itself and become inc a), the resulting instruction is not executed until 
// the next time it is reached.
// For example, given this program:
// 
// cpy 2 a
// tgl a
// tgl a
// tgl a
// cpy 1 a
// dec a
// dec a
// cpy 2 a initializes register a to 2.
// The first tgl a toggles an instruction a (2) away from it, which changes the third tgl a into inc a.
// The second tgl a also modifies an instruction 2 away from it, which changes the cpy 1 a into jnz 1 a.
// The fourth line, which is now inc a, increments a to 3.
// Finally, the fifth line, which is now jnz 1 a, jumps a (3) instructions ahead, skipping the dec a instructions.
// In this example, the final value in register a is 3.
// 
// The rest of the electronics seem to place the keypad entry (the number of eggs, 7) in register a, run the code, and then send the value left 
// in register a to the safe.
// 
// What value should be sent to the safe?

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import utils.Assembunny;
import utils.Utils;

public class Day23 {
	private static final String INPUT = "aoc2016_day23.txt";

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

		Map<String, Integer> registers = Assembunny.initializeRegisters(0, 0, 0, 0);
		
		ListIterator<String> it = instructions.listIterator();
		int index = it.nextIndex();
		while (index > -1 && index < instructions.size()) {
			it = instructions.listIterator(index);
			String instruction = it.next();
			String tokens[] = instruction.split(" ");
			
			if (instruction.contains("tgl")) {
				int x = Assembunny.getRegisterValue(tokens[1]);
				if (index + x > instructions.size() || index + x < 0)
					continue;
				it = instructions.listIterator(index + x);
				instruction = it.next();
				tokens = instruction.split(" ");
				
				if (tokens.length == 2) {
					if (instruction.contains("inc"))
						it.set(instruction.replace("inc", "dec"));
					else
						it.set(instruction = instruction.replace(instruction.substring(0, 3), "inc"));
				} else if (tokens.length == 3) {
					if (instructions.contains("jnz"))
						it.set(instruction.replace("jnz", "cpy"));
					else
						it.set(instruction.replace(instruction.substring(0, 3), "jnz"));
				}
				index++;
			}
			else if (instruction.contains("inc")) {
				if (tokens.length != 2)
					continue;
				Assembunny.increase(tokens[1]);
				index++;
			} else if (instruction.contains("dec")) {
				if (tokens.length != 2)
					continue;
				Assembunny.decrease(tokens[1]);
				index++;
			} else if (instruction.contains("cpy")) {
				if (tokens.length != 3)
					continue;
				if (registers.get(tokens[1]) != null)
					Assembunny.copyValueToRegister(tokens[1], tokens[2]);
				else
					Assembunny.copyValueToRegister(Integer.valueOf(tokens[1]), tokens[2]);
				index++;
			} else if (instruction.contains("jnz")) {
				if (tokens.length != 3)
					continue;

				if (registers.get(tokens[1]) != null && registers.get(tokens[2]) != null) {
					index += Assembunny.jump(tokens[1], tokens[2]);
				}
				else if (registers.get(tokens[1]) != null && registers.get(tokens[2]) == null) {
					index += Assembunny.jump(tokens[1], Integer.valueOf(tokens[2]));		
				}
				else if (registers.get(tokens[1]) == null && registers.get(tokens[2]) != null) {
					index += Assembunny.jump(Integer.valueOf(tokens[1]), tokens[2]);
				}
				else {
					index += Assembunny.jump(Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]));
				}
			}
		}
		
		Assembunny.printRegisters();
	}
}
