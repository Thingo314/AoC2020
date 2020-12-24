import java.util.*;

public class Day_24 {
	static int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {-1, 1}, {1, -1}, {0, -1}};

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashMap<ArrayList<Integer>, Boolean> state = new HashMap<>();

		while (sc.hasNextLine()) {
			int x = 0, y = 0;

			String line = sc.nextLine();
			int idx = 0;
			while (idx < line.length()) {
				char firstChar = line.charAt(idx);
				switch (firstChar) {
					case 'e':
						x++;
						break;
					case 'w':
						x--;
						break;
					case 'n': {
						y++;
						char secondChar = line.charAt(++idx);
						switch (secondChar) {
							case 'w':
								x--;
								break;
						}
						break;
					}
					case 's': {
						y--;
						char secondChar = line.charAt(++idx);
						switch (secondChar) {
							case 'e':
								x++;
								break;
						}
						break;
					}
				}
				idx++;
			}

			ArrayList<Integer> tile = new ArrayList<>(Arrays.asList(x, y));
			state.put(tile, !state.getOrDefault(tile, false));
		}
		sc.close();

		int count1 = 0;
		for (boolean b : state.values())
			if (b)
				count1++;
		System.out.println("Part 1: " + count1);

		for (int i = 0; i < 100; i++) {
			HashMap<ArrayList<Integer>, Boolean> nextState = new HashMap<>();

			HashMap<ArrayList<Integer>, Integer> surrounding = new HashMap<>();
			// increment the count of adj black tiles for surrounding tiles
			for (Map.Entry<ArrayList<Integer>, Boolean> e : state.entrySet()) {
				if (!e.getValue())
					continue;

				// add to the surrounding tiles
				for (int[] dir : dirs) {
					ArrayList<Integer> adjacent = new ArrayList<>();
					adjacent.add(e.getKey().get(0) + dir[0]);
					adjacent.add(e.getKey().get(1) + dir[1]);

					surrounding.put(adjacent, surrounding.getOrDefault(adjacent, 0) + 1);
				}
			}

			for (Map.Entry<ArrayList<Integer>, Integer> e : surrounding.entrySet()) {
				int adj = e.getValue();
				ArrayList<Integer> coord = e.getKey();
				if (state.containsKey(coord) && state.get(coord)) {
					// current state of the coord is known and is black
					nextState.put(coord, (adj == 1 || adj == 2));
				} else {
					// coord is white
					nextState.put(coord, adj == 2);
				}
			}

			state = nextState;
		}

		int count2 = 0;
		for (boolean b : state.values())
			if (b)
				count2++;
		System.out.println("Part 2: " + count2);
	}
}
