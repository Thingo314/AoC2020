import java.util.*;

public class Day_23 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashMap<Integer, Node> mapping = new HashMap<>();
		LinkedList<Integer> cups = new LinkedList<>();
		String input = sc.nextLine();
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < input.length(); i++) {
			int val = Integer.parseInt(input.substring(i, i+1));
			cups.add(val);
			min = Math.min(min, val);
			max = Math.max(max, val);
		}
		sc.close();

		LinkedList<Integer> part1Cups = new LinkedList<>(cups);
		LinkedList<Integer> removed = new LinkedList<>();
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 3; j++)
				removed.add(part1Cups.remove(1));

			int destinationCup = part1Cups.get(0) - 1;
			if (destinationCup < min)
				destinationCup = max;
			while (removed.contains(destinationCup)) {
				destinationCup--;
				if (destinationCup < min)
					destinationCup = max;
			}

			int destination = part1Cups.indexOf(destinationCup);
			while (removed.size() != 0)
				part1Cups.add(destination + 1, removed.removeLast());

			part1Cups.add(part1Cups.removeFirst());
		}

		while (part1Cups.peekFirst() != 1)
			part1Cups.add(part1Cups.removeFirst());
		part1Cups.removeFirst();

		int part1 = 0;
		while (part1Cups.size() != 0) {
			part1 *= 10;
			part1 += part1Cups.removeFirst();
		}

		while (cups.size() != 1_000_000) {
			max++;
			cups.add(max);
		}

		CircularList l = new CircularList(cups);
		Node start = l.getStart();
		for (int i = 0; i < cups.size(); i++) {
			mapping.put(start.data, start);
			start = start.right;
		}

		int currentCup = l.getStart().data;
		for (int i = 0; i < 10_000_000; i++) {
			Node current = mapping.get(currentCup);
			Node a = current.removeRight();
			Node b = current.removeRight();
			Node c = current.removeRight();

			int destinationCup = currentCup - 1;
			if (destinationCup < min)
				destinationCup = max;

			Node destination = mapping.get(destinationCup);
			while (destination == a || destination == b || destination == c) {
				destinationCup--;
				if (destinationCup < min)
					destinationCup = max;

				destination = mapping.get(destinationCup);
			}

			destination.addRight(c);
			destination.addRight(b);
			destination.addRight(a);
			currentCup = current.right.data;
		}

		Node theOne = mapping.get(1);
		long a = theOne.right.data;
		long b = theOne.right.right.data;
		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + (a * b));
	}
}

class Node {
	Node left = null;
	Node right = null;
	int data = 0;

	public Node(int n) {
		data = n;
	}

	public Node removeRight() {
		Node res = this.right;
		this.right = res.right;
		this.right.left = this;
		return res;
	}

	public void addRight(Node n) {
		Node wasRight = this.right;
		wasRight.left = n;
		n.right = wasRight;
		this.right = n;
		n.left = this;
	}
}

class CircularList {
	Node start = null;

	public CircularList(LinkedList<Integer> list) {
		LinkedList<Integer> copy = new LinkedList<>(list);
		start = new Node(copy.removeFirst());
		Node tail = start;
		while (copy.size() > 1) {
			Node next = new Node(copy.removeFirst());
			tail.right = next;
			next.left = tail;

			tail = next;
		}

		Node end = new Node(copy.removeFirst());
		tail.right = end;
		start.left = end;
		end.right = start;
		end.left = tail;
	}

	public Node getStart() {
		return start;
	}
}
