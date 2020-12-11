import java.util.*;

public class Day_11 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		char[][] cells = new char[98][98];
		int i = 0;
		while (sc.hasNextLine()) {
			cells[i] = sc.nextLine().toCharArray();
			i++;
		}
		sc.close();

		char[][] next = simulate(cells, false);
		char[][] tmp = deepCopy(cells);
		while (!Arrays.deepEquals(tmp, next)) {
			tmp = next;
			next = simulate(next, false);
		}

		int result1 = 0;
		for (char[] a : next) {
			for (char c : a)
				if (c == '#')
					result1++;
		}

		next = simulate(cells, true);
		tmp = deepCopy(cells);
		while (!Arrays.deepEquals(tmp, next)) {
			tmp = next;
			next = simulate(next, true);
		}

		int result2 = 0;
		for (char[] a : next) {
			for (char c : a)
				if (c == '#')
					result2++;
		}

		System.out.println("Part 1: " + result1);
		System.out.println("Part 2: " + result2);
	}

	static int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

	static char[][] deepCopy(char[][] a) {
		char[][] b = new char[a.length][a[0].length];
		for (int i = 0; i < b.length; i++)
			for (int j = 0; j < b[0].length; j++)
				b[i][j] = a[i][j];
		return b;
	}

	static char[][] simulate(char[][] cells, boolean part2) {
		char[][] res = new char[cells.length][cells[0].length];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[i].length; j++) {
				if (cells[i][j] == '.') {
					res[i][j] = '.';
					continue;
				}

				int adj = 0;
				if (part2)
					adj = checkSurrounding2(cells, i, j);
				else
					adj = checkSurrounding1(cells, i, j);

				if (cells[i][j] == 'L')
					if (adj == 0)
						res[i][j] = '#';
					else
						res[i][j] = 'L';
				else
					if (adj >= (part2 ? 5 : 4))
						res[i][j] = 'L';
					else
						res[i][j] = '#';
			}
		}

		return res;
	}

	static int checkSurrounding1(char[][] arr, int r, int c) {
		int adj = 0;
		for (int[] dir : dirs) {
			int newr = r + dir[0];
			int newc = c + dir[1];
			if (newr < 0 || newr >= arr.length || newc < 0 || newc >= arr[r].length)
				continue;
			if (arr[newr][newc] == '#')
				adj++;
		}

		return adj;
	}

	static int checkSurrounding2(char[][] arr, int r, int c) {
		int adj = 0;
		for (int[] dir : dirs) {
			int newr = r + dir[0];
			int newc = c + dir[1];
			while (true) {
				if (newr < 0 || newr >= arr.length || newc < 0 || newc >= arr[r].length)
					break;
				if (arr[newr][newc] == '.') {
					newr += dir[0];
					newc += dir[1];
					continue;
				}
				if (arr[newr][newc] == '#') {
					adj++;
					break;
				} else {
					break;
				}
			}
		}

		return adj;
	}
}
