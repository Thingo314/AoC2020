import java.util.*;

public class Day_08 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<int[]> operations = new ArrayList<>();
		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" ");

			int[] op = new int[2];
			// "nop" => 0, "jmp" => 1, "acc" => 2
			if (line[0].equals("acc"))
				op[0] = 2;
			else if (line[0].equals("jmp"))
				op[0] = 1;
			op[1] = Integer.parseInt(line[1]);

			operations.add(op);
		}
		sc.close();

		int[] part1 = run(operations);
		System.out.println("Part 1: " + part1[1]);

		int[] part2 = null;
		for (int i = 0; i < operations.size(); i++) {
			int[] op = operations.get(i);

			if (op[0] < 2) {
				op[0]++;
				op[0] %= 2;

				part2 = run(operations);
				if (part2[0] == 1)
					break;

				op[0]++;
				op[0] %= 2;
			}
		}

		System.out.println("Part 2: " + part2[1]);
	}

	static int[] run(ArrayList<int[]> ops) {
		int acc = 0;
		int idx = 0;
		HashSet<Integer> reached = new HashSet<>();
		while (reached.add(idx)) {
			if (idx == ops.size())
				return new int[] {1, acc};

			int[] op = ops.get(idx);
			switch (op[0]) {
				case 0:
					idx++;
					break;
				case 1:
					idx += op[1];
					break;
				case 2:
					acc += op[1];
					idx++;
					break;
			}
		}
		return new int[] {0, acc};
	}
}
