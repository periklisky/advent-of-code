// --- Day 13: Knights of the Dinner Table ---
// In years past, the holiday feast with your family hasn't gone so well. Not everyone gets along! This year, you resolve, will be different. 
// You're going to find the optimal seating arrangement and avoid all those awkward conversations.
// 
// You start by writing up a list of everyone invited and the amount their happiness would increase or decrease if they were to find 
// themselves sitting next to each other person. You have a circular table that will be just big enough to fit everyone comfortably, 
// and so each person will have exactly two neighbors.
// 
// For example, suppose you have only four attendees planned, and you calculate their potential happiness as follows:
// 
// Alice would gain 54 happiness units by sitting next to Bob.
// Alice would lose 79 happiness units by sitting next to Carol.
// Alice would lose 2 happiness units by sitting next to David.
// Bob would gain 83 happiness units by sitting next to Alice.
// Bob would lose 7 happiness units by sitting next to Carol.
// Bob would lose 63 happiness units by sitting next to David.
// Carol would lose 62 happiness units by sitting next to Alice.
// Carol would gain 60 happiness units by sitting next to Bob.
// Carol would gain 55 happiness units by sitting next to David.
// David would gain 46 happiness units by sitting next to Alice.
// David would lose 7 happiness units by sitting next to Bob.
// David would gain 41 happiness units by sitting next to Carol.
// Then, if you seat Alice next to David, Alice would lose 2 happiness units (because David talks so much), but David would gain 46 happiness 
// units (because Alice is such a good listener), for a total change of 44.
// 
// If you continue around the table, you could then seat Bob next to Alice (Bob gains 83, Alice gains 54). Finally, seat Carol, 
// who sits next to Bob (Carol gains 60, Bob loses 7) and David (Carol gains 55, David gains 41). The arrangement looks like this:
// 
//      +41 +46
// +55   David    -2
// Carol       Alice
// +60    Bob    +54
//      -7  +83
// After trying every other seating arrangement in this hypothetical scenario, you find that this one is the most optimal, 
// with a total change in happiness of 330.
// 
// What is the total change in happiness for the optimal seating arrangement of the actual guest list?

package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.util.Pair;
import utils.Utils;

public class Day13 {
	private static final String INPUT_NAME = "aoc2015_day13.txt";
	
	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		Map<Pair<String, String>, Integer> infos = new HashMap<>();
		Set<Person> persons = new HashSet<>();
		List<Person> arrangements = new LinkedList<>();

		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(" ");
				String person1 = tokens[0];
				String person2 = tokens[10].substring(0, tokens[10].length()-1);
				int weight = (tokens[2].equals("gain")) ? 1 : -1;
				int unit = Integer.valueOf(tokens[3]);

				persons.add(new Person(person1));

				infos.put(new Pair<String, String>(person1, person2), weight * unit);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Entry<Pair<String, String>, Integer> info : infos.entrySet()) {
			System.out.println(info.getKey().getKey() + "-" + info.getKey().getValue() + " " + info.getValue());
		}
		
		int optimal = 0;
	}
	
	public List<Person> generatePermutation(List<Person> arrangements) {
		if (arrangements.isEmpty()) {
			return new ArrayList<>();			
		}
		
		Person firstPerson = arrangements.remove(0);
		List<Person> returnValue = new ArrayList<>();
		List<Person> permutations = generatePermutation(arrangements);
		for (Person person : permutations) {
			
		}
		
		return returnValue;
	}

	public static class Person {
		private String name;
		private int happinessLeft;
		private int happinessRight;

		public Person(String name) {
			this.name = name;
			this.happinessLeft = 0;
			this.happinessRight = 0;
		}

		public String getName() { return name; }
		public void setName(String name) { this.name = name; }
		public int getHappinessLeft() { return happinessLeft; }
		public void setHappinessLeft(int happinessLeft) { this.happinessLeft = happinessLeft; }
		public int getHappinessRight() { return happinessRight; }
		public void setHappinessRight(int happinessRight) { this.happinessRight = happinessRight; }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + happinessLeft;
			result = prime * result + happinessRight;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (happinessLeft != other.happinessLeft)
				return false;
			if (happinessRight != other.happinessRight)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
	}
}
