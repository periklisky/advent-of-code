package aoc2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.Utils;

public class Day09 {
	private static final String INPUT_NAME = "input_day9.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		Map<Connection, Integer> map = new HashMap<>();
		Map<String, Integer> costs = new HashMap<>();

		Set<Node> unvisited = new HashSet<>();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(" ");
				unvisited.add(new Node(tokens[0]));
				unvisited.add(new Node(tokens[2]));
				int cost = Integer.valueOf(tokens[4]);
			}
			
			for (Map.Entry<Connection, Integer> entry : map.entrySet()) {
				Connection connection = entry.getKey();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class Connection {
		private Node node1, node2;

		public Connection(Node node1, Node node2) {
			this.node1 = node1;
			this.node2 = node2;
		}

		public Node getNode1() { return node1; }
		public Node getNode2() { return node2; }
	}
	
	public static class Node {
		private String name;
		private boolean visited;
		
		public Node(String name) {
			this.name = name;
			visited = false;
		}
		
		public String getName() { return name; }
		public boolean isVisited() { return visited; }
	}
}
