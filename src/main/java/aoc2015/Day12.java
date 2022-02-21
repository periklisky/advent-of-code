package aoc2015;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import utils.Utils;

// --- Day 12: JSAbacusFramework.io ---
// Santa's Accounting-Elves need help balancing the books after a recent order. Unfortunately, their accounting software uses
// a peculiar storage format. That's where you come in.
// 
// They have a JSON document which contains a variety of things: arrays ([1,2,3]), objects ({"a":1, "b":2}), numbers, and strings. 
// Your first job is to simply find all of the numbers throughout the document and add them together.
// 
// For example:
// 
// [1,2,3] and {"a":2,"b":4} both have a sum of 6.
// [[[3]]] and {"a":{"b":4},"c":-1} both have a sum of 3.
// {"a":[-1,1]} and [-1,{"a":1}] both have a sum of 0.
// [] and {} both have a sum of 0.
// You will not encounter any strings containing numbers.
// 
// What is the sum of all numbers in the document?
// 
// 111754

public class Day12 {
	private static final String INPUT_NAME = "input_day12.json";
	private static int result = 0;

	public static void main(String[] args) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<?, ?> map = mapper.readValue(Utils.getFileFromResource(INPUT_NAME), Map.class);

			for (Map.Entry<?, ?> entry : map.entrySet()) {
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
					result += (int)list.get(i);
				}
			}
		} else if (obj instanceof LinkedHashMap) {
			Map<?, ?> map = new LinkedHashMap<>((LinkedHashMap) obj);
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				if (entry.getValue() instanceof ArrayList || entry.getValue() instanceof LinkedHashMap) {
					getResult(entry.getValue());
				} else if (entry.getValue() instanceof Integer) {
					result += (int)entry.getValue();
				}
			}
		}
	}
}
