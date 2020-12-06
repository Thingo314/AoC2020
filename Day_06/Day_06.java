import java.util.*;

public class Day_06 {
	static int answered1 = 0;
	static int answered2 = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String> answers = new ArrayList<>();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals("")) {
				calc(answers);
				answers.clear();
			} else {
				answers.add(line);
			}
		}
		calc(answers);
		sc.close();

		System.out.println("Part 1: " + answered1);
		System.out.println("Part 2: " + answered2);
	}

	static void calc(ArrayList<String> ans) {
		HashMap<Character, Integer> responses = new HashMap<>();
		for (String s : ans)
			for (char c : s.toCharArray())
				responses.put(c, responses.getOrDefault(c, 0) + 1);
		answered1 += responses.size();

		for (int n : responses.values()) {
			if (n == ans.size())
				answered2++;
		}
	}
}
