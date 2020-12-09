import java.util.*;

public class Day_09 {
	public static void main(String[] args) {
		ArrayList<Long> data = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLong())
			data.add(sc.nextLong());
		sc.close();

		long invalidNum = Long.MIN_VALUE;
		for (int i = 25; i < data.size(); i++) {
			boolean found = false;
			for (int j = i - 25; j < i; j++) {
				for (int k = j + 1; k < i; k++) {
					if (data.get(j) + data.get(k) == data.get(i)) {
						found = true;
						break;
					}
				}
				if (found)
					break;
			}

			if (!found) {
				invalidNum = data.get(i);
				break;
			}
		}

		long minmaxSum = Long.MIN_VALUE;
		int a = 0, b = 1;
		while (true) {
			long sum = 0;
			long min = Long.MAX_VALUE;
			long max = Long.MIN_VALUE;
			for (int c = a; c <= b; c++) {
				sum += data.get(c);
				min = Math.min(min, data.get(c));
				max = Math.max(max, data.get(c));
			}

			if (sum == invalidNum) {
				minmaxSum = min + max;
				break;
			} else if (sum > invalidNum)
				a++;
			else
				b++;
		}

		System.out.println("Part 1: " + invalidNum);
		System.out.println("Part 2: " + minmaxSum);
	}
}
