import java.util.*;

public class Day_14 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String mask = "";
		HashMap<Long, Long> memory1 = new HashMap<>();
		HashMap<Long, Long> memory2 = new HashMap<>();

		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" ");
			if (line[0].equals("mask")) {
				mask = line[2];
			} else {
				long loc = Long.parseLong(line[0].substring(4, line[0].length() - 1));
				long val = Long.parseLong(line[2]);

				HashSet<Long> locations = new HashSet<>();
				locations.add(loc);
				for (int i = 0; i < mask.length(); i++) {
					HashSet<Long> newLocations = new HashSet<>();

					switch (mask.charAt(mask.length() - i - 1)) {
						case 'X':
							for (long l : locations) {
								newLocations.add(l & (~(1L << i)));
								newLocations.add(l | (1L << i));
							}
							break;
						case '0':
							continue;
						case '1':
							for (long l : locations)
								newLocations.add(l | (1L << i));
							break;
					}

					locations = newLocations;
				}

				for (long l : locations)
					memory2.put(l, val);

				for (int i = 0; i < mask.length(); i++)
					switch (mask.charAt(mask.length() - i - 1)) {
						case 'X':
							continue;
						case '0':
							val &= (~(1L << i));
							break;
						case '1':
							val |= (1L << i);
					}

				memory1.put(loc, val);
			}
		}
		sc.close();

		long res1 = 0;
		for (long n : memory1.values())
			res1 += n ;
		long res2 = 0;
		for (long n : memory2.values())
			res2 += n ;

		System.out.println("Part 1: " + res1);
		System.out.println("Part 2: " + res2);
	}
}
