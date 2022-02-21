package aoc2016;

import aoc2016.Day22.Node;

public class Test {
	public static void main(String[] args) {
		Node a = new Node(0, 0, 1, 2, 3);
		Node b = new Node(0, 0, 1, 2, 3);
		Node c = new Node(0, 1, 1, 2, 3);
		
		
		System.out.println("true: "  + a.equals(b));
		System.out.println("false: " + a.equals(c));
		System.out.println("false: " + b.equals(c));
	}
	
	public static class Node {
		int x;
		int y;
		int size;
		int used;
		int available;
		
		public Node(int x, int y, int size, int used, int available) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.used = used;
			this.available = available;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getUsed() {
			return used;
		}

		public void setUsed(int used) {
			this.used = used;
		}

		public int getAvailable() {
			return available;
		}

		public void setAvailable(int available) {
			this.available = available;
		}
		
		public void print() {
			System.out.println(x + " " + y + " " + size + " " + used + " " + available);
		}
		
		public boolean isEmpty() {
			return used != 0;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
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
			Node other = (Node) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}
}