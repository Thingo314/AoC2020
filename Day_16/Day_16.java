import java.util.*;

public class Day_16 {
	public static void main(String[] args) {
		ArrayList<int[]> ranges = new ArrayList<>();
		int feature = 0;
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals(""))
				break;

			String[] rule = line.split(" ");
			String[] lower = rule[rule.length - 3].split("-");
			String[] upper = rule[rule.length - 1].split("-");

			int[] range = new int[4];
			range[0] = Integer.parseInt(lower[0]);
			range[1] = Integer.parseInt(lower[1]);
			range[2] = Integer.parseInt(upper[0]);
			range[3] = Integer.parseInt(upper[1]);

			ranges.add(range);
			feature++;
		}
		sc.nextLine();

		String[] ticket = sc.nextLine().split(",");
		int[] myTicket = new int[ticket.length];
		for (int i = 0; i < myTicket.length; i++)
			myTicket[i] = Integer.parseInt(ticket[i]);

		sc.nextLine();
		sc.nextLine();

		int res1 = 0;
		ArrayList<int[]> validTickets = new ArrayList<>();
		while (sc.hasNextLine()) {
			ticket = sc.nextLine().split(",");
			int[] ticketNums = new int[ticket.length];
			for (int i = 0; i < ticketNums.length; i++)
				ticketNums[i] = Integer.parseInt(ticket[i]);

			boolean validTicket = true;
			for (int i = 0; i < ticketNums.length; i++) {
				boolean validField = false;
				int field = ticketNums[i];
				for (int[] rule : ranges) {
					if ((field >= rule[0] && field <= rule[1])
						|| (field >= rule[2] && field <= rule[3])) {
						validField = true;
						break;
					}
				}

				if (!validField) {
					res1 += field;
					validTicket = false;
				}
			}

			if (validTicket)
				validTickets.add(ticketNums);
		}
		sc.close();

		boolean[][] possibleMap = new boolean[ranges.size()][ranges.size()];
		for (int i = 0; i < possibleMap.length; i++)
			for (int j = 0; j < possibleMap[i].length; j++)
				possibleMap[i][j] = true;

		for (int[] ticketNum : validTickets) {
			for (int i = 0; i < ticketNum.length; i++) {
				int field = ticketNum[i];
				HashSet<Integer> possibilities = new HashSet<>();

				for (int idx = 0; idx < ranges.size(); idx++) {
					int[] rule = ranges.get(idx);
					if ((field >= rule[0] && field <= rule[1])
						|| (field >= rule[2] && field <= rule[3]))
						possibilities.add(idx);
				}

				for (int j = 0; j < ranges.size(); j++)
					if (!possibilities.contains(j))
						possibleMap[i][j] = false;
			}
		}

		HashMap<Integer, Integer> mapping = new HashMap<>();
		while (mapping.size() != ranges.size()) {
			HashSet<Integer> foundMapping = new HashSet<>();
			for (int i = 0; i < possibleMap.length; i++) {
				int mappings = 0;
				int mappedTo = -1;
				for (int j = 0; j < possibleMap[i].length; j++) {
					if (possibleMap[i][j]) {
						mappings++;
						mappedTo = j;
					}
				}
				if (mappings == 1 && !mapping.containsKey(i)) {
					foundMapping.add(i);
					mapping.put(i, mappedTo);
				}
			}
			if (foundMapping.size() == 0)
				break;

			for (int n : foundMapping) {
				int idx = mapping.get(n);
				for (int i = 0; i < possibleMap.length; i++)
					possibleMap[i][idx] = false;
			}
		}

		long res2 = 1;
		for (int i = 0; i < ranges.size(); i++)
			if (mapping.get(i) < 6)
				res2 *= myTicket[i];

		System.out.println("Part 1: " + res1);
		System.out.println("Part 2: " + res2);
	}
}
