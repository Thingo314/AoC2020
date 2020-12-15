import java.util.*;

public class Day_15 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] line = sc.nextLine().split(",");
		sc.close();

		int[] nums = new int[line.length];
		HashMap<Integer, Integer> whenLast = new HashMap<>();
		HashMap<Integer, Integer> whenSecondLast = new HashMap<>();
		for (int i = 0; i < line.length; i++) {
			nums[i] = Integer.parseInt(line[i]);
			whenLast.put(nums[i], i + 1);
		}

		int turn = nums.length + 1;
		int num = nums[nums.length - 1];
		int res1 = -1;
		int res2 = -1;
		while (res1 == -1 || res2 == -1) {
			if (whenSecondLast.containsKey(num)) {
				num = whenLast.get(num) - whenSecondLast.get(num);
				if (whenLast.containsKey(num))
					whenSecondLast.put(num, whenLast.get(num));
				whenLast.put(num, turn);
			} else {
				num = 0;
				if (whenLast.containsKey(num))
					whenSecondLast.put(num, whenLast.get(num));
				whenLast.put(num, turn);
			}

			if (turn == 2020) {
				res1 = num;
				System.out.println("Part 1: " + res1);
			}
			if (turn == 30000000) {
				res2 = num;
				System.out.println("Part 2: " + res2);
			}

			turn++;
		}
	}
}
