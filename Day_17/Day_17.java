import java.util.*;

public class Day_17 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		HashSet<ArrayList<Integer>> threeDState = new HashSet<>();
		HashSet<ArrayList<Integer>> fourDState = new HashSet<>();

		int x = 0, y = 0;
		int[] mins = new int[4];
		int[] maxs = new int[4];

		while (sc.hasNextLine()) {
			char[] line = sc.nextLine().toCharArray();
			for (x = 0; x < line.length; x++) {
				if (line[x] == '#') {
					ArrayList<Integer> threeDCoord = new ArrayList<>(Arrays.asList(x, y, 0));
					ArrayList<Integer> fourDCoord = new ArrayList<>(Arrays.asList(x, y, 0, 0));

					threeDState.add(threeDCoord);
					fourDState.add(fourDCoord);
					mins[0] = Math.min(mins[0], x);
					mins[1] = Math.min(mins[1], y);
					maxs[0] = Math.max(maxs[0], x);
					maxs[1] = Math.max(maxs[1], y);
				}
			}
			y++;
		}
		sc.close();

		// generateDirs will create the possible directions without hardcoding
		ArrayList<ArrayList<Integer>> dirs3 = generateDirs(3, true);
		ArrayList<ArrayList<Integer>> dirs4 = generateDirs(4, true);

		for (int cycle = 0; cycle < 6; cycle++) {
			// simulate the 3d cycle
			HashSet<ArrayList<Integer>> newThreeDState = new HashSet<>();

			// iterate through the entire space that could be active
			for (int i = mins[0] - 1; i <= maxs[0] + 1; i++) {
				for (int j = mins[1] - 1; j <= maxs[1] + 1; j++) {
					for (int k = mins[2] - 1; k <= maxs[2] + 1; k++) {
						// coord is checked later
						ArrayList<Integer> coord = new ArrayList<>(Arrays.asList(i, j, k));

						// check the surrounds
						int otherActive = 0;
						for (ArrayList<Integer> dir : dirs3) {
							ArrayList<Integer> o = new ArrayList<>();
							boolean validCoord = true;
							for (int m = 0; m < coord.size(); m++) {
								o.add(coord.get(m) + dir.get(m));
								if (o.get(m) < mins[m] || o.get(m) > maxs[m]) {
									validCoord = false;
									break;
								}
							}
							if (!validCoord)
								continue;

							if (threeDState.contains(o))
								otherActive++;
						}

						// determine the new state of the current coord
						if (otherActive == 3 || (threeDState.contains(coord) && otherActive == 2)) {
							newThreeDState.add(coord);
							for (int m = 0; m < coord.size(); m++) {
								mins[m] = Math.min(mins[m], coord.get(m));
								maxs[m] = Math.max(maxs[m], coord.get(m));
							}
						}
					}
				}
			}
			threeDState = newThreeDState;

			// simulate the 4d cycle
			// this is the same as the 3d sim
			HashSet<ArrayList<Integer>> newFourDState = new HashSet<>();

			for (int i = mins[0] - 1; i <= maxs[0] + 1; i++) {
				for (int j = mins[1] - 1; j <= maxs[1] + 1; j++) {
					for (int k = mins[2] - 1; k <= maxs[2] + 1; k++) {
						for (int l = mins[3] - 1; l <= maxs[3] + 1; l++) {
							ArrayList<Integer> coord = new ArrayList<>(Arrays.asList(i, j, k, l));

							int otherActive = 0;
							for (ArrayList<Integer> dir : dirs4) {
								ArrayList<Integer> o = new ArrayList<>();
								boolean validCoord = true;
								for (int m = 0; m < coord.size(); m++) {
									o.add(coord.get(m) + dir.get(m));
									if (o.get(m) < mins[m] || o.get(m) > maxs[m]) {
										validCoord = false;
										break;
									}
								}
								if (!validCoord)
									continue;

								if (fourDState.contains(o))
									otherActive++;
							}

							if (otherActive == 3 || (fourDState.contains(coord) && otherActive == 2)) {
								newFourDState.add(coord);
								for (int m = 0; m < coord.size(); m++) {
									mins[m] = Math.min(mins[m], coord.get(m));
									maxs[m] = Math.max(maxs[m], coord.get(m));
								}
							}
						}
					}
				}
			}
			fourDState = newFourDState;
		}

		System.out.println("Part 1: " + threeDState.size());
		System.out.println("Part 2: " + fourDState.size());
	}

	// recursively generate the directions for n dimensions
	// using directions from n-1 dimensions
	// last is a control to signify that (0, 0, ...) should be removed
	static ArrayList<ArrayList<Integer>> generateDirs(int n, boolean removeZero) {
		// single dimension will default
		if (n == 1) {
			ArrayList<ArrayList<Integer>> dirs = new ArrayList<>();
			for (int i = -1; i <= 1; i++)
				dirs.add(new ArrayList<>(Arrays.asList(i)));
			return dirs;
		}

		ArrayList<ArrayList<Integer>> lowerDimension = generateDirs(n - 1, false);
		ArrayList<ArrayList<Integer>> thisDimension = new ArrayList<>();

		for (ArrayList<Integer> dir : lowerDimension) {
			// tack on the new dimension from the lower dimension
			for (int i = -1; i <= 1; i++) {
				ArrayList<Integer> newDir = new ArrayList<>(dir);
				newDir.add(i);
				thisDimension.add(newDir);
			}
		}

		// removal of zero direction so there is no need for checking elsewhere
		if (removeZero) {
			ArrayList<Integer> zeros = new ArrayList<>();
			for (int i = 0; i < n; i++)
				zeros.add(0);
			thisDimension.remove(zeros);
		}

		return thisDimension;
	}
}
