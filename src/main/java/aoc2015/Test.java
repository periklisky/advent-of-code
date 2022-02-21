package aoc2015;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Test {
	private static List<List<String>> combinations = new ArrayList<>();

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");

		populateCombinations(list);
		combinations.forEach(System.out::println);
	}

	public static void populateCombinations(List<String> list) {
		List<String> copy = new ArrayList<>(list);
		ListIterator<String> it = copy.listIterator();
		String x = it.next();
//		List<String> singleItem = new ArrayList<>();
//		singleItem.add(x);
//		combinations.add(singleItem);
		
		if (copy.size() > 1) {
			it.remove();
			populateCombinations(copy);
			for (int i = 0; i < copy.size(); i++) {
				List<String> combination = new ArrayList<>();
				combination.add(x);
				combination.add(copy.get(i));

				combinations.add(combination);
			}
		} 
//		else {
//			List<String> emptyList = new ArrayList<>();
//			combinations.add(emptyList);
//		}
	}
}
