import java.util.*;

public class Day_10 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Integer> ratings = new ArrayList<>();
		int highest = Integer.MIN_VALUE;
		while (sc.hasNextInt()) {
			int adapter = sc.nextInt();
			ratings.add(adapter);
			highest = Math.max(highest, adapter);
		}
		sc.close();

		ratings.add(0);
		ratings.add(highest + 3);
		ratings.sort(null);

		int diff1 = 0;
		int diff3 = 0;
		for (int i = 0; i < ratings.size() - 1; i++)
			if (ratings.get(i) + 1 == ratings.get(i+1))
				diff1++;
			else if (ratings.get(i) + 3 == ratings.get(i + 1))
				diff3++;

		long[] ways = new long[ratings.size()];

		ways[ratings.size() - 1] = 1;
		for (int i = ratings.size() - 2; i >= 0; i--)
			for (int j = i + 1; j < ratings.size(); j++)
				if (ratings.get(j) - ratings.get(i) <= 3)
					ways[i] += ways[j];
				else
					break;

		System.out.println("Part 1: " + (diff1 * diff3));
		System.out.println("Part 2: " + ways[0]);
	}
}
