import java.util.*;

public class Day_03 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<String> terrain = new ArrayList<>();
		while (sc.hasNextLine())
			terrain.add(sc.nextLine());

		sc.close();

		ArrayList<int[]> slopes = new ArrayList<>();
		slopes.add(new int[] {1, 1});
		slopes.add(new int[] {3, 1});
		slopes.add(new int[] {5, 1});
		slopes.add(new int[] {7, 1});
		slopes.add(new int[] {1, 2});

		long trees1 = 0;
		long trees2 = 1;

		for (int slopeNum = 0; slopeNum < slopes.size(); slopeNum++) {
			long seen = 0;
			int[] slope = slopes.get(slopeNum);

			for (int down = slope[1], right = slope[0]; down < terrain.size(); down += slope[1], right += slope[0])
				if (terrain.get(down).charAt(right % terrain.get(down).length()) == '#')
					seen++;

			trees2 *= seen;
			if (slopeNum == 1)
				trees1 = seen;
		}

		System.out.println("Part 1: " + trees1);
		System.out.println("Part 2: " + trees2);
	}
}
