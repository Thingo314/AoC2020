import java.util.*;

public class Day_05 {
	public static void main(String[] args) {
		int highestID = Integer.MIN_VALUE;
		HashSet<Integer> seats = new HashSet<>();
		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String seat = sc.nextLine();
			int row = 0;
			for (int i = 0; i < 7; i++) {
				row <<= 1;
				if (seat.charAt(i) == 'B')
					row |= 1;
			}

			int col = 0;
			for (int i = 7; i < 10; i++) {
				col <<= 1;
				if (seat.charAt(i) == 'R')
					col |= 1;
			}

			int seatID = row * 8 + col;
			highestID = Math.max(seatID, highestID);
			seats.add(seatID);
		}
		sc.close();

		System.out.println("Part 1: " + highestID);
		int mySeat = highestID;
		while (seats.contains(mySeat))
			mySeat--;
		System.out.println("Part 2: " + mySeat);
	}
}
