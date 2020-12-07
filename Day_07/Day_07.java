import java.util.*;

public class Day_07 {
	static HashMap<String, HashMap<String, Integer>> rules = new HashMap<>();
	static HashMap<String, Boolean> containsGold = new HashMap<>();
	static HashMap<String, Integer> containsInside = new HashMap<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			int idx = line.indexOf("bags");
			String biggerBag = line.substring(0, idx-1);

			idx = line.indexOf("contain");
			String[] otherBags = line.substring(idx + 8).split(" ");
			HashMap<String, Integer> smallerBags = new HashMap<>();

			for (int i = 0; i < otherBags.length; i += 4) {
				if (otherBags[i].equals("no"))
					break;
				smallerBags.put(otherBags[i+1] + " " + otherBags[i+2], Integer.parseInt(otherBags[i]));
			}

			rules.put(biggerBag, smallerBags);
		}
		sc.close();

		int result1 = 0;
		for (String bag : rules.keySet())
			if (hasGold(bag))
				result1++;

		int result2 = hasInside("shiny gold");

		System.out.println("Part 1: " + result1);
		System.out.println("Part 2: " + result2);
	}

	static boolean hasGold(String bag) {
		if (containsGold.containsKey(bag))
			return containsGold.get(bag);

		boolean contains = false;
		for (String smaller : rules.get(bag).keySet()) {
			if (smaller.equals("shiny gold"))
				contains = true;
			contains = contains || hasGold(smaller);

			if (contains)
				break;
		}

		containsGold.put(bag, contains);
		return contains;
	}

	static int hasInside(String bag) {
		if (rules.get(bag).size() == 0)
			return 0;
		if (containsInside.containsKey(bag))
			return containsInside.get(bag);

		int contains = 0;
		for (String smaller : rules.get(bag).keySet()) {
			int mul = rules.get(bag).get(smaller);
			int smallerBagContains = hasInside(smaller);
			contains += mul * (smallerBagContains + 1);
		}

		containsInside.put(bag, contains);
		return contains;
	}
}
