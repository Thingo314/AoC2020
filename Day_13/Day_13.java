import java.util.*;

public class Day_13 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long earliest = Long.parseLong(sc.nextLine());
		String[] ids = sc.nextLine().split(",");
		sc.close();

		ArrayList<Long> running = new ArrayList<>();
		ArrayList<Long> offset = new ArrayList<>();
		for (int i = 0; i < ids.length; i++) {
			if (!ids[i].equals("x")) {
				running.add(Long.parseLong(ids[i]));
				offset.add((long)i);
			}
		}

		long depart = earliest;
		long id = 0;
		boolean found = false;
		while (!found) {
			for (long time : running) {
				if (depart % time == 0) {
					found = true;
					id = time;
				}
			}
			if (found)
				break;
			depart++;
		}

		ArrayList<Long> a = new ArrayList<>();
		for (int i = 0; i < running.size(); i++)
			a.add((-offset.get(i)) % running.get(i) + running.get(i));
			// note that -offset is guaranteed to be non-negative as
			// the values added are array indices.
			// the above expression is therefore non-negative

		System.out.println("Part 1: " + (depart - earliest) * id);
		System.out.println("Part 2: " + crt(a, running));
	}

	static long crt(ArrayList<Long> a, ArrayList<Long> m) {
		long product = 1;
		for (long n : m)
			product *= n;

		long p = 0, sum = 0;
		for (int i = 0; i < m.size(); i++) {
			p = product / m.get(i);
			sum += a.get(i) * mulInv(p, m.get(i)) * p;
		}

		return sum % product;
	}

	static long mulInv(long a, long b) {
		long b0 = b;
		long x0 = 0;
		long x1 = 1;

		if (b == 1)
			return 1;

		while (a > 1) {
			long q = a / b;
			long tmp = a % b;

			a = b;
			b = tmp;

			tmp = x1 - q * x0;
			x1 = x0;
			x0 = tmp;
		}

		if (x1 < 0)
			x1 += b0;

		return x1;
	}
}
