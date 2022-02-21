package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Assembunny {
	private static Map<String, Integer> registers = new HashMap<>();

	public static Map<String, Integer> initializeRegisters(int a, int b, int c, int d) {
		registers.put("a", a);
		registers.put("b", b);
		registers.put("c", c);
		registers.put("d", d);
		
		return registers;
	}
	
	public static void copyValueToRegister(String register1, String register2) {
		registers.put(register2, registers.get(register1));
	}
	
	public static void copyValueToRegister(int value, String register) {
		registers.put(register, value);
	}
	
	public static void increase(String register) {
		registers.put(register, registers.get(register) + 1);
	}
	
	public static void decrease(String register) {
		int x = (registers.get(register) > 0) ? registers.get(register) - 1 : 0;
		registers.put(register, x);
	}
	
	public static int jump(String r1, String r2) {
		if (registers.get(r1) != 0)
			return registers.get(r2);
		return 1;
	}

	public static int jump(int value, int offset) {
		if (value != 0)
			return offset;
		return 1;
	}
	
	public static int jump(String register, int offset) {
		if (registers.get(register) != 0)
			return offset;
		return 1;
	}
	
	public static int jump(int x, String register) {
		if (x != 0)
			return registers.get(register);
		return 1;
	}
	
	public static void printRegisters() {
		for (Entry<String, Integer> register : registers.entrySet()) {
			System.out.println(register.getKey() + ": " + register.getValue());
		}
	}
	
	public static int getRegisterValue(String r) {
		return registers.get(r);
	}
}
