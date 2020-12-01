import java.util.*;

public class Day_01 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Integer> list = new ArrayList<>();
		int part1 = 0;
		int part2 = 0;

		while (sc.hasNextInt()) {
			int next = sc.nextInt();
			if (part1 == 0)
				for (int i = 0; i < list.size(); i++)
					if (next + list.get(i) == 2020)
						part1 = next * list.get(i);
			if (part2 == 0)
			for (int i = 0; i < list.size(); i++)
				for (int j = i + 1; j < list.size(); j++)
					if (next + list.get(i) + list.get(j) == 2020)
						part2 = next * list.get(i) * list.get(j);
			list.add(next);
		}
		sc.close();
		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
}
