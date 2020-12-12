import java.util.*;

public class Day_12 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int facing = 1;
		int wx = 10, wy = 1;
		int x1 = 0, y1 = 0;
		int x2 = 0, y2 = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			char op = line.charAt(0);
			int val = Integer.parseInt(line.substring(1));
			int tmp = wx;
			switch (op) {
				case 'F':
					if (facing == 0)
						y1 += val;
					else if (facing == 1)
						x1 += val;
					else if (facing == 2)
						y1 -= val;
					else if (facing == 3)
						x1 -= val;

					x2 += wx * val;
					y2 += wy * val;

					break;
				case 'N':
					y1 += val;
					wy += val;

					break;
				case 'S':
					y1 -= val;
					wy -= val;

					break;
				case 'E':
					x1 += val;
					wx += val;

					break;
				case 'W':
					x1 -= val;
					wx -= val;

					break;
				case 'L':
					facing = (facing + 4 - (val / 90)) % 4;

					if (val == 90) {
						wx = -wy;
						wy = tmp;
					} else if (val == 180) {
						wx = -wx;
						wy = -wy;
					} else if (val == 270) {
						wx = wy;
						wy = -tmp;
					}

					break;
				case 'R':
					facing = (facing + (val / 90)) % 4;
					if (val == 90) {
						wx = wy;
						wy = -tmp;
					}
					else if (val == 180) {
						wx = -wx;
						wy = -wy;
					} else if (val == 270) {
						wx = -wy;
						wy = tmp;
					}

					break;
			}
		}
		sc.close();

		System.out.println("Part 1: " + (Math.abs(x1) + Math.abs(y1)));
		System.out.println("Part 2: " + (Math.abs(x2) + Math.abs(y2)));
	}
}
