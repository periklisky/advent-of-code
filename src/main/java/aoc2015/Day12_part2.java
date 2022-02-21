package aoc2015;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import utils.Utils;

// --- Part Two ---
// Uh oh - the Accounting-Elves have realized that they double-counted everything red.
// 
// Ignore any object (and all of its children) which has any property with the value "red". 
// Do this only for objects ({...}), not arrays ([...]).
// 
// [1,2,3] still has a sum of 6.
// [1,{"c":"red","b":2},3] now has a sum of 4, because the middle object is ignored.
// {"d":"red","e":[1,2,3,4],"f":5} now has a sum of 0, because the entire structure is ignored.
// [1,"red",5] has a sum of 6, because "red" in an array has no effect.

public class Day12_part2 {
	private static final String INPUT_NAME = "input_day12.json";
	private static int result = 0;
	private static int redCount = 0;
	private static boolean foundRed = false;

	public static void main(String[] args) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<?, ?> map = mapper.readValue(Utils.getFileFromResource(INPUT_NAME), Map.class);

			for (Map.Entry<?, ?> entry : map.entrySet()) {
				if (entry.getValue() instanceof ArrayList)
					System.out.println("ArrayList");
				if (entry.getValue() instanceof LinkedHashMap)
					System.out.println("LinkedHashMap");
				getResult(entry.getValue());
			}
			System.out.println("result: " + result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getResult(Object obj) {
		if (obj instanceof ArrayList) {
			List<?> list = new ArrayList<>((ArrayList) obj);
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) instanceof ArrayList || list.get(i) instanceof LinkedHashMap) {
					getResult(list.get(i));
				} else if (list.get(i) instanceof Integer) {
					result += (int) list.get(i);
					redCount += (int) list.get(i);
				} else if (list.get(i) instanceof String) {
					if (((String) list.get(i)).equals("red")) {
						System.out.println("result " + result + " // redCount " + redCount);
						result -= redCount;
						redCount = 0;
						foundRed = true;
						return;
					}
				}
			}
		} else if (obj instanceof LinkedHashMap) {
			Map<?, ?> map = new LinkedHashMap<>((LinkedHashMap) obj);
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				if (entry.getValue() instanceof ArrayList || entry.getValue() instanceof LinkedHashMap) {
					getResult(entry.getValue());
				} else if (entry.getValue() instanceof Integer) {
					result += (int) entry.getValue();
					redCount += (int) entry.getValue();
				} else if (entry.getValue() instanceof String) {
					if (((String) entry.getValue()).equals("red")) {
						System.out.println("result " + result + " // redCount " + redCount);
						result -= redCount;
						redCount = 0;
						foundRed = true;
						return;
					}
				}
			}
		}
		foundRed = false;
	}
}
