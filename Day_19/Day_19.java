import java.util.*;

public class Day_19 {
	public static void main(String[] args) {
		// rules will contain the dependencies
		// endRule will contain all possible strings that satisfy the rule
		HashMap<Integer, ArrayList<ArrayList<Integer>>> rules = new HashMap<>();
		HashMap<Integer, HashSet<String>> endRule = new HashMap<>();

		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals(""))
				break;

			String[] rule = line.split(" ");
			int numRule = Integer.parseInt(rule[0].substring(0, rule[0].length() - 1));
			// check if the rule is a single char rule
			if (rule.length == 2 && rule[1].indexOf("\"") != -1) {
				HashSet<String> end = new HashSet<>();
				end.add(rule[1].substring(1, 2));
				endRule.put(numRule, end);

				continue;
			}

			// for other rules, store which rules it depends on
			ArrayList<ArrayList<Integer>> rulesList = new ArrayList<>();
			ArrayList<Integer> subRule = new ArrayList<>();

			for (int i = 1; i < rule.length; i++) {
				if (rule[i].equals("|")) {
					rulesList.add(subRule);
					subRule = new ArrayList<>();
				} else {
					subRule.add(Integer.parseInt(rule[i]));
				}
			}

			rulesList.add(subRule);
			rules.put(numRule, rulesList);
		}

		generate(rules, endRule, 0);

		int part1 = 0;
		int part2 = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			if (endRule.get(0).contains(line)) {
				part1++;
				part2++;
				continue;
			}

			// check part 2
			// the updated 0 rule now requires the following
			// there is some number of matches for 42 at the beginning
			// there is some number of matches for 31 at the end
			// the number for 42 is strictly greater than the number for 31
			// this overlaps with the original so if the original rule was
			// satisfied, there is no need to perform this check
			int start42 = 0, end31 = 0;
			String string42 = startsWith(endRule, line, 42);
			while (!string42.equals("")) {
				start42++;
				line = line.substring(string42.length());
				string42 = startsWith(endRule, line, 42);
			}

			String string31 = endsWith(endRule, line, 31);
			while (!string31.equals("")) {
				end31++;
				line = line.substring(string31.length());
				string31 = endsWith(endRule, line, 31);
			}

			if (line.equals("") && end31 > 0 && start42 > end31)
				part2++;
		}
		sc.close();

		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}

	static String startsWith(HashMap<Integer, HashSet<String>> endRule, String n, int m) {
		for (String s : endRule.get(m))
			if (n.startsWith(s))
				return s;

		return "";
	}

	static String endsWith(HashMap<Integer, HashSet<String>> endRule, String n, int m) {
		for (String s : endRule.get(m))
			if (n.endsWith(s))
				return s;

		return "";
	}

	static void generate(HashMap<Integer, ArrayList<ArrayList<Integer>>> rules, HashMap<Integer, HashSet<String>> endRule, int n) {
		if (endRule.containsKey(n))
			return;

		ArrayList<ArrayList<Integer>> rulesList = rules.get(n);
		HashSet<String> result = new HashSet<>();

		for (ArrayList<Integer> subRule : rulesList) {
			ArrayList<String> possibilities = new ArrayList<>();
			possibilities.add("");

			for (int m : subRule) {
				generate(rules, endRule, m);

				ArrayList<String> nextPossible = new ArrayList<>();
				for (String s : possibilities)
					for (String t : endRule.get(m))
						nextPossible.add(s + t);

				possibilities = nextPossible;
			}

			result.addAll(possibilities);
		}

		endRule.put(n, result);
	}
}
